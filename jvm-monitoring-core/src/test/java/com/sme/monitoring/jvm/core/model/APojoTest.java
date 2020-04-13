package com.sme.monitoring.jvm.core.model;

import java.lang.reflect.ParameterizedType;

import org.junit.Assert;
import org.junit.Test;

import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.rule.impl.GetterMustExistRule;
import com.openpojo.validation.rule.impl.SetterMustExistRule;
import com.openpojo.validation.test.impl.DefaultValuesNullTester;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;

/**
 * Pojo tester based on <a href="https://github.com/OpenPojo/openpojo">openpojo</a> library.
 */
abstract class APojoTest<T> extends Assert
{
    @Test
    public void testPojo() throws Exception
    {
        Validator validator = ValidatorBuilder.create()
                .with(new GetterMustExistRule())
                .with(new SetterMustExistRule())
                .with(new SetterTester())
                .with(new GetterTester())
                .with(new DefaultValuesNullTester())
                .build();

        @SuppressWarnings("unchecked")
        Class<T> clazz = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];

        validator.validate(PojoClassFactory.getPojoClass(clazz));
    }
}
