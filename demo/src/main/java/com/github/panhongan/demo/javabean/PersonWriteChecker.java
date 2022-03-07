package com.github.panhongan.demo.javabean;

import com.github.panhongan.mysql.conveyer.bean2sql.table.TableAccess;
import com.github.panhongan.mysql.conveyer.commons.MysqlConveyerException;
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

    @Override
    public TableAccess<PersonDO> getTableAccess() {
        return personTableAccess;
    }

    @Override
    public void checkBeforeAdd(Person bizObj) throws MysqlConveyerException {
        Preconditions.checkArgument(StringUtils.isNotBlank(bizObj.getName()), "name can't be empty");
        Preconditions.checkArgument(Objects.nonNull(bizObj.getBirthday()), "birthday can't be null");

        PersonDO condition = new PersonDO();
        condition.setName(bizObj.getName());
        List<PersonDO> list = personTableAccess.queryByCondition(condition, null);
        if (CollectionUtils.isNotEmpty(list)) {
            throw new MysqlConveyerException("待插入记录已经存在, name=" + bizObj.getName());
        }
    }
}
