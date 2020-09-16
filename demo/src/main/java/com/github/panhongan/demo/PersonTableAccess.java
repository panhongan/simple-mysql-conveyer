package com.github.panhongan.demo;

import com.github.panhongan.bean2sql.table.AbstractTableAccess;
import com.github.panhongan.demo.PersonDO;
import org.springframework.stereotype.Service;

/**
 * @author panhongan
 * @since 2020.9.1
 * @version 1.0
 */

@Service
public class PersonTableAccess extends AbstractTableAccess<PersonDO> {

    private final static String TABLE = "t_person";

    @Override
    public String getTable() {
        return TABLE;
    }
}
