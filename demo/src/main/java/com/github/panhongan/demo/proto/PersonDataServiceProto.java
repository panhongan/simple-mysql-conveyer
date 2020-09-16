package com.github.panhongan.demo.proto;

import com.github.panhongan.bean2sql.table.TableAccess;
import com.github.panhongan.conveyer.service.AbstractDataService;
import com.github.panhongan.conveyer.service.Converter;
import com.github.panhongan.conveyer.service.WriteOpChecker;
import com.github.panhongan.demo.PersonDO;
import com.github.panhongan.demo.PersonTableAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author panhongan
 * @since 2020.9.2
 * @version 1.0
 */

@Service
public class PersonDataServiceProto extends AbstractDataService<Person, PersonDO> {

    @Autowired
    private PersonTableAccess personTableAccess;

    @Autowired
    private PersonConverterProto personConverterProto;

    @Autowired
    private PersonWriteOpCheckerProto personWriteOpCheckerProto;

    @Override
    protected TableAccess<PersonDO> getTableAccess() {
        return personTableAccess;
    }

    @Override
    protected Converter<Person, PersonDO> getConverter() {
        return personConverterProto;
    }

    @Override
    public WriteOpChecker<Person> getWriteOpChecker() {
        return personWriteOpCheckerProto;
    }
}
