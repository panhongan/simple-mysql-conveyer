package com.github.panhongan.demo;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString(callSuper = true)
public class Person extends DbBase {

    private String name;

    private Date birthDay;
}
