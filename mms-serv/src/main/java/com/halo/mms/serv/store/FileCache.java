package com.halo.mms.serv.store;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.halo.mms.common.utils.EnvUtil;
import com.halo.mms.common.utils.JsonUtil;
import com.halo.mms.serv.util.RockDBUtil;
import lombok.extern.slf4j.Slf4j;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;

import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Slf4j
public class FileCache extends AbstractCache {

    private RocksDB rocksDB;

    public FileCache(String dbName) {
        Path path = Paths.get(EnvUtil.getDbDir(), dbName);
        if (!path.toFile().exists()) {
            FileUtil.mkdir(path.toFile());
        }


        rocksDB = RockDBUtil.init(path.toString());
    }

    @Override
    void doPut(String key, StoreData storeData) {
        execute(r -> {
            r.put(key.getBytes(Charset.defaultCharset()), JsonUtil.toJson(storeData).getBytes(Charset.defaultCharset()));
            return null;
        });
    }

    @Override
    void doRemove(String key) {
        execute(rocksDB -> {
            rocksDB.delete(key.getBytes(Charset.defaultCharset()));
            return null;
        });
    }

    @Override
    StoreData doGet(String key) {
        String msg = execute(rocksDB -> StrUtil.str(rocksDB.get(key.getBytes(Charset.defaultCharset())), Charset.defaultCharset()));
        StoreData storeData = JsonUtil.fromJson(msg, StoreData.class);
        return Optional.ofNullable(storeData).filter(d -> d.getInvalidTimeInMs() > System.currentTimeMillis()).orElse(null);
    }

    private <T> T execute(RocksAction<T> rocksAction) {
        try {
            return rocksAction.doAction(rocksDB);
        } catch (RocksDBException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
