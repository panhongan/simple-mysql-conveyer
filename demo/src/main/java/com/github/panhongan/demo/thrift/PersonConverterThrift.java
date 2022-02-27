package com.github.panhongan.demo.thrift;

import com.github.panhongan.conveyer.service.Converter;
import com.github.panhongan.demo.PersonDO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author panhongan
 * @since 2020.9.2
 * @version 1.0
 */

@Service
public class PersonConverterThrift implements Converter<Person, PersonDO> {

    @Override
    public Person do2bo(PersonDO doObj) {
        if (Objects.isNull(doObj)) {
            return null;
        }

        DbBase dbBase = new DbBase();
        dbBase.setId(doObj.getId());
        dbBase.setCreated_at(TimeUtilsThrift.date2timestamp(doObj.getCreatedAt()));
        dbBase.setCreated_by(doObj.getCreatedBy());
        dbBase.setUpdated_at(TimeUtilsThrift.date2timestamp(doObj.getUpdatedAt()));
        dbBase.setUpdated_by(doObj.getUpdatedBy());

        Person bizObj = new Person();
        bizObj.setDb_base(dbBase);
        bizObj.setName(doObj.getName());
        bizObj.setBirthday(TimeUtilsThrift.date2timestamp(doObj.getBirthday()));

        return bizObj;
    }

    @Override
    public PersonDO bo2do(Person bizObj) {
        if (Objects.isNull(bizObj)) {
            return null;
        }

        PersonDO doObj = new PersonDO();
        if (bizObj.isSetName() && StringUtils.isNotBlank(bizObj.getName())) {
            doObj.setName(bizObj.getName());
        }

        if (bizObj.isSetBirthday()) {
            doObj.setBirthday(TimeUtilsThrift.timestamp2date(bizObj.getBirthday()));
        }

        if (bizObj.isSetDb_base()) {
            DbBase dbBase = bizObj.getDb_base();
            if (Objects.nonNull(dbBase)) {
                doObj.setId(dbBase.getId());
                doObj.setCreatedAt(TimeUtilsThrift.timestamp2date(dbBase.getCreated_at()));
                doObj.setCreatedBy(dbBase.getCreated_by());
                doObj.setUpdatedAt(TimeUtilsThrift.timestamp2date(dbBase.getUpdated_at()));
                doObj.setUpdatedBy(dbBase.getUpdated_by());
            }
        }

        return doObj;
    }
}
