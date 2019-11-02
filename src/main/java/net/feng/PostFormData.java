package net.feng;

import org.apache.commons.codec.Charsets;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class PostFormData
{
    private HttpClient httpClient;
    private ResponseHandler<String> responseHandler;

    public PostFormData(HttpClient httpClient,ResponseHandler<String> responseHandler)
    {
        this.httpClient = httpClient;
        this.responseHandler = responseHandler;
    }

    public ResponseCodeAndBody send(String url, Map<String,String> formDataMap) throws IOException
    {

        List<NameValuePair> formData = mapToNameValuePairList(formDataMap);
        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(formData, Charsets.UTF_8);

        HttpPost post = new HttpPost(url);
        post.setEntity(formEntity);
        HttpResponse response = httpClient.execute(post);

        ResponseCodeAndBody result = new ResponseCodeAndBody();
        result.setCode(response.getStatusLine().getStatusCode());
        String body = responseHandler.handleResponse(response);
        result.setBody(body);

        return result;
    }

    private List<NameValuePair> mapToNameValuePairList(Map<String,String> map)
    {
        List<NameValuePair> result = new ArrayList<>();

        Iterator<Map.Entry<String,String>> mapIterator = map.entrySet().iterator();
        while(mapIterator.hasNext())
        {
            Map.Entry<String,String> currentEntry = mapIterator.next();
            NameValuePair currentPair = new BasicNameValuePair(currentEntry.getKey(),currentEntry.getValue());
            result.add(currentPair);
        }
        return result;
    }
}
