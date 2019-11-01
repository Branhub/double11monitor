package net.feng.realAlarmDo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.feng.PostFormData;
import net.feng.ResponseCodeAndBody;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RealAlarmDo
{
    private String url = "http://10.4.167.115:8080/itf-qry/interface/monitor/realAlarm.do";
    private PostFormData postFormData;
    private ObjectMapper objectMapper = new ObjectMapper();
    private Map<String,String> formData = new HashMap<>();
    private Integer errorTodayThreshold = 1000;
    private Integer stockMsgsThreshold = 10000;

    public RealAlarmDo(
            HttpClient httpClient,
            ResponseHandler<String> responseHandler,
            Map<String,String> formData,
            Integer errorTodayThreshold,
            Integer stockMsgsThreshold)
    {
        postFormData = new PostFormData(httpClient,responseHandler);
        if (formData != null)
        {
            this.formData = formData;
        }
        else
        {
            this.formData.put("sysName","innersys");
            this.formData.put("systemname","投递子系统");
        }

        if (errorTodayThreshold != null)
        {
            this.errorTodayThreshold = errorTodayThreshold;
        }

        if (stockMsgsThreshold != null)
        {
            this.stockMsgsThreshold = stockMsgsThreshold;
        }
    }

    public ResponseCodeAndBody send(Map<String,String> formData) throws IOException
    {
        return postFormData.send(url,formData);
    }

    public RealAlarmDoResponse processResponse(ResponseCodeAndBody responseCodeAndBody) throws JsonProcessingException
    {
        String body = responseCodeAndBody.getBody();
        RealAlarmDoResponse result = objectMapper.readValue(body,RealAlarmDoResponse.class);
        return result;
    }

    public void alarm() throws IOException
    {
        ResponseCodeAndBody responseCodeAndBody = send(formData);
        RealAlarmDoResponse realAlarmDoResponse = processResponse(responseCodeAndBody);
        List<RealAlarmDoRowsElement> rows = realAlarmDoResponse.getRows();
        for (RealAlarmDoRowsElement currentElement: rows)
        {
            if (shouldAlarm(currentElement))
            {
                System.out.println("警告！！！！！！！！！！");
                System.out.println(currentElement);
            }
        }

    }

    public Boolean shouldAlarm(RealAlarmDoRowsElement element)
    {
        Integer errorToday = element.getErrortoday();
        Integer stockMsgs = element.getStock_msgs();
        return (errorToday > errorTodayThreshold || stockMsgs > stockMsgsThreshold);
    }


}
