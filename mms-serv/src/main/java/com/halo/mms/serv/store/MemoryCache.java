package com.halo.mms.serv.store;


import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class MemoryCache extends AbstractCache {

    private static final ConcurrentHashMap<String, StoreData> CONTAINER = new ConcurrentHashMap<>();

    private static final MemoryCache INSTANCE = new MemoryCache();

    public static void put(String key, StoreData storeData) {
        INSTANCE.putData(key, storeData);
    }

    public static Optional<StoreData> get(String key) {
        return INSTANCE.getData(key);
    }

    protected void doPut(String key, StoreData storeData) {
        CONTAINER.put(key, storeData);
    }

    protected void doRemove(String key) {
        CONTAINER.remove(key);
    }

    protected StoreData doGet(String key) {
        return CONTAINER.get(key);
    }
}
