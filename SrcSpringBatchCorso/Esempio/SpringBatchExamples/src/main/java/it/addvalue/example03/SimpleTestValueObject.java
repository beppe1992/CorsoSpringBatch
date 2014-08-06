package it.addvalue.example03;

import java.io.Serializable;

public class SimpleTestValueObject implements Serializable
{
	private static final long serialVersionUID = -844885598858696611L;

	private String name;

    private long   val;

    private String surname;

    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public void setVal(long val)
    {
        this.val = val;
    }

    public long getVal()
    {
        return val;
    }

    public void setSurname(String surname)
    {
        this.surname = surname;
    }

    public String getSurname()
    {
        return surname;
    }
}
