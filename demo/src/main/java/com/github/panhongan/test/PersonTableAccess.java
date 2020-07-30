package com.github.panhongan.test;

import com.github.panhongan.dal.AbstractTableAccess;
import org.springframework.stereotype.Service;

@Service
public class PersonTableAccess extends AbstractTableAccess<Person> {

    private final static String TABLE = "t_person";

    @Override
    public String getTable() {
        return TABLE;
    }

    public static void main(String[] args) {
        // spring初始化
    }
}
