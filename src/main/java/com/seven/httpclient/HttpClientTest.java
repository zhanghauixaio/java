package com.seven.httpclient;

import lombok.SneakyThrows;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;

public class HttpClientTest {
    private CloseableHttpClient httpClient = HttpClientBuilder.create().build();
    private RequestConfig requstConfig = RequestConfig.custom()

            .build();
    private URI uri = new URIBuilder().setScheme("https").setHost("www.baidu.com").setPath("/").build();

    public HttpClientTest() throws URISyntaxException {
    }

    @SneakyThrows
    @Test
    public void testGet() {
        HttpGet httpGet = new HttpGet();
        httpGet.setURI(new URI("https://www.baidu.com"));
        CloseableHttpResponse response = httpClient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        InputStream content = entity.getContent();
        System.out.println(EntityUtils.toString(entity));
        response.close();
        httpClient.close();
    }
    @SneakyThrows
    @Test
    public void testPost() {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        httpClient = HttpClients.custom().setConnectionManager(connectionManager).build();
        RequestBuilder requestBuilder = RequestBuilder.post();
        requestBuilder.setUri(new URI("https://www.baidu.com"));
        requestBuilder.setConfig(requstConfig);
        CloseableHttpResponse response = httpClient.execute(requestBuilder.build());
        HttpEntity entity = response.getEntity();
        System.out.println(EntityUtils.toString(entity));
        httpClient.close();
    }
}
