package com.github.panhongan.demo.javabean;

import com.github.panhongan.mysql.conveyer.core.Converter;
import com.github.panhongan.demo.PersonDO1;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author panhongan
 * @since 2020.9.1
 * @version 1.0
 */

@Service
public class Person1Converter implements Converter<Person1, PersonDO1> {

    @Override
    public Person1 do2bo(PersonDO1 doObj) {
        if (Objects.isNull(doObj)) {
            return null;
        }

        Person1 bizObj = new Person1();
        bizObj.setId(doObj.getId());
        bizObj.setName(doObj.getName());
        bizObj.setBirthday(doObj.getBirthday());

        return bizObj;
    }

    @Override
    public PersonDO1 bo2do(Person1 bizObj) {
        if (Objects.isNull(bizObj)) {
            return null;
        }

        PersonDO1 doObj = new PersonDO1();
        doObj.setId(bizObj.getId());
        doObj.setName(bizObj.getName());
        doObj.setBirthday(bizObj.getBirthday());

        return doObj;
    }
}
