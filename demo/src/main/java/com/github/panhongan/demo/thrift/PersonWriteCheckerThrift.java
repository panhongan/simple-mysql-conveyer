package com.github.panhongan.demo.thrift;

import com.github.panhongan.mysql.conveyer.bean2sql.table.TableAccess;
import com.github.panhongan.mysql.conveyer.commons.MysqlConveyerException;
import com.github.panhongan.mysql.conveyer.core.Converter;
import com.github.panhongan.mysql.conveyer.core.WriteChecker;
import com.github.panhongan.demo.PersonDO;
import com.github.panhongan.demo.PersonTableAccess;
import com.google.common.base.Preconditions;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author panhongan
 * @since 2020.9.2
 * @version 1.0
 */

@Service
public class PersonWriteCheckerThrift implements WriteChecker<Person, PersonDO> {

    @Autowired
    private PersonTableAccess personTableAccess;

    @Autowired
    private PersonConverterThrift personConverterThrift;

    @Override
    public TableAccess<PersonDO> getTableAccess() {
        return personTableAccess;
    }

    @Override
    public Converter<Person, PersonDO> getConverter() {
        return personConverterThrift;
    }

    @Override
    public void checkParametersWhenAdd(Person bizObj) throws MysqlConveyerException {
        Preconditions.checkArgument(StringUtils.isNotBlank(bizObj.getName()), "name can't be empty");
        Preconditions.checkArgument(Objects.nonNull(bizObj.getBirthday()), "birthday can't be null");
    }

    @Override
    public void checkUniq(Person bizObj) throws MysqlConveyerException {
        PersonDO condition = new PersonDO();
        condition.setName(bizObj.getName());
        List<PersonDO> list = personTableAccess.queryByCondition(condition, null);
        if (CollectionUtils.isNotEmpty(list)) {
            throw new MysqlConveyerException("待插入记录已经存在, name=" + bizObj.getName());
        }
    }

    @Override
    public void checkParametersWhenUpdate(Person bizObj) throws MysqlConveyerException {
        Preconditions.checkArgument(StringUtils.isNotBlank(bizObj.getName()), "name can't be empty");
        Preconditions.checkArgument(Objects.nonNull(bizObj.getBirthday()), "birthday can't be null");
    }

    @Override
    public Person mergeObjectWhenUpdate(Person oldBizObj, Person newBizObj) throws MysqlConveyerException {
        Person mergedObj = new Person();
        mergedObj.setName(oldBizObj.getName());
        mergedObj.setBirthday(oldBizObj.getBirthday());

        if (Objects.nonNull(newBizObj.getName())) {
            mergedObj.setName(newBizObj.getName());
        }
        if (Objects.nonNull(newBizObj.getBirthday())) {
            mergedObj.setBirthday(newBizObj.getBirthday());
        }

        return mergedObj;
    }
}
