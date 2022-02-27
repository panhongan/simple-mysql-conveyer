package com.github.panhongan.demo.javabean;

import com.github.panhongan.bean2sql.table.TableAccess;
import com.github.panhongan.conveyer.service.AbstractDataService;
import com.github.panhongan.conveyer.service.Converter;
import com.github.panhongan.conveyer.service.WriteChecker;
import com.github.panhongan.demo.PersonDO;
import com.github.panhongan.demo.PersonTableAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author panhongan
 * @since 2020.9.1
 * @version 1.0
 */

@Service
public class PersonDataService extends AbstractDataService<Person, PersonDO> {

    @Autowired
    private PersonTableAccess personTableAccess;

    @Autowired
    private PersonConverter personConverter;

    @Autowired
    private PersonWriteChecker personWriteOpChecker;

    @Override
    protected TableAccess<PersonDO> getTableAccess() {
        return personTableAccess;
    }

    @Override
    protected Converter<Person, PersonDO> getConverter() {
        return personConverter;
    }

    @Override
    public WriteChecker<Person, PersonDO> getWriteChecker() {
        return personWriteOpChecker;
    }
}
