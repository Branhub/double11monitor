package net.feng.innersys;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.feng.PostQueryString;
import net.feng.ResponseCodeAndBody;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Innersys
{
    private PostQueryString postQueryString;
    private String url = "http://10.4.167.115:8080/itf-qry/interface/monitor/fullMoni";
    private Map<String,String> queryString = new HashMap<>();
    private String printCode = "DLV";
    private ObjectMapper objectMapper = new ObjectMapper();
    private InnersysAlarmType alarmType = InnersysAlarmType.all;


    public Innersys(HttpClient httpClient, ResponseHandler<String> responseHandler,String printCode,String alarmType)
    {
        postQueryString = new PostQueryString(httpClient,responseHandler);
        queryString.put("sysType","innersys");
        if (printCode != null && !printCode.equals(""))
        {
            this.printCode = printCode;
        }
        if (alarmType != null && !alarmType.equals(""))
        {
            try
            {
                this.alarmType = InnersysAlarmType.valueOf(alarmType);
            }
            catch (IllegalArgumentException e)
            {
                System.out.println(e);
            }
        }
    }

    public ResponseCodeAndBody send() throws IOException, URISyntaxException
    {
        return postQueryString.send(url,queryString);
    }

    public InnersysResponse processResponse(ResponseCodeAndBody responseCodeAndBody) throws JsonProcessingException
    {
        String body = responseCodeAndBody.getBody();
        InnersysResponse innersysResponse = objectMapper.readValue(body,InnersysResponse.class);
        return innersysResponse;
    }

    public InnersysListElement getPrintElement(InnersysResponse innersysResponse)
    {
        List<InnersysListElement> innersysList = innersysResponse.getList();
        for (InnersysListElement currentElement:innersysList)
        {
            if (printCode.equals(currentElement.getCode()))
            {
                return currentElement;
            }
        }
        return null;
    }

    public InnersysListElement getPrintElement() throws IOException, URISyntaxException
    {
        ResponseCodeAndBody responseCodeAndBody = send();
        InnersysResponse innersysResponse = processResponse(responseCodeAndBody);
        InnersysListElement innersysListElement = getPrintElement(innersysResponse);
        return innersysListElement;
    }

    public void print() throws IOException, URISyntaxException
    {
        InnersysListElement innersysListElement = getPrintElement();
        System.out.println(innersysListElement);
    }

    public void alarm() throws IOException, URISyntaxException
    {
        InnersysListElement innersysListElement = getPrintElement();

        switch (alarmType)
        {
            case all:
                if (shouldAlarm(innersysListElement))
                {
                    System.out.println("Innersys警告！！！！！！！！！！！！！");
                }
                System.out.println(innersysListElement);
                break;
            case onlyAlarm:
                if (shouldAlarm(innersysListElement))
                {
                    System.out.println("Innersys警告！！！！！！！！！！！！！");
                    System.out.println(innersysListElement);
                }
                else
                {
                    System.out.println("Innersys正常");
                }
                break;
        }
    }

    public Boolean shouldAlarm(InnersysListElement innersysListElement)
    {
        if
        (
                "大量".equals(innersysListElement.getError())
                || ("激增".equals(innersysListElement.getNumber()) || "锐减".equals(innersysListElement.getNumber()))
                || "积压".equals(innersysListElement.getStatus())
        )
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
