package com.halo.mms.serv.store;

import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;

@FunctionalInterface
public interface RocksAction<T> {

    T doAction(RocksDB rocksDB) throws RocksDBException;
}
