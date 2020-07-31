package com.github.panhongan.spring;

import com.google.common.base.Preconditions;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

public class InjectUtils {

    public static void inject(Object target, Object inject, String... injectNames) {
        String injectName = null;

        if (ArrayUtils.isNotEmpty(injectNames)) {
            injectName = injectNames[0];
        } else {
            String simpleName = inject.getClass().getSimpleName();
            simpleName = StringUtils.substringBefore(simpleName, "$");
            injectName = StringUtils.lowerCase(simpleName.substring(0, 1)).concat(simpleName.substring(1));
        }

        Preconditions.checkArgument(StringUtils.isNotEmpty(injectName));


    }
}
