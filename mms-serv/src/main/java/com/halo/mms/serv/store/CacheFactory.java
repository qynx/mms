package com.halo.mms.serv.store;

import java.util.HashMap;
import java.util.Map;

public class CacheFactory {

    private static final Map<String, FileCache> map = new HashMap<>();

    public static AbstractCache buildFileCache(String name) {
        if (map.get(name) != null) {
            return map.get(name);
        }

        synchronized (map) {
            if (null != map.get(name)) {
                return map.get(name);
            }

            FileCache fileCache = new FileCache(name);
            map.put(name, fileCache);
            return fileCache;
        }
    }
}
