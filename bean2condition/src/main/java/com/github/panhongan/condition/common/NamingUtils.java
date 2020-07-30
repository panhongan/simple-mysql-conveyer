package com.github.panhongan.condition.common;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

public class NamingUtils {

    /**
     * 驼峰命名 转 匈牙利命名
     *
     * @param camelStr 驼峰命名串
     * @return 匈牙利命名串
     */
    public static String camel2Hung(String camelStr) {
        if (StringUtils.isEmpty(camelStr)) {
            return null;
        }

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < camelStr.length();  ++i) {
            char ch = camelStr.charAt(i);
            if (Character.isUpperCase(ch)) {
                builder.append("_");
                builder.append(Character.toLowerCase(ch));
            } else {
                builder.append(ch);
            }
        }

        return builder.toString();
    }

    public static String hung2Camel(String hungStr) {
        if (StringUtils.isEmpty(hungStr)) {
            return null;
        }

        String[] arr = hungStr.split("_");
        if (ArrayUtils.isNotEmpty(arr)) {
            StringBuilder builder = new StringBuilder();
            builder.append(arr[0]);

            for (int i = 1; i < arr.length; ++i) {
                char ch = arr[i].charAt(0);
                builder.append(Character.toUpperCase(ch) + arr[i].substring(1));
            }

            return builder.toString();
        } else {
            return null;
        }
    }


}
