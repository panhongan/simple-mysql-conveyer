package com.github.panhongan.demo;

import com.github.panhongan.bean2sql.table.AbstractTableAccess;
import org.springframework.stereotype.Service;

/**
 * @author panhongan
 * @since 2020.9.1
 * @version 1.0
 */

@Service
public class Person1TableAccess extends AbstractTableAccess<PersonDO1> {

    private final static String TABLE = "t_person1";

    @Override
    public String getTable() {
        return TABLE;
    }

    @Override
    public PersonDO1 emptyDO() {
        return new PersonDO1();
    }
}
