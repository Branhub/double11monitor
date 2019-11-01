package net.feng.innersys;

public class InnersysListElement
{
    private String code;
    private String error;
    private String name;
    private String number;
    private String status;

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getError()
    {
        return error;
    }

    public void setError(String error)
    {
        this.error = error;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getNumber()
    {
        return number;
    }

    public void setNumber(String number)
    {
        this.number = number;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    @Override
    public String toString()
    {
        return "InnersysListElement{" +
                "code='" + code + '\'' +
                ", error='" + error + '\'' +
                ", name='" + name + '\'' +
                ", number='" + number + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
