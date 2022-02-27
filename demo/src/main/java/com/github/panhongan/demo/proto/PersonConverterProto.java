package com.github.panhongan.demo.proto;

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
public class PersonConverterProto implements Converter<Person, PersonDO> {

    @Override
    public Person do2bo(PersonDO doObj) {
        if (Objects.isNull(doObj)) {
            return null;
        }

        DbBase dbBase = DbBase.newBuilder()
                .setId(doObj.getId())
                .setCreatedAt(TimeUtilsProto.date2timestamp(doObj.getCreatedAt()))
                .setCreatedBy(doObj.getCreatedBy())
                .setUpdatedAt(TimeUtilsProto.date2timestamp(doObj.getUpdatedAt()))
                .setUpdatedBy(doObj.getUpdatedBy())
                .build();

        Person bizObj = Person.newBuilder()
                .setDbBase(dbBase)
                .setName(doObj.getName())
                .setBirthday(TimeUtilsProto.date2timestamp(doObj.getBirthday()))
                .build();

        return bizObj;
    }

    @Override
    public PersonDO bo2do(Person bizObj) {
        if (Objects.isNull(bizObj)) {
            return null;
        }

        PersonDO doObj = new PersonDO();
        if (StringUtils.isNotBlank(bizObj.getName())) {
            doObj.setName(bizObj.getName());
        }

        if (bizObj.hasBirthday()) {
            doObj.setBirthday(TimeUtilsProto.timestamp2date(bizObj.getBirthday()));
        }

        if (bizObj.hasDbBase()) {
            DbBase dbBase = bizObj.getDbBase();
            doObj.setId(dbBase.getId());
            doObj.setCreatedAt(TimeUtilsProto.timestamp2date(dbBase.getCreatedAt()));
            doObj.setCreatedBy(dbBase.getCreatedBy());
            doObj.setUpdatedAt(TimeUtilsProto.timestamp2date(dbBase.getUpdatedAt()));
            doObj.setUpdatedBy(dbBase.getUpdatedBy());
        }

        return doObj;
    }
}
