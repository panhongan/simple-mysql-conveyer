package com.github.panhongan.demo.thrift;

import com.github.panhongan.bean2sql.condition.SqlConditionMaker;
import com.github.panhongan.bean2sql.condition.sql.LikeCondition;
import com.github.panhongan.conveyer.service.req.AddReq;
import com.github.panhongan.conveyer.service.req.ModifyReq;
import com.github.panhongan.conveyer.service.req.QueryByConditionReq;
import com.github.panhongan.conveyer.service.req.QueryByPageReq;
import com.github.panhongan.demo.PersonDO;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;

/**
 * @author panhongan
 * @since 2020.9.12
 * @version 1.0
 */

public class TestDataServiceDemoThrift {

    private static PersonDataServiceThrift personDataServiceThrift;

    private static PersonConverterThrift personConverterThrift;

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"classpath:spring/spring-context.xml"});
        context.start();

        personDataServiceThrift = context.getBean(PersonDataServiceThrift.class);
        personConverterThrift = context.getBean(PersonConverterThrift.class);

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
        person.setName("pha3");
        person.setBirthday(TimeUtilsThrift.date2timestamp(new Date()));

        AddReq<Person> request = new AddReq<>();
        request.setCreatedBy("pha3");
        request.setDataType("person");
        request.setEffectTime(null);
        request.setBizObj(person);

        long id = personDataServiceThrift.add(request);
        System.out.println("id = " + id);
    }

    public static void modify() {
        Person person = new Person();
        person.setName("pha33");
        person.setBirthday(TimeUtilsThrift.date2timestamp(new Date()));

        ModifyReq<Person> request = new ModifyReq<>();
        request.setOriId(15L);
        request.setUpdatedBy("pha33");
        request.setDataType("person");
        request.setEffectTime(null);
        request.setNewBizObj(person);

        System.out.println(personDataServiceThrift.modify(request));
    }

    public static void deleteById() {
        System.out.println(personDataServiceThrift.deleteById(11L));
    }

    public static void queryById() {
        System.out.println(personDataServiceThrift.queryById(6L));
    }

    public static void queryByCondition() {
        Person condition = new Person();
        condition.setName("pha2");

        QueryByConditionReq<Person> request = new QueryByConditionReq<>();
        request.setBizObjCondition(condition);

        System.out.println(personDataServiceThrift.queryByCondition(request));
    }

    public static void queryByLikeCondition() {
        Person condition = new Person();

        Person likeObj = new Person();
        likeObj.setName("pha");
        likeObj.setBirthday(TimeUtilsThrift.date2timestamp(new Date(2020 - 1900, 8, 16)));
        PersonDO personDO = personConverterThrift.bo2do(likeObj);

        LikeCondition likeCondition = SqlConditionMaker.likeCondition(personDO);

        QueryByConditionReq<Person> request = new QueryByConditionReq<>();
        request.setBizObjCondition(condition);
        request.setSqlCondition(likeCondition);

        System.out.println(personDataServiceThrift.queryByCondition(request));
    }

    public static void queryByPage() {
        Person condition = new Person();
        condition.setName("pha1");

        QueryByPageReq<Person> request = new QueryByPageReq<>();
        request.setBizObjCondition(condition);
        request.setCurrPage(1);
        request.setPageSize(10);

        System.out.println(personDataServiceThrift.queryByPage(request));
    }
}
