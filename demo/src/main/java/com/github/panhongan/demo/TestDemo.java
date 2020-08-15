package com.github.panhongan.demo;

import com.github.panhongan.condition.sql.EqualCondition;
import com.github.panhongan.dal.core.TransactionManagerEx;
import com.github.panhongan.dal.page.PageContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;

public class TestDemo {

    private static PersonTableAccess tableAccess;

    private static TransactionManagerEx transactionManagerEx;

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"classpath:spring/spring-context.xml"});
        context.start();

        tableAccess = context.getBean(PersonTableAccess.class);
        System.out.println(tableAccess.getTable());

        transactionManagerEx = context.getBean(TransactionManagerEx.class);

        testQueryByCondition();
        //testQueryById();
        //testQueryByPage();
        //testGetMaxId();
        //testInsert();
        //testUpdate();
        //testDeleteById();
    }

    public static void testQueryByCondition() {
        Person condition = new Person();
        condition.setName("pha1");
        System.out.println(tableAccess.queryByCondition(condition));
    }

    public static void testQueryById() {
        Person condition = new Person();
        condition.setId(2L);
        System.out.println(tableAccess.queryByCondition(condition));
    }

    public static void testQueryByPage() {
        Person condition = new Person();
        condition.setName("pha");

        PageContext pageContext = new PageContext();
        pageContext.setCurrPage(1);
        pageContext.setPageSize(10);
        System.out.println(tableAccess.queryByPage(condition, pageContext));
    }

    public static void testGetMaxId() {
        System.out.println(tableAccess.getMaxRowId(null));

        Person condition = new Person();
        condition.setName("pha1");
        System.out.println(tableAccess.getMaxRowId(EqualCondition.builder().obj(condition).build()));
    }

    public static void testInsert() {
        Person obj = new Person();
        obj.setName("pha4");
        obj.setBirthday(new Date());
        obj.setCreatedBy("test");
        obj.setCreatedAt(new Date());
        obj.setUpdatedBy("test");
        obj.setUpdatedAt(new Date());

        Long id = transactionManagerEx.execute(() -> tableAccess.insert(obj));
        System.out.println(id);
    }

    public static void testUpdate() {
        Person newObj = new Person();
        newObj.setBirthday(new Date(2020 - 1900, 1, 1));
        newObj.setCreatedBy("test-update");
        newObj.setUpdatedBy("test-update");
        newObj.setUpdatedAt(new Date());

        int affectedRows = transactionManagerEx.execute(() -> tableAccess.update(7L, newObj));
        System.out.println(affectedRows);
    }

    public static void testDeleteById() {
        int affectedRows = transactionManagerEx.execute(() -> tableAccess.deleteById(3L));
        System.out.println(affectedRows);
    }
}
