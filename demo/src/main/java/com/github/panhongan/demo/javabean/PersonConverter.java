package com.github.panhongan.demo.javabean;

import com.github.panhongan.commons.DbBase;
import com.github.panhongan.conveyer.service.Converter;
import com.github.panhongan.demo.PersonDO;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class PersonConverter implements Converter<Person, PersonDO> {

    @Override
    public Person do2bo(PersonDO doObj) {
        if (Objects.isNull(doObj)) {
            return null;
        }

        Person bizObj = new Person();
        bizObj.setName(doObj.getName());
        bizObj.setBirthday(doObj.getBirthday());
        DbBase.assign(doObj, bizObj);

        return bizObj;
    }

    @Override
    public PersonDO bo2do(Person bizObj) {
        if (Objects.isNull(bizObj)) {
            return null;
        }

        PersonDO doObj = new PersonDO();
        doObj.setName(bizObj.getName());
        doObj.setBirthday(bizObj.getBirthday());
        DbBase.assign(bizObj, doObj);

        return doObj;
    }

    @Override
    public PersonDO emptyDO() {
        return new PersonDO();
    }
}
