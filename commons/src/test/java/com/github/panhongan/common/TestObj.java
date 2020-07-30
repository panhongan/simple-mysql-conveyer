package com.github.panhongan.common;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

/**
 * @author panhongan
 * @since 2019.7.14
 * @version 1.0
 */

public class TestObj {
    @NotNull
    @NotBlank
    public String name;

    public Integer age;

    public Float height;

    public Double weight;
}
