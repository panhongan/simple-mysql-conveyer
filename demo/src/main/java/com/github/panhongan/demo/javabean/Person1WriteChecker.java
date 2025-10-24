package com.github.panhongan.demo.javabean;

import com.github.panhongan.mysql.conveyer.bean2sql.condition.impl.EqualCondition;
import com.github.panhongan.mysql.conveyer.bean2sql.condition.impl.IdNotEqualCondition;
import com.github.panhongan.mysql.conveyer.bean2sql.condition.impl.NotEqualCondition;
import com.github.panhongan.mysql.conveyer.bean2sql.table.TableAccess;
import com.github.panhongan.mysql.conveyer.commons.MysqlConveyerException;
import com.github.panhongan.mysql.conveyer.core.Converter;
import com.github.panhongan.mysql.conveyer.core.WriteChecker;
import com.github.panhongan.demo.Person1TableAccess;
import com.github.panhongan.demo.PersonDO1;
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
public class Person1WriteChecker implements WriteChecker<Person1, PersonDO1> {

    @Autowired
    private Person1TableAccess person1TableAccess;

    @Autowired
    private Person1Converter person1Converter;

    @Override
    public TableAccess<PersonDO1> getTableAccess() {
        return person1TableAccess;
    }

    @Override
    public Converter<Person1, PersonDO1> getConverter() {
        return person1Converter;
    }

    @Override
    public void checkParametersWhenAdd(Person1 bizObj) throws MysqlConveyerException {
        Preconditions.checkArgument(StringUtils.isNotBlank(bizObj.getName()), "name can't be empty");
        Preconditions.checkArgument(Objects.nonNull(bizObj.getBirthday()), "birthday can't be null");
    }

    @Override
    public void checkUniq(long oriId, Person1 bizObj) throws MysqlConveyerException {
        PersonDO1 condition = new PersonDO1();
        condition.setName(bizObj.getName());

        IdNotEqualCondition idNotEqualCondition = new IdNotEqualCondition(oriId);

        List<PersonDO1> list = person1TableAccess.queryByCondition(condition, idNotEqualCondition);
        if (CollectionUtils.isNotEmpty(list)) {
            throw new MysqlConveyerException("待插入记录已经存在, name=" + bizObj.getName());
        }
    }

    @Override
    public void checkParametersWhenUpdate(Person1 newBizObj) throws MysqlConveyerException {
        Preconditions.checkArgument(StringUtils.isNotBlank(newBizObj.getName()), "name can't be empty");
        Preconditions.checkArgument(Objects.nonNull(newBizObj.getBirthday()), "birthday can't be null");
    }
}
