package com.github.panhongan.test;


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
