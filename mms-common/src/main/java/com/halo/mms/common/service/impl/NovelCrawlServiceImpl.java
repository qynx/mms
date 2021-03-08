package com.halo.mms.common.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.halo.mms.common.consts.MqQueueConst;
import com.halo.mms.common.domain.ChapterDTO;
import com.halo.mms.common.domain.ListPageDTO;
import com.halo.mms.common.domain.ListPageResult;
import com.halo.mms.common.exception.MmsException;
import com.halo.mms.common.mq.RedisMqConsumer;
import com.halo.mms.common.mq.RedisMqProducer;
import com.halo.mms.common.plus.model.NovelBookDO;
import com.halo.mms.common.plus.model.NovelContentDO;
import com.halo.mms.common.plus.service.IModelService;
import com.halo.mms.common.repo.NovelBookRepo;
import com.halo.mms.common.service.AbstractNovelCrawService;
import com.halo.mms.common.service.BiqugeNovelCrawService;
import com.halo.mms.common.service.NovelCrawlService;
import com.halo.mms.common.utils.CommonUtils;
import com.halo.mms.common.utils.JsonUtil;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class NovelCrawlServiceImpl implements NovelCrawlService {

    @Resource
    private Vertx vertx;
    @Resource
    private NovelBookRepo novelBookRepo;
    @Resource
    private RedisMqProducer crawlerListProducer;
    @Resource
    private RedisMqProducer crawlerContentProducer;
    @Resource
    private RedisMqProducer crawlerSaveContentProducer;
    @Resource
    private IModelService<NovelContentDO> contentModelService;

    private static final Map<String, AbstractNovelCrawService> HOST_HANDLER =
        new HashMap<String, AbstractNovelCrawService>() {{
            put("www.biquge5200.net", new BiqugeNovelCrawService());
            put("www.sizhicn.com", new BiqugeNovelCrawService());
        }};

    private WebClient webClient;

    @PostConstruct
    public void initCop() {
        WebClientOptions clientOptions = new WebClientOptions()
            .setUserAgent("PostmanRuntime/7.26.10");
        webClient = WebClient.create(vertx, clientOptions);
    }

    @Override
    public NovelBookDO start(String firstListPageUrl) {
        String host = CommonUtils.extractHost(firstListPageUrl);
        AbstractNovelCrawService parser = HOST_HANDLER.get(host);
        if (null == parser) {
            throw new MmsException("尚未支持的站点");
        }

        NovelBookDO novelBookDO = novelBookRepo.getByUrl(firstListPageUrl);
        if (null == novelBookDO) {
            novelBookDO = NovelBookDO.builder().startUrl(firstListPageUrl).name("").build();
            novelBookDO = novelBookRepo.save(novelBookDO);
        }

        ListPageDTO listPageDTO = new ListPageDTO().setBookId(novelBookDO.getId())
            .setHost(host).setIndex(1).setUrl(firstListPageUrl);
        crawlerListProducer.send(listPageDTO);
        return novelBookDO;
    }

    @Bean
    public RedisMqConsumer crawlerListConsumer() {
        RedisMqConsumer redisMqConsumer = new RedisMqConsumer(MqQueueConst.CHAPTER_LIST_TASK);
        redisMqConsumer.setMessageListener(message -> {
            consumeList(message.getPayload());
        });
        return redisMqConsumer;
    }

    public void consumeList(String msg) {
        ListPageDTO listPageDTO = JsonUtil.fromJson(msg, ListPageDTO.class);
        handleList(listPageDTO);
    }

    public Future<Void> handleList(ListPageDTO listPageDTO) {

        AbstractNovelCrawService parser = HOST_HANDLER.get(listPageDTO.getHost());
        Promise<Void> promise = Promise.promise();
        Future<HttpResponse<Buffer>> future = webClient.getAbs(listPageDTO.getUrl())
            .send()
            .onSuccess(rsp -> {
                String body = new String(rsp.body().getBytes(), StandardCharsets.UTF_8);
                ListPageResult listPageResult = parser.parsePageUrl(body);
                //log.info("handleList-result: {}", JsonUtil.toJson(listPageResult));
                if (CollectionUtil.isNotEmpty(listPageResult.getNewListPageUrl())) {
                    listPageResult.getNewListPageUrl().forEach(l -> {
                        crawlerListProducer.send(l);
                    });
                }

                if (CollectionUtil.isNotEmpty(listPageResult.getChapters())) {
                    listPageResult.getChapters().forEach(l -> {
                        l.setPageInfo(listPageDTO);
                        crawlerContentProducer.send(l);
                    });
                }
                promise.complete();
            })
            .onFailure(e ->{
                log.error("", e);
                promise.fail(e);
            });

        return promise.future();
    }

    @Bean
    public RedisMqConsumer crawlerContentConsumer() {
        RedisMqConsumer redisMqConsumer = new RedisMqConsumer(MqQueueConst.CHAPTER_CONTENT_TASK);
        redisMqConsumer.setMessageListener(message -> {
            consumeContent(message.getPayload());
        });
        return redisMqConsumer;
    }

    public void consumeContent(String msg) {
        ChapterDTO chapterDTO = JsonUtil.fromJson(msg, ChapterDTO.class);
        handlerContent(chapterDTO);
    }

    public void handlerContent(ChapterDTO chapterDTO) {
        log.info("handleContent: {}", JsonUtil.toJson(chapterDTO));
        AbstractNovelCrawService parser = HOST_HANDLER.get(chapterDTO.getPageInfo().getHost());
        if (null == parser) {
            throw new MmsException("暂不支持的站点: " + chapterDTO.getPageInfo().getHost());
        }

        webClient.getAbs(chapterDTO.getUrl())
            .send()
            .onSuccess(rsp -> {
                String body = new String(rsp.body().getBytes(), StandardCharsets.UTF_8);
                String content = parser.parseChapterContent(body);

                chapterDTO.setContent(content);
                crawlerSaveContentProducer.send(chapterDTO);
            }).onFailure(e -> log.error("", e));
    }

    @Bean
    public RedisMqConsumer crawlerSaveConsumer() {
        RedisMqConsumer redisMqConsumer = new RedisMqConsumer(MqQueueConst.CHAPTER_SAVE_TASK);
        redisMqConsumer.setMessageListener(message -> {
            ChapterDTO chapterDTO = JsonUtil.fromJson(message.getPayload(), ChapterDTO.class);
            handlerSave(chapterDTO);
        });
        return redisMqConsumer;
    }

    public void handlerSave(ChapterDTO chapterDTO) {
        Wrapper<NovelContentDO> wrapper = new QueryWrapper<NovelContentDO>()
            .lambda().eq(NovelContentDO::getUrl, chapterDTO.getUrl());
        NovelContentDO novelContentDO = contentModelService.getOne(wrapper, false);
        if (null == novelContentDO) {
            novelContentDO = build(chapterDTO);
            contentModelService.save(novelContentDO);
        } else {
            NovelContentDO news = build(chapterDTO);
            news.setId(novelContentDO.getId());
            contentModelService.updateById(news);
        }
    }

    private NovelContentDO build(ChapterDTO chapterDTO) {
        return NovelContentDO.builder().content(chapterDTO.getContent())
            .bookId(chapterDTO.getPageInfo().getBookId())
            .chapterIndex(chapterDTO.getChapterIndex())
            .title(chapterDTO.getTitle())
            .url(chapterDTO.getUrl()).build();
    }
}
