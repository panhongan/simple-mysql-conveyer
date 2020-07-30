package com.github.panhongan.common.utils;

import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author panhongan
 * @since 2019.7.13
 * @version 1.0
 */

public class ReflectUtils {

    private static Map<Class, Collection<Field>> classFieldsMap = new ConcurrentHashMap<>();

    public static Collection<Field> getClassBeanFieldFast(Class c) {
        Collection<Field> fields = classFieldsMap.get(c);
        if (fields != null) {
            return fields;
        }

        return classFieldsMap.computeIfAbsent(c, k -> getAllFields(k));
    }

    static Collection<Field> getAllFields(Class c) {
        Collection<Field> fields = new ArrayList<>();
        Class tmp = c;

        while (tmp != null) {
            Field[] arr = tmp.getDeclaredFields();
            if (ArrayUtils.isNotEmpty(arr)) {
                for (Field field : arr) {
                    int modifier = field.getModifiers();
                    if (modifier == Modifier.PRIVATE ||
                            modifier == Modifier.PROTECTED ||
                            modifier == Modifier.PUBLIC) {
                        field.setAccessible(true);
                        fields.add(field);
                    }
                }
            }

            tmp = tmp.getSuperclass();
        }

        return fields;
    }
}
