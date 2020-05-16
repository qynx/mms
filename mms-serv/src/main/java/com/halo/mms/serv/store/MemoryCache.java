package com.halo.mms.serv.store;


import org.springframework.util.Assert;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class MemoryCache {

    private static final ConcurrentHashMap<String, StoreData> CONTAINER = new ConcurrentHashMap<>();


    public static void put(String key, StoreData storeData) {
        Assert.hasText(key, "key must not null");
        CONTAINER.put(key, storeData);
    }

    public static Optional<StoreData> get(String key) {
        return Optional.ofNullable(CONTAINER.get(key));
    }

}
