package com.github.panhongan.demo.javabean;

import com.github.panhongan.commons.MysqlConveyerException;
import com.github.panhongan.conveyer.service.WriteOpChecker;
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
public class PersonWriteOpChecker implements WriteOpChecker<Person> {

    @Autowired
    private PersonTableAccess personTableAccess;

    @Override
    public void checkBeforeAdd(Person bizObj) throws MysqlConveyerException {
        Preconditions.checkArgument(StringUtils.isNotBlank(bizObj.getName()), "name can't be empty");
        Preconditions.checkArgument(Objects.nonNull(bizObj.getBirthday()), "birthday can't be null");

        PersonDO condition = new PersonDO();
        condition.setName(bizObj.getName());
        List<PersonDO> list = personTableAccess.queryByCondition(condition);
        if (CollectionUtils.isNotEmpty(list)) {
            throw new MysqlConveyerException("待插入记录已经存在, name=" + bizObj.getName());
        }
    }

    @Override
    public void checkBeforeModify(long oriId, Person newBizObj) throws MysqlConveyerException {
        PersonDO condition = new PersonDO();
        condition.setId(oriId);
        List<PersonDO> list = personTableAccess.queryByCondition(condition);
        if (CollectionUtils.isEmpty(list)) {
            throw new MysqlConveyerException("待修改记录不存在, id=" + oriId);
        }
    }

    @Override
    public void checkBeforeDelete(long oriId) throws MysqlConveyerException {
        PersonDO condition = new PersonDO();
        condition.setId(oriId);
        List<PersonDO> list = personTableAccess.queryByCondition(condition);
        if (CollectionUtils.isEmpty(list)) {
            throw new MysqlConveyerException("待删除记录不存在, id=" + oriId);
        }
    }
}
