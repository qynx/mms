package com.halo.mms.serv.util;

import lombok.extern.slf4j.Slf4j;
import org.rocksdb.Options;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;

@Slf4j
public class RockDBUtil {
    private static final String NAME_SUFFIX = "_rocks";

    static {
        RocksDB.loadLibrary();
    }

    public static RocksDB init(String path) {
        Options options = new Options();
        options.setCreateIfMissing(true);

        RocksDB rocksDB;
        try {
            rocksDB = RocksDB.open(options, path + NAME_SUFFIX);
        } catch (RocksDBException e) {
            log.error("", e);
            throw new RuntimeException(e.getMessage());
        }
        return rocksDB;
    }
}
