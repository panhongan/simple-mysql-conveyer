package com.github.panhongan.demo.javabean;

import com.github.panhongan.bean2sql.condition.SqlConditionMaker;
import com.github.panhongan.bean2sql.condition.impl.LikeCondition;
import com.github.panhongan.conveyer.service.req.AddReq;
import com.github.panhongan.conveyer.service.req.ModifyReq;
import com.github.panhongan.conveyer.service.req.QueryByConditionReq;
import com.github.panhongan.conveyer.service.req.QueryByPageReq;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;

/**
 * @author panhongan
 * @since 2020.9.1
 * @version 1.0
 */

public class TestDataServiceDemo1 {

    private static Person1DataService person1DataService;

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"classpath:spring/spring-context.xml"});
        context.start();

        person1DataService = context.getBean(Person1DataService.class);

        // add();
        // modify();
        // deleteById();
        // queryById();
        // queryByCondition();
        // queryByLikeCondition();
        queryByPage();
    }

    public static void add() {
        Person1 person = new Person1();
        person.setName("pha1");
        person.setBirthday(new Date());

        AddReq<Person1> request = new AddReq<>();
        request.setBizObj(person);

        long id = person1DataService.add(request);
        System.out.println("id = " + id);
    }

    public static void modify() {
        Person1 person = new Person1();
        person.setName("pha12");
        person.setBirthday(new Date());

        ModifyReq<Person1> request = new ModifyReq<>();
        request.setOriId(1L);
        request.setNewBizObj(person);

        System.out.println(person1DataService.modify(request));
    }

    public static void deleteById() {
        System.out.println(person1DataService.deleteById(1L));
    }

    public static void queryById() {
        System.out.println(person1DataService.queryById(2L));
    }

    public static void queryByCondition() {
        Person1 condition = new Person1();
        condition.setName("pha1");

        QueryByConditionReq<Person1> request = new QueryByConditionReq<>();
        request.setBizObjCondition(condition);

        System.out.println(person1DataService.queryByCondition(request));
    }

    public static void queryByLikeCondition() {
        Person1 condition = new Person1();
        // condition.setName("pha1");

        Person1 likeObj = new Person1();
        likeObj.setName("pha");
        LikeCondition likeCondition = SqlConditionMaker.likeCondition(likeObj);

        QueryByConditionReq<Person1> request = new QueryByConditionReq<>();
        request.setBizObjCondition(condition);
        request.setSqlCondition(likeCondition);

        System.out.println(person1DataService.queryByCondition(request));
    }

    public static void queryByPage() {
        Person1 condition = new Person1();
        condition.setName("pha1");

        QueryByPageReq<Person1> request = new QueryByPageReq<>();
        request.setBizObjCondition(condition);
        request.setCurrPage(1);
        request.setPageSize(10);

        System.out.println(person1DataService.queryByPage(request));
    }
}
