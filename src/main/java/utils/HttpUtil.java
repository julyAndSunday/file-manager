package utils;

import com.alibaba.fastjson.JSONObject;
import entity.HomeDto;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @Description:
 * @Author: July
 * @Date: 2021-11-08 19:39
 **/
public class HttpUtil {
    private static CloseableHttpClient httpClient = HttpClientBuilder.create().build();

    public static String get(String url) {
        HttpGet httpGet = new HttpGet();
        setDefaultHead(httpGet);
        try {
            httpGet.setURI(new URI(url));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return execute(httpGet);
    }

    public static String post(String url) {
        HttpPost httpPost = new HttpPost();
        setDefaultHead(httpPost);
        try {
            httpPost.setURI(new URI(url));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return execute(httpPost);
    }

    public static String post(String url, Object object) {
        HttpPost httpPost = new HttpPost();
        setDefaultHead(httpPost);
        try {
            HomeDto homeDto = (HomeDto) object;
            String jsonString = JSONObject.toJSONString(homeDto);
            httpPost.setEntity(new StringEntity(jsonString));
            httpPost.setURI(new URI(url));
        } catch (URISyntaxException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return execute(httpPost);
    }

    private static void setDefaultHead(HttpRequestBase request) {
        request.setHeader("Content-Type","application/json");
        request.setHeader("User-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:94.0) Gecko/20100101 Firefox/94.0");
    }

    private static String execute(HttpRequestBase request) {
        try {
            CloseableHttpResponse response = httpClient.execute(request);
            if (response != null) {
                return EntityUtils.toString(response.getEntity());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
