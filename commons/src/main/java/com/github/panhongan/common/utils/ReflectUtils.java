package com.github.panhongan.common.utils;

import com.github.panhongan.common.function.ThrowableConsumer;
import com.github.panhongan.common.tuple.Tuple.Tuple3;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/**
 * @author panhongan
 * @since 2019.7.13
 * @version 1.0
 */

public class ReflectUtils {

    private static Map<Class, Collection<Field>> classFieldsMap = new ConcurrentHashMap<>();

    private static Map<String, Class> classMap = new ConcurrentHashMap<>();


    public static Collection<Field> getClassBeanFieldFast(Class c) {
        Collection<Field> fields = classFieldsMap.get(c);
        if (fields != null) {
            return fields;
        }

        return classFieldsMap.computeIfAbsent(c, k -> getAllFields(k));
    }

    public static Class getClassFast(String className) throws Exception {
        Class c = classMap.get(className);
        if (c != null) {
            return c;
        }

        c = Class.forName(className);
        classMap.put(className, c);
        return c;
    }

    public static <T> T newInstance(Class<T> c) throws Exception {
        return (T) getClassFast(c.getName()).newInstance();
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

    public static void setField(Object obj, Field field, String fieldName, String fieldType) {
        //ThrowableConsumer consumer = fieldSetterConsumer.get(Tuple3.of(obj,field, ));
        Object obj1 = null;
        //field.set(obj, obj1);

    }

    public static final ThrowableConsumer<Tuple3<String, Field, Integer>, Throwable> INTEGER_FIELD_SETTER = tuple -> tuple._2.set(tuple._1, tuple._3);


    public static Map<String, ThrowableConsumer> fieldSetterConsumer = new HashMap<>();

    static {
        fieldSetterConsumer.put(Integer.class.getTypeName(), INTEGER_FIELD_SETTER);
    }
}
