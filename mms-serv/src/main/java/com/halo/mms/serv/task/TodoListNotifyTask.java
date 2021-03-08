package com.halo.mms.serv.task;

import com.halo.mms.serv.service.api.TodoListNotifyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Slf4j
@Component
public class TodoListNotifyTask {

    @Resource
    private TodoListNotifyService todoListNotifyService;

    @Scheduled(fixedDelay = 5 * 1000, initialDelay = 0L)
    public void doScan() {
        todoListNotifyService.scanToNotify();
    }

    @Scheduled(cron = "1 15 23,10,9 * * ?")
    public void notifyToday() {
        todoListNotifyService.notifyToday();
    }
}
