package com.halo.mms.serv.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.davidmarquis.redisq.producer.DefaultMessageProducer;
import com.halo.mms.common.domain.WxSendMsg;
import com.halo.mms.common.utils.CommonUtils;
import com.halo.mms.common.utils.EnvUtil;
import com.halo.mms.common.plus.model.ToDoListDO;
import com.halo.mms.common.plus.mapper.ToDoListMapper;
import com.halo.mms.serv.service.api.TodoListNotifyService;
import com.halo.mms.serv.store.RocksAction;
import com.halo.mms.common.utils.JsonUtil;
import com.halo.mms.serv.util.RockDBUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TodoListNotifyServiceImpl implements TodoListNotifyService {

    @Resource
    private ToDoListMapper toDoListMapper;

    @Resource
    private DefaultMessageProducer<String> wxPushProducer;

    @Override
    public void notifyToday() {
        String today = CommonUtils.getToday();
        Wrapper<ToDoListDO> wrapper = new QueryWrapper<ToDoListDO>().lambda()
            .le(ToDoListDO::getStartDay, today)
            .ge(ToDoListDO::getEndDay, today);
        List<ToDoListDO> domainList = toDoListMapper.selectList(wrapper);

        if (domainList.size() <= 0) {
           return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (ToDoListDO toDoListDO: domainList) {
            stringBuilder.append(toDoListDO.getTitle())
                .append("\n    ")
                .append(toDoListDO.getContent());
        }
        WxSendMsg wxSendMsg = new WxSendMsg().setUid(domainList.get(0).getUid())
            .setTitle("今日待办").setContent(stringBuilder.toString());
        wxPushProducer.submit(JsonUtil.toJson(wxSendMsg));
    }

    @Override
    public void scanToNotify() {
        doScan();
    }

    private void doScan() {
        // 允许一分钟的误差
        long scanStart = System.currentTimeMillis() - 20;
        long scanEnd = System.currentTimeMillis() + 60 * 1000;

        Wrapper<ToDoListDO> wrapper = new QueryWrapper<ToDoListDO>().lambda()
            .between(ToDoListDO::getNotifyTime, scanStart, scanEnd)
            .orderByAsc(ToDoListDO::getNotifyTime);
        List<ToDoListDO> list = toDoListMapper.selectList(wrapper);
        list = list.stream().filter(d -> Boolean.FALSE.equals(d.getNotifyStatus()))
            .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(list)) {
            return;
        }

        if (list.get(0).getNotifyTime() > System.currentTimeMillis()) {
            try {
                Thread.sleep(list.get(0).getNotifyTime() - System.currentTimeMillis());
            } catch (Exception e) {
                log.error("", e);
            }
        }

        for (ToDoListDO domain: list) {
            ToDoListDO updates = new ToDoListDO();
            updates.setId(domain.getId());
            updates.setNotifyStatus(true);
            toDoListMapper.updateById(updates);

            WxSendMsg wxSendMsg = new WxSendMsg().setTitle(domain.getTitle()).setContent(domain.getContent()).setUid(domain.getUid());
            wxPushProducer.submit(JsonUtil.toJson(wxSendMsg));
        }
    }


    private static Path path = Paths.get(EnvUtil.getDbDir(), "todo_list_noti");
    private static RocksDB rocksDB = RockDBUtil.init(path.toString());

    public void record(ToDoListDO toDoListDO) {
    }

    private <T> T execute(RocksAction<T> rocksAction) {
        try {
            return rocksAction.doAction(rocksDB);
        } catch (RocksDBException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
