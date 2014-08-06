package it.addvalue.example04.impl.key;

import it.addvalue.example04.interfaces.key.SortingKeyGenerator;

import java.lang.reflect.Method;

import org.springframework.beans.factory.InitializingBean;

public class SimpleSortKeyGeneratorImpl implements SortingKeyGenerator, InitializingBean
{

    public void afterPropertiesSet() throws Exception
    {
    }

    public String getKey(Object obj)
    {
        String fieldName = "name";

        try
        {
            Method mth = obj.getClass().getMethod("get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1));

            return (String) mth.invoke(obj);
        }
        catch ( Exception e )
        {
            throw new IllegalArgumentException("Check the settings: reflection failed", e);
        }
    }
}
