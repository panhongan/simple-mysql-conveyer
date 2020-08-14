package com.github.panhongan.demo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class DbBase  {

    private Long id;

    private String createdBy;

    private Date createdAt;

    private String updatedBy;

    private Date updatedAt;
}
