package it.addvalue.example02;

import org.springframework.batch.item.file.LineMapper;

public class SimpleTestLineMapper implements LineMapper<SimpleTestValueObject>
{

    public SimpleTestValueObject mapLine(String line,
                                         int lineNumber) throws Exception
    {
        SimpleTestValueObject simpleTestValueObject = new SimpleTestValueObject();

        simpleTestValueObject.setName(line.substring(0, 20));
        simpleTestValueObject.setVal(Long.parseLong(line.substring(20, 30)));
        simpleTestValueObject.setSurname(line.substring(30));

        return simpleTestValueObject;
    }

}