package it.addvalue.example03;

import org.springframework.batch.item.file.transform.LineAggregator;

public class SimpleTestLineAggregator implements LineAggregator<SimpleTestValueObject>
{

    public String aggregate(SimpleTestValueObject item)
    {
        return item.getName() + tenDigits(item.getVal()) + item.getSurname();
    }

    private String tenDigits(long val)
    {
        String str = "0000000000000" + val;

        return str.substring(str.length() - 10);
    }

}