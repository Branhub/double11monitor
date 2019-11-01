package net.feng.realAlarmDo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

public class RealAlarmDoResponse
{
    private List<RealAlarmDoRowsElement> rows;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date time;
    private Integer total;

    public List<RealAlarmDoRowsElement> getRows()
    {
        return rows;
    }

    public void setRows(List<RealAlarmDoRowsElement> rows)
    {
        this.rows = rows;
    }

    public Date getTime()
    {
        return time;
    }

    public void setTime(Date time)
    {
        this.time = time;
    }

    public Integer getTotal()
    {
        return total;
    }

    public void setTotal(Integer total)
    {
        this.total = total;
    }
}
