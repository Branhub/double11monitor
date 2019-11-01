package net.feng.innersys;

import java.util.List;

public class InnersysResponse
{
    private List<InnersysListElement> list;
    private InnersysTotal total;

    public List<InnersysListElement> getList()
    {
        return list;
    }

    public void setList(List<InnersysListElement> list)
    {
        this.list = list;
    }

    public InnersysTotal getTotal()
    {
        return total;
    }

    public void setTotal(InnersysTotal total)
    {
        this.total = total;
    }
}
