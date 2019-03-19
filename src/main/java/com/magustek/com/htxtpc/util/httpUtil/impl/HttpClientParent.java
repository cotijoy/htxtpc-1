package com.magustek.com.htxtpc.util.httpUtil.impl;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

@Slf4j
public abstract class HttpClientParent {
    @Value("${zconnection.type}")
    protected String odataType;
    @Value("${zconnection.odataIp}")
    protected String odataIp;
    @Value("${zconnection.odataPort}")
    protected String odataPort;
    @Value("${zconnection.odataUser}")
    protected String odataUser;
    @Value("${zconnection.odataPasswd}")
    protected String odataPasswd;
    @Value("${zconnection.odataClient}")
    protected String odataClient;
    @Value("${zconnection.esbIp}")
    protected String esbIp;

    String checkResponse(CloseableHttpClient httpclient, HttpRequestBase http) throws Exception{
        try (CloseableHttpResponse response = httpclient.execute(http)) {
            // 判断返回是不是200
            int status = response.getStatusLine().getStatusCode();
            log.info("响应状态：" + status);
            if (status >= HttpStatus.SC_OK && status < HttpStatus.SC_MULTIPLE_CHOICES) {
                HttpEntity entity = response.getEntity();
                if (entity != null)
                    return EntityUtils.toString(entity, Charset.forName("UTF-8"));
                else
                    return String.valueOf(status);
            } else {
                throw new Exception("---------调用httpClient出错返回状态值" + status);
            }
        }
    }
    String checkResponseWithoutStatus(CloseableHttpClient httpclient, HttpEntityEnclosingRequestBase http) throws Exception{
        try (CloseableHttpResponse response = httpclient.execute(http)) {
            // 判断返回是不是200
            int status = response.getStatusLine().getStatusCode();
            if (status >= HttpStatus.SC_OK && status < HttpStatus.SC_MULTIPLE_CHOICES) {
                HttpEntity entity1 = response.getEntity();
                return EntityUtils.toString(entity1, Charset.forName("UTF-8"));
            }
            return null;
        }
    }

    Map<String, Object> getMap(String data) {
        if (!data.isEmpty()) {
            @SuppressWarnings("unchecked")
            Map<String, Object> dataMap = (Map<String, Object>) JSON.parseObject(data).get("d");
            dataMap.remove("__metadata");
            return dataMap;
        } else {
            return null;
        }
    }

    List<Map<String, Object>> getList(String data){
        if (!data.isEmpty()) {
            @SuppressWarnings("unchecked")
            Map<String, Object> dataMap = (Map<String, Object>) JSON.parseObject(data).get("d");

            @SuppressWarnings("unchecked")
            List<Map<String, Object>> ListMap = (List<Map<String, Object>>) dataMap.get("results");
            for (Map<String, Object> m : ListMap) {
                m.remove("__metadata");
            }
            return ListMap;
        } else {
            return null;
        }
    }

    String checkStatus(CloseableHttpClient httpclient, HttpRequestBase http) throws Exception {
        try (CloseableHttpResponse response = httpclient.execute(http)) {
            // 判断返回是不是200
            int status = response.getStatusLine().getStatusCode();
            log.info("响应状态码：" + status);
            log.info("请求url:" + http.getURI());
            if (status >= HttpStatus.SC_OK && status < HttpStatus.SC_MULTIPLE_CHOICES) {
                return "true";
            } else {
                throw new Exception("---------调用httpClient出错返回状态值" + status);
            }
        }
    }
}
