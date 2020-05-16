package com.halo.mms.serv.configs;

import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class AbstractConfigLoader {

    protected static final String PRI_DIR = "/.mms";
    protected static final String CONFIG_DIR = "/config";

    protected Object LoadYamlConfig(Path path) throws Exception {
        if (!Files.exists(path)) {
            throw new RuntimeException(path.toString());
        }
        return new Yaml().load(Files.newInputStream(path));
    }

    protected String getConfigDir() {
        return Paths.get(getRootDir(), CONFIG_DIR).toString();
    }

    protected String getRootDir() {
        return Paths.get(System.getProperty("user.home"), PRI_DIR).toString();
    }
}
