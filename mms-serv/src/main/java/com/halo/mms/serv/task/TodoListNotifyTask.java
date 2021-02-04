package com.halo.mms.serv.task;

import com.halo.mms.serv.service.api.TodoListNotifyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component
public class TodoListNotifyTask {

    @Resource
    private TodoListNotifyService todoListNotifyService;

    @Scheduled(fixedDelay = 5 * 1000, initialDelay = 0L)
    public void doScan() {
        log.info("notify-task schedule");
        todoListNotifyService.scanToNotify();
    }
}
