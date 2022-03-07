package com.github.panhongan.demo.javabean;

import com.github.panhongan.mysql.conveyer.bean2sql.table.TableAccess;
import com.github.panhongan.mysql.conveyer.core.AbstractDataService;
import com.github.panhongan.mysql.conveyer.core.Converter;
import com.github.panhongan.mysql.conveyer.core.WriteChecker;
import com.github.panhongan.demo.Person1TableAccess;
import com.github.panhongan.demo.PersonDO1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author panhongan
 * @since 2020.9.1
 * @version 1.0
 */

@Service
public class Person1DataService extends AbstractDataService<Person1, PersonDO1> {

    @Autowired
    private Person1TableAccess person1TableAccess;

    @Autowired
    private Person1Converter person1Converter;

    @Autowired
    private Person1WriteChecker person1WriteOpChecker;

    @Override
    protected TableAccess<PersonDO1> getTableAccess() {
        return person1TableAccess;
    }

    @Override
    protected Converter<Person1, PersonDO1> getConverter() {
        return person1Converter;
    }

    @Override
    public WriteChecker<Person1, PersonDO1> getWriteChecker() {
        return person1WriteOpChecker;
    }
}
