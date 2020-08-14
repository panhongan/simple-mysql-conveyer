package com.github.panhongan.demo;

import com.github.panhongan.dal.AbstractTableAccess;
import org.springframework.stereotype.Service;

@Service
public class PersonTableAccess extends AbstractTableAccess<Person> {

    private final static String TABLE = "t_person";

    @Override
    public String getTable() {
        return TABLE;
    }
}
