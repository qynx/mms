package com.halo.mms.common.service;

import com.halo.mms.common.domain.ChapterDTO;
import com.halo.mms.common.domain.ListPageResult;
import io.vertx.core.Vertx;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * http://www.biquge5200.net/124_124345/
 */
@Slf4j
public class BiqugeNovelCrawService extends AbstractNovelCrawService {

    @Override
    public String parseChapterContent(String contentPageHtml) {
        log.info("body: {}", contentPageHtml);
        Document document = Jsoup.parse(contentPageHtml);
        Element contentDiv = document.getElementById("content");
        return contentDiv.text();
    }

    @Override
    public ListPageResult parsePageUrl(String listPageHtml) {
        Document document = Jsoup.parse(listPageHtml);
        Element infoDiv = document.getElementById("info");
        String title = infoDiv.getElementsByTag("h1").text();
        log.info("title: {}", title);

        Element chapterListDiv = document.getElementById("list").getElementsByTag("dl").get(0);
        Elements elements = chapterListDiv.getAllElements();
        List<ChapterDTO> list = new ArrayList<>();
        int size = elements.size();
        boolean begin = false;
        int chapterIndex = 1;
        for (int i = 1; i < size; i++) {
            Element ele = elements.get(i);
            if (begin && ele.tagName().equals("dd")) {
                Element urlEle = ele.getElementsByTag("a").get(0);
                ChapterDTO chapterDTO = new ChapterDTO().setUrl(urlEle.attr("href"))
                    .setTitle(urlEle.text()).setChapterIndex(chapterIndex++);
                list.add(chapterDTO);
            } else if (ele.text().contains(title) && ele.text().contains("正文")) {
                begin = true;
            }
        }

        return new ListPageResult().setChapters(list).setNewListPageUrl(Collections.emptyList());
    }

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        WebClientOptions clientOptions = new WebClientOptions()
            .setUserAgent("PostmanRuntime/7.26.10");
        WebClient client = WebClient.create(vertx, clientOptions);

        BiqugeNovelCrawService biqugeNovelCrawService = new BiqugeNovelCrawService();

        client.getAbs("http://www.biquge5200.net/124_124345/168028807.html")
            .send()
            .onSuccess(rsp -> {
                byte[] originBytes = rsp.body().getBytes();
                String body = new String(originBytes, Charset.forName("GBK"));
                biqugeNovelCrawService.parseChapterContent(body);
            }).onFailure(e -> e.printStackTrace());
    }
}
