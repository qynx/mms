package com.halo.mms.repo.repository;

import lombok.extern.slf4j.Slf4j;

import java.util.function.Supplier;

@Slf4j
public class BaseRepository {

    protected <T> T safeExec(Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (Exception e) {
            log.error("", e);
            throw new RuntimeException("服务繁忙");
        }
    }
}
