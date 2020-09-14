package com.github.panhongan.demo;


import com.github.panhongan.commons.DbBase;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString(callSuper = true)
public class PersonDO extends DbBase {

    private String name;

    private Date birthday;
}
