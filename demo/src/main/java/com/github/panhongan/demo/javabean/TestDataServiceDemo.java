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

public class TestDataServiceDemo {

    private static PersonDataService personDataService;

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"classpath:spring/spring-context.xml"});
        context.start();

        personDataService = context.getBean(PersonDataService.class);

        //add();
        //modify();
        //deleteById();
        //queryById();
        //queryByCondition();
        queryByLikeCondition();
        //queryByPage();
    }

    public static void add() {
        Person person = new Person();
        person.setName("pha1");
        person.setBirthday(new Date());

        AddReq<Person> request = new AddReq<>();
        request.setCreatedBy("pha");
        request.setDataType("person");
        request.setEffectTime(null);
        request.setBizObj(person);

        long id = personDataService.add(request);
        System.out.println("id = " + id);
    }

    public static void modify() {
        Person person = new Person();
        person.setName("pha6");
        person.setBirthday(new Date());

        ModifyReq<Person> request = new ModifyReq<>();
        request.setOriId(1L);
        request.setUpdatedBy("pha6");
        request.setDataType("person");
        request.setEffectTime(null);
        request.setNewBizObj(person);

        System.out.println(personDataService.modify(request));
    }

    public static void deleteById() {
        System.out.println(personDataService.deleteById(1L));
    }

    public static void queryById() {
        System.out.println(personDataService.queryById(6L));
    }

    public static void queryByCondition() {
        Person condition = new Person();
        condition.setName("pha2");

        QueryByConditionReq<Person> request = new QueryByConditionReq<>();
        request.setBizObjCondition(condition);

        System.out.println(personDataService.queryByCondition(request));
    }

    public static void queryByLikeCondition() {
        Person condition = new Person();
        //condition.setName("pha2");

        Person likeObj = new Person();
        likeObj.setName("pha");
        likeObj.setBirthday(new Date(2020 - 1900, 8, 15));
        LikeCondition likeCondition = SqlConditionMaker.likeCondition(likeObj);

        QueryByConditionReq<Person> request = new QueryByConditionReq<>();
        request.setBizObjCondition(condition);
        request.setSqlCondition(likeCondition);

        System.out.println(personDataService.queryByCondition(request));
    }

    public static void queryByPage() {
        Person condition = new Person();
        condition.setName("pha2");

        QueryByPageReq<Person> request = new QueryByPageReq<>();
        request.setBizObjCondition(condition);
        request.setCurrPage(1);
        request.setPageSize(10);

        System.out.println(personDataService.queryByPage(request));
    }
}
