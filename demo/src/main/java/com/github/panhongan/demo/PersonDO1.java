package com.github.panhongan.demo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author panhongan
 * @since 2020.9.1
 * @version 1.0
 */

@Getter
@Setter
@ToString
public class PersonDO1 {

    private Long id;

    private String name;

    private Date birthday;
}
