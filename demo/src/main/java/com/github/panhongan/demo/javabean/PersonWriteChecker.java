package com.github.panhongan.demo.javabean;

import com.github.panhongan.mysql.conveyer.bean2sql.condition.impl.IdNotEqualCondition;
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
public class PersonWriteChecker implements WriteChecker<Person, PersonDO> {

    @Autowired
    private PersonTableAccess personTableAccess;

    @Autowired
    private PersonConverter personConverter;

    @Override
    public TableAccess<PersonDO> getTableAccess() {
        return personTableAccess;
    }

    @Override
    public Converter<Person, PersonDO> getConverter() {
        return personConverter;
    }

    @Override
    public void checkParametersWhenAdd(Person bizObj) throws MysqlConveyerException {
        Preconditions.checkArgument(StringUtils.isNotBlank(bizObj.getName()), "name can't be empty");
        Preconditions.checkArgument(Objects.nonNull(bizObj.getBirthday()), "birthday can't be null");
    }

    @Override
    public void checkUniq(long oriId, Person bizObj) throws MysqlConveyerException {
        PersonDO condition = new PersonDO();
        condition.setName(bizObj.getName());

        IdNotEqualCondition idNotEqualCondition = new IdNotEqualCondition(oriId);

        List<PersonDO> list = personTableAccess.queryByCondition(condition, idNotEqualCondition);
        if (CollectionUtils.isNotEmpty(list)) {
            throw new MysqlConveyerException("待插入记录已经存在, name=" + bizObj.getName());
        }
    }

    @Override
    public void checkParametersWhenUpdate(Person newBizObj) throws MysqlConveyerException {
        Preconditions.checkArgument(StringUtils.isNotBlank(newBizObj.getName()), "name can't be empty");
        Preconditions.checkArgument(Objects.nonNull(newBizObj.getBirthday()), "birthday can't be null");
    }
}
