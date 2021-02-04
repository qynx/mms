package com.halo.mms.common.config;

import com.halo.mms.common.exception.ConfigNotFoundException;
import com.halo.mms.common.utils.EnvUtil;
import lombok.extern.slf4j.Slf4j;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

@Slf4j
public abstract class AbstractConfigLoader {

    private static final String CONFIG_DIR = "/config";

    protected Object LoadYamlConfig(Path path) throws Exception {
        if (!Files.exists(path)) {
            throw new RuntimeException(path.toString());
        }
        return new Yaml().load(Files.newInputStream(path));
    }

    protected static Properties loadProp(String fileName) {
        Path path = Paths.get(getConfigDir(), fileName);
        if (!Files.exists(path)) {
            throw new ConfigNotFoundException(fileName);
        }

        Properties p = new Properties();
        try {
            p.load(Files.newInputStream(path));
        } catch (IOException e) {
            throw new ConfigNotFoundException(fileName);
        }

        return p;
    }

    protected static String getConfigDir() {
        return Paths.get(EnvUtil.getRootDir(), CONFIG_DIR).toString();
    }

}
