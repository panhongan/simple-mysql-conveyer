package com.github.panhongan.demo.proto;

import com.github.panhongan.mysql.conveyer.bean2sql.table.TableAccess;
import com.github.panhongan.mysql.conveyer.core.AbstractDataService;
import com.github.panhongan.mysql.conveyer.core.Converter;
import com.github.panhongan.mysql.conveyer.core.WriteChecker;
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
    private PersonWriteCheckerProto personWriteCheckerProto;

    @Override
    protected TableAccess<PersonDO> getTableAccess() {
        return personTableAccess;
    }

    @Override
    protected Converter<Person, PersonDO> getConverter() {
        return personConverterProto;
    }

    @Override
    public WriteChecker<Person, PersonDO> getWriteChecker() {
        return personWriteCheckerProto;
    }
}
