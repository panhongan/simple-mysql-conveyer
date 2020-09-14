package com.github.panhongan.demo;

import com.github.panhongan.bean2sql.table.TableAccess;
import com.github.panhongan.conveyer.service.AbstractDataService;
import com.github.panhongan.conveyer.service.Converter;
import com.github.panhongan.conveyer.service.WriteOpChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonDataService extends AbstractDataService<Person, PersonDO> {

    @Autowired
    private PersonTableAccess personTableAccess;

    @Autowired
    private PersonConverter personConverter;

    @Autowired
    private PersonWriteOpChecker personWriteOpChecker;

    @Override
    protected TableAccess<PersonDO> getTableAccess() {
        return personTableAccess;
    }

    @Override
    protected Converter<Person, PersonDO> getConverter() {
        return personConverter;
    }

    @Override
    public WriteOpChecker<Person> getWriteOpChecker() {
        return personWriteOpChecker;
    }
}
