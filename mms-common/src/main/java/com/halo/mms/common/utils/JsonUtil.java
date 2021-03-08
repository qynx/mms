package com.halo.mms.common.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Map;

@Slf4j
public class JsonUtil {

    private static ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static <T> Map<String, Object> toMap(T obj) {
        if (null == obj) {
            return null;
        }
        try {
            return objectMapper.convertValue(obj, new TypeReference<Map<String, Object>>() {
            });
        } catch (Exception e) {
            log.error("", e);
        }
        return null;
    }

    public static String toJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            log.error("", e);
        }
        return null;
    }

    public static <T> T fromJson(String json, TypeReference<T> typeReference) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }

        try {
            return objectMapper.readValue(json, typeReference);
        } catch (Exception e) {
            log.error("", e);
        }

        return null;
    }

    public static <T> T fromJson(String json, Class<T> type) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }

        try {
            return objectMapper.readValue(json, type);
        } catch (Exception e) {
            log.error("", e);
        }

        return null;
    }
}
