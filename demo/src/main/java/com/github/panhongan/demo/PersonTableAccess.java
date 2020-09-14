package com.github.panhongan.demo;

import com.github.panhongan.bean2sql.table.AbstractTableAccess;
import org.springframework.stereotype.Service;

@Service
public class PersonTableAccess extends AbstractTableAccess<PersonDO> {

    private final static String TABLE = "t_person";

    @Override
    public String getTable() {
        return TABLE;
    }
}
