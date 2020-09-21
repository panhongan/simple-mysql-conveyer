package com.github.panhongan.demo.proto;

import com.github.panhongan.bean2sql.condition.ConditionMaker;
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

public class TestDataServiceDemoProto {

    private static PersonDataServiceProto personDataServiceProto;

    private static PersonConverterProto personConverterProto;

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"classpath:spring/spring-context.xml"});
        context.start();

        personDataServiceProto = context.getBean(PersonDataServiceProto.class);
        personConverterProto = context.getBean(PersonConverterProto.class);

        //add();
        //modify();
        //deleteById();
        //queryById();
        queryByCondition();
        //queryByLikeCondition();
        //queryByPage();
    }

    public static void add() {
        Person person = Person.newBuilder()
                .setName("pha2")
                .setBirthday(TimeUtilsProto.date2timestamp(new Date()))
                .build();

        AddReq<Person> request = new AddReq<>();
        request.setCreatedBy("pha2");
        request.setDataType("person");
        request.setEffectTime(null);
        request.setBizObj(person);

        long id = personDataServiceProto.add(request);
        System.out.println("id = " + id);
    }

    public static void modify() {
        Person person = Person.newBuilder()
                .setName("pha66")
                .setBirthday(TimeUtilsProto.date2timestamp(new Date()))
                .build();

        ModifyReq<Person> request = new ModifyReq<>();
        request.setOriId(6L);
        request.setUpdatedBy("pha66");
        request.setDataType("person");
        request.setEffectTime(null);
        request.setNewBizObj(person);

        System.out.println(personDataServiceProto.modify(request));
    }

    public static void deleteById() {
        System.out.println(personDataServiceProto.deleteById(7L));
    }

    public static void queryById() {
        System.out.println(personDataServiceProto.queryById(6L));
    }

    public static void queryByCondition() {
        Person condition = Person.newBuilder()
                .setName("pha2")
                .build();

        QueryByConditionReq<Person> request = new QueryByConditionReq<>();
        request.setBizObjCondition(condition);

        System.out.println(personDataServiceProto.queryByCondition(request));
    }

    public static void queryByLikeCondition() {
        Person condition = Person.newBuilder()
                .build();

        Person likeObj = Person.newBuilder()
                .setName("pha")
                .setBirthday(TimeUtilsProto.date2timestamp(new Date(2020 - 1900, 8, 15)))
                .build();
        PersonDO personDO = personConverterProto.bo2do(likeObj);

        LikeCondition likeCondition = ConditionMaker.likeCondition(personDO);

        QueryByConditionReq<Person> request = new QueryByConditionReq<>();
        request.setBizObjCondition(condition);
        request.setSqlCondition(likeCondition);

        System.out.println(personDataServiceProto.queryByCondition(request));
    }

    public static void queryByPage() {
        Person condition = Person.newBuilder()
                .setName("pha1")
                .build();

        QueryByPageReq<Person> request = new QueryByPageReq<>();
        request.setBizObjCondition(condition);
        request.setCurrPage(1);
        request.setPageSize(10);

        System.out.println(personDataServiceProto.queryByPage(request));
    }
}
