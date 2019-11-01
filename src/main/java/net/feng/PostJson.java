package net.feng;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class PostJson
{

    private HttpClient httpClient;
    private ResponseHandler<String> responseHandler;

    public PostJson(HttpClient httpClient,ResponseHandler<String> responseHandler)
    {
        this.httpClient = httpClient;
        this.responseHandler = responseHandler;
    }

    public ResponseCodeAndBody send( String url,String json) throws IOException
    {
        HttpPost post = new HttpPost(url);

        StringEntity jsonEntity = new StringEntity(json,ContentType.APPLICATION_JSON);
        post.setEntity(jsonEntity);
        HttpResponse response = httpClient.execute(post);

        ResponseCodeAndBody result = new ResponseCodeAndBody();
        result.setCode(response.getStatusLine().getStatusCode());
        String body = responseHandler.handleResponse(response);
        result.setBody(body);

        return result;
    }

//    public static void main( String[] args ) throws IOException
//    {
//        HttpClient httpClient = HttpClients.createDefault();
//        HttpPost httpPost = new HttpPost("http://127.0.0.1:8080/multiValue");
//        String json = "{ \n" +
//                "    \"data1\":\"1936\",\n" +
//                "     \"date1\":\"2011_11_11 11:11:11\",\n" +
//                "    \"data2\":\"fgdf\"\n" +
//                "}";
//        StringEntity jsonEntity = new StringEntity(json, ContentType.APPLICATION_JSON);
//        httpPost.setEntity(jsonEntity);
//        HttpResponse response = httpClient.execute(httpPost);
//        //response.get
//        ResponseHandler<String> responseHandler = new BasicResponseHandler();
//        String body = responseHandler.handleResponse(response);
//        System.out.println(response.getStatusLine().getStatusCode());
//        System.out.println(body);
//    }
}
