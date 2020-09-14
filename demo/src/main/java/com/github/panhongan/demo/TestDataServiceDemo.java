package com.github.panhongan.demo;

import com.github.panhongan.conveyer.service.DataType;
import com.github.panhongan.conveyer.service.req.AddReq;
import com.github.panhongan.conveyer.service.req.ModifyReq;
import com.github.panhongan.conveyer.service.req.QueryByConditionReq;
import com.github.panhongan.conveyer.service.req.QueryByPageReq;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;

public class TestDataServiceDemo {

    private static PersonDataService personDataService;

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"classpath:spring/spring-context.xml"});
        context.start();

        personDataService = context.getBean(PersonDataService.class);

        add();
    }

    public static void add() {
        Person person = new Person();
        person.setName("pha");
        person.setBirthday(new Date());

        AddReq<Person> request = new AddReq<>();
        request.setCreatedBy("pha");
        request.setDataType(DataType.TEST1);
        request.setEffectTime(null);
        request.setBizObj(person);

        personDataService.add(request);
    }

    public static void modify() {
        Person person = new Person();
        person.setName("pha");
        person.setBirthday(new Date());

        ModifyReq<Person> request = new ModifyReq<>();
        request.setUpdatedBy("pha");
        request.setDataType(DataType.TEST1);
        request.setEffectTime(null);
        request.setNewBizObj(person);

        personDataService.modify(request);
    }

    public static void delete() {
        personDataService.deleteById(1L);
    }

    public static void queryById() {
        personDataService.queryById(1L);
    }

    public static void queryByCondition() {
        Person condition = new Person();
        condition.setName("pha");

        QueryByConditionReq<Person> request = new QueryByConditionReq<>();
        request.setBizObjCondition(condition);

        personDataService.queryByCondition(request);
    }

    public static void queryByPage() {
        Person condition = new Person();
        condition.setName("pha");

        QueryByPageReq<Person> request = new QueryByPageReq<>();
        request.setBizObjCondition(condition);
        request.setCurrPage(1);
        request.setPageSize(10);

        personDataService.queryByPage(request);
    }
}
