package net.feng.realAlarmDo;

public class RealAlarmDoRowsElement
{
    private Integer dealcur;
    private Integer dealtoday;
    private Integer dealyesterday;
    private String direction;
    private Integer errorcur;
    private Integer errortoday;
    private String itfname;
    private String itfno;
    private String itfserno;
    private String itftype;
    private String othersys;
    private Integer stock_msgs;

    public Integer getDealcur()
    {
        return dealcur;
    }

    public void setDealcur(Integer dealcur)
    {
        this.dealcur = dealcur;
    }

    public Integer getDealtoday()
    {
        return dealtoday;
    }

    public void setDealtoday(Integer dealtoday)
    {
        this.dealtoday = dealtoday;
    }

    public Integer getDealyesterday()
    {
        return dealyesterday;
    }

    public void setDealyesterday(Integer dealyesterday)
    {
        this.dealyesterday = dealyesterday;
    }

    public String getDirection()
    {
        return direction;
    }

    public void setDirection(String direction)
    {
        this.direction = direction;
    }

    public Integer getErrorcur()
    {
        return errorcur;
    }

    public void setErrorcur(Integer errorcur)
    {
        this.errorcur = errorcur;
    }

    public Integer getErrortoday()
    {
        return errortoday;
    }

    public void setErrortoday(Integer errortoday)
    {
        this.errortoday = errortoday;
    }

    public String getItfname()
    {
        return itfname;
    }

    public void setItfname(String itfname)
    {
        this.itfname = itfname;
    }

    public String getItfno()
    {
        return itfno;
    }

    public void setItfno(String itfno)
    {
        this.itfno = itfno;
    }

    public String getItfserno()
    {
        return itfserno;
    }

    public void setItfserno(String itfserno)
    {
        this.itfserno = itfserno;
    }

    public String getItftype()
    {
        return itftype;
    }

    public void setItftype(String itftype)
    {
        this.itftype = itftype;
    }

    public String getOthersys()
    {
        return othersys;
    }

    public void setOthersys(String othersys)
    {
        this.othersys = othersys;
    }

    public Integer getStock_msgs()
    {
        return stock_msgs;
    }

    public void setStock_msgs(Integer stock_msgs)
    {
        this.stock_msgs = stock_msgs;
    }

    @Override
    public String toString()
    {
        return "RealAlarmDoRowsElement{" +
                "dealcur=" + dealcur +
                ", dealtoday=" + dealtoday +
                ", dealyesterday=" + dealyesterday +
                ", direction='" + direction + '\'' +
                ", errorcur=" + errorcur +
                ", errortoday=" + errortoday +
                ", itfname='" + itfname + '\'' +
                ", itfno='" + itfno + '\'' +
                ", itfserno='" + itfserno + '\'' +
                ", itftype='" + itftype + '\'' +
                ", othersys='" + othersys + '\'' +
                ", stock_msgs=" + stock_msgs +
                '}';
    }
}
