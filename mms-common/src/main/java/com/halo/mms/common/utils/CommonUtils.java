package com.halo.mms.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Pattern;

public class CommonUtils {

    public static String genUuid() {
        return UUID.randomUUID().toString();
    }

    public static String getToday() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

    /**
     * scheme
     * host
     */
    public static boolean isValidUrl(String url) {
        if (StringUtils.isEmpty(url)) {
            return false;
        }
        String regex = "^https?://(.*?)";
        return Pattern.matches(regex, url);
    }

    public static String extractHost(String url) {
        int startPos = url.indexOf("/");
        startPos = startPos + 2;
        int endPos = url.indexOf("/", startPos);

        return url.substring(startPos, endPos);
    }


    public static void main(String[] args) {
        System.out.println(extractHost("https://www.runoob.com/java/java-regular-expressions.html"));
    }
}

