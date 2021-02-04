package com.halo.mms.common.utils;

import cn.hutool.core.io.FileUtil;

import java.nio.file.Path;
import java.nio.file.Paths;

public class EnvUtil {

    private static final String PRI_DIR = "/.mms";
    private static final String DB_DIR = "/db";

    static {
        Path path = Paths.get(System.getProperty("user.home"), PRI_DIR);
        if (!path.toFile().exists()) {
            FileUtil.mkdir(path.toFile());
        }

        Path dbPath = Paths.get(path.toString(), DB_DIR);
        if (!dbPath.toFile().exists()) {
            FileUtil.mkdir(dbPath.toFile());
        }
    }

    public static String getDbDir() {
        return Paths.get(getRootDir(), DB_DIR).toString();
    }

    public static String getRootDir() {
        return Paths.get(System.getProperty("user.home"), PRI_DIR).toString();
    }
}
