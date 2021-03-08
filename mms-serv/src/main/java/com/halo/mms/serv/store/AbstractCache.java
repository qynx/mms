package com.halo.mms.serv.store;

import com.fasterxml.jackson.core.type.TypeReference;
import com.halo.mms.common.utils.JsonUtil;
import org.springframework.util.Assert;

import java.util.Optional;

public abstract class AbstractCache {

    public void putData(String key, StoreData storeData) {
        internalPut(key, storeData);
    }

    public Optional<StoreData> getData(String key) {
        return internalGet(key);
    }

    public <T> Optional<T> obtainData(String key) {
        return getData(key).map(d -> JsonUtil.fromJson(d.getValue(), new TypeReference<T>() {
        }));
    }

    private void internalPut(String key, StoreData storeData) {
        Assert.hasText(key, "key must not null");
        doPut(key, storeData);
    }

    private Optional<StoreData> internalGet(String key) {
        StoreData storeData = doGet(key);
        if (null == storeData) {
            return Optional.empty();
        }
        if (null != storeData.getInvalidTimeInMs() && System.currentTimeMillis() > storeData.getInvalidTimeInMs()) {
            doRemove(key);
            return Optional.empty();
        }

        return Optional.ofNullable(doGet(key));
    }

    abstract void doPut(String key, StoreData storeData);

    abstract void doRemove(String key);

    abstract StoreData doGet(String key);
}
