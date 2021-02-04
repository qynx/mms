package com.halo.mms.serv.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.halo.mms.common.domain.WxSendMsg;
import com.halo.mms.common.utils.EnvUtil;
import com.halo.mms.repo.model.ToDoListDO;
import com.halo.mms.repo.mybatis.mapper.ToDoListMapper;
import com.halo.mms.serv.mq.KafkaMqProducer;
import com.halo.mms.serv.service.api.TodoListNotifyService;
import com.halo.mms.serv.store.RocksAction;
import com.halo.mms.serv.util.JsonUtil;
import com.halo.mms.serv.util.RockDBUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;
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
    private KafkaMqProducer wxPusherProducer;

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
            wxPusherProducer.sendMsg(JsonUtil.toJson(wxSendMsg));
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
