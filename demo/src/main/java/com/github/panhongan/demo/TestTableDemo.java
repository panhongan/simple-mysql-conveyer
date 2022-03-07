package com.github.panhongan.demo;

import com.github.panhongan.mysql.conveyer.bean2sql.condition.SqlConditionMaker;
import com.github.panhongan.mysql.conveyer.bean2sql.condition.impl.LikeCondition;
import com.github.panhongan.mysql.conveyer.bean2sql.orderby.OrderBy;
import com.github.panhongan.mysql.conveyer.bean2sql.table.PageContext;
import com.github.panhongan.mysql.conveyer.commons.TransactionManagerEx;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;

/**
 * @author panhongan
 * @since 2020.9.1
 * @version 1.0
 */

public class TestTableDemo {

    private static PersonTableAccess tableAccess;

    private static TransactionManagerEx transactionManagerEx;

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"classpath:spring/spring-context.xml"});
        context.start();

        tableAccess = context.getBean(PersonTableAccess.class);
        System.out.println(tableAccess.getTable());

        transactionManagerEx = context.getBean(TransactionManagerEx.class);

        // testQueryByCondition();
        // testQueryByLikeCondition();
        //testQueryById();
        testQueryByPage();
        //testGetMaxId();
        //testInsert();
        //testInsert_TransactionException();
        //testUpdate();
        //testUpdate_TransactionException();
        //testDeleteById();
        //testDeleteById_TransactionException();
    }

    public static void testQueryByCondition() {
        PersonDO condition = new PersonDO();
        condition.setName("pha6");

        OrderBy orderBy = new OrderBy(OrderByCreatedTime.class, false);
        System.out.println(tableAccess.queryByCondition(condition, orderBy));
    }

    public static void testQueryByLikeCondition() {
        PersonDO condition = new PersonDO();
        // condition.setName("pha");

        PersonDO likeObj = new PersonDO();
        likeObj.setName("pha");
        // likeObj.setBirthday(new Date(2020 - 1900, 8, 15));
        LikeCondition likeCondition = SqlConditionMaker.likeCondition(likeObj);

        OrderBy orderBy = new OrderBy(OrderByCreatedTime.class, false);

        System.out.println(tableAccess.queryByCondition(condition, likeCondition, orderBy));
    }

    public static void testQueryById() {
        System.out.println(tableAccess.queryById(1L));
    }

    public static void testQueryByPage() {
        PersonDO condition = new PersonDO();
        // condition.setName("pha6");

        PageContext pageContext = new PageContext();
        pageContext.setCurrPage(1);
        pageContext.setPageSize(10);

        OrderBy orderBy = new OrderBy(OrderByCreatedTime.class, false);

        System.out.println(tableAccess.queryByPage(condition, orderBy, pageContext));
    }

    public static void testGetMaxId() {
        System.out.println(tableAccess.getMaxRowId(null));

        PersonDO condition = new PersonDO();
        condition.setName("pha1");
        System.out.println(tableAccess.getMaxRowId(SqlConditionMaker.equalCondition(condition)));
    }

    public static void testInsert() {
        PersonDO obj = new PersonDO();
        obj.setName("pha6");
        obj.setBirthday(new Date());
        obj.setCreatedBy("test");
        obj.setCreatedAt(new Date());
        obj.setUpdatedBy("test");
        obj.setUpdatedAt(new Date());

        Long id = transactionManagerEx.execute(() -> tableAccess.insert(obj));
        System.out.println(id);
    }

    public static void testInsert_TransactionException() {
        PersonDO obj = new PersonDO();
        obj.setName("pha6");
        obj.setBirthday(new Date());
        obj.setCreatedBy("test");
        obj.setCreatedAt(new Date());
        obj.setUpdatedBy("test");
        obj.setUpdatedAt(new Date());

        transactionManagerEx.execute(() -> {
            Long id = transactionManagerEx.execute(() -> tableAccess.insert(obj));
            System.out.println(id);
        });
    }

    public static void testUpdate() {
        PersonDO newObj = new PersonDO();
        newObj.setBirthday(new Date(2020 - 1900, 1, 1));
        newObj.setCreatedBy("test-update1");
        newObj.setUpdatedBy("test-update2");
        newObj.setUpdatedAt(new Date());

        int affectedRows = transactionManagerEx.execute(() -> tableAccess.update(1L, newObj));
        System.out.println(affectedRows);
    }

    public static void testUpdate_TransactionException() {
        PersonDO newObj = new PersonDO();
        newObj.setBirthday(new Date(2020 - 1900, 1, 1));
        newObj.setCreatedBy("test-update1");
        newObj.setUpdatedBy("test-update1");
        newObj.setUpdatedAt(new Date());

        transactionManagerEx.execute(() -> {
            int affectedRows = transactionManagerEx.execute(() -> tableAccess.update(7L, newObj));
            System.out.println(affectedRows);
        });
    }

    public static void testDeleteById() {
        int affectedRows = transactionManagerEx.execute(() -> tableAccess.deleteById(4L));
        System.out.println(affectedRows);
    }

    public static void testDeleteById_TransactionException() {
        transactionManagerEx.execute(() -> {
            int affectedRows = transactionManagerEx.execute(() -> tableAccess.deleteById(3L));
            System.out.println(affectedRows);
        });
    }
}
