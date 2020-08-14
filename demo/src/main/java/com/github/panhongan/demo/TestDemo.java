package com.github.panhongan.demo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestDemo {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"classpath:spring/spring-context.xml"});
        context.start();

        PersonTableAccess tableAccess = context.getBean(PersonTableAccess.class);
        System.out.println(tableAccess.getTable());

        // query
        Person condition = new Person();
        condition.setName("pha");
        System.out.println(tableAccess.queryByCondition(condition));
    }
}
