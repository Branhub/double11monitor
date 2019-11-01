package net.feng;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.Map;

public class PostQueryString
{
    private HttpClient httpClient;
    private ResponseHandler<String> responseHandler;

    public PostQueryString(HttpClient httpClient,ResponseHandler<String> responseHandler)
    {
        this.httpClient = httpClient;
        this.responseHandler = responseHandler;
    }

    public ResponseCodeAndBody send(String url, Map<String,String> queryString) throws URISyntaxException, IOException
    {
        URIBuilder uriBuilder = new URIBuilder(url);
        Iterator<Map.Entry<String, String>> iterator = queryString.entrySet().iterator();
        if (iterator.hasNext())
        {
            Map.Entry<String,String> current = iterator.next();
            uriBuilder.setParameter(current.getKey(),current.getValue());
        }

        HttpPost post = new HttpPost(uriBuilder.build());
        HttpResponse response = httpClient.execute(post);

        ResponseCodeAndBody result = new ResponseCodeAndBody();
        result.setCode(response.getStatusLine().getStatusCode());
        String body = responseHandler.handleResponse(response);
        result.setBody(body);

        return result;
    }
}
