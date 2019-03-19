package com.magustek.com.htxtpc.util.httpUtil.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Strings;
import com.magustek.com.htxtpc.config.HttpConnectConfig;
import com.magustek.com.htxtpc.util.ContextUtils;
import com.magustek.com.htxtpc.util.OdataUtils;
import com.magustek.com.htxtpc.util.httpUtil.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.net.BCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class HttpUtilsImpl implements HttpUtils {
    private final HttpConnectConfig config;
    private final RestTemplate restTemplate;

    private final String odataTokenHeader = "X-CSRF-TOKEN";
    private String odataString = "/sap/opu/odata/sap/";
    private String odataClient = "&sap-client=";

    @Autowired
    public HttpUtilsImpl(HttpConnectConfig config, RestTemplate restTemplate) {
        this.config = config;
        this.restTemplate = restTemplate;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Map<String, Object> getMapByUrl(String url, Object params, HttpMethod method) throws Exception{
        String data = getStringByUrl(url, params, method);
        if (!data.isEmpty()) {
            Map<String, Object> dataMap = (Map<String, Object>) JSON.parseObject(data).get("d");
            dataMap.remove("__metadata");
            return dataMap;
        } else {
            return null;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Map<String,Object>> getListByUrl(String url, Object params, HttpMethod method) throws Exception{
        String data = getStringByUrl(url, params, method);
        if (!data.isEmpty()) {
            Map<String, Object> dataMap = (Map<String, Object>) JSON.parseObject(data).get("d");
            List<Map<String, Object>> ListMap = (List<Map<String, Object>>) dataMap.get("results");
            for (Map<String, Object> m : ListMap) {
                m.remove("__metadata");
            }
            return ListMap;
        } else {
            return null;
        }
    }

    @Override
    public String getResultByUrl(String url, Object params, HttpMethod method){
        try {
            return getStringByUrl(url, params, method);
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public void downloadFile(HttpServletRequest request, String url, HttpServletResponse response) throws Exception {
        StringBuilder sb = handleURL(url, HttpMethod.GET);
        HttpHeaders headers = handleHeader();
        ResponseEntity<byte[]> httpResponse = restTemplate.exchange(sb.toString(), HttpMethod.GET, new HttpEntity<>(headers), byte[].class);
        if(httpResponse.getStatusCode().is2xxSuccessful()){
            byte[] body = httpResponse.getBody();
            if(body==null || body.length==0){
                response.sendError(500, "文件下载错误！");
                log.error("文件下载错误！url:{}",url);
                return;
            }
            String name;
            // 获得文件名
            name = getFileName(httpResponse);
            if (null == name) {
                // 获取随机文件名
                name = getRandomFileName();
            }

            // 确定浏览器 , Chrome 谷歌，firefox 火狐 、 MSIE ie浏览
            String userAgent = request.getHeader("User-Agent");
            // IE和谷歌处理方式一样
            if (userAgent.contains("Chrome") || userAgent.contains("MSIE")) {
                name = URLEncoder.encode(name, "UTF-8");
            }
            // 火狐 --使用的base64编码
            if (userAgent.contains("Firefox")) {
                name = new BCodec().encode(name);
            }
            response.setHeader("content-disposition", "attachment;filename=" + name);
            response.getOutputStream().write(body);
        }
    }

    @Override
    public String uploadFile(String url, MultipartFile[] files) {

        HttpHeaders headers = handleHeader();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultipartFile[]> httpEntity = new HttpEntity<>(files, headers);
        ResponseEntity<Object> responseEntity = restTemplate.postForEntity(url,httpEntity,Object.class);
        if(responseEntity.getStatusCode().is2xxSuccessful()){
            return "200";
        }
        log.error("上传文件失败！");
        return responseEntity.getStatusCodeValue()+"";
    }

    // 获取随机文件名
    private String getRandomFileName() {
        return String.valueOf(System.currentTimeMillis());
    }

    // 获得文件名
    private String getFileName(ResponseEntity<byte[]> response) {
        String filename  = "";
        MediaType contentType = response.getHeaders().getContentType();
        if(contentType != null){
            filename = contentType.getSubtype();
        }
        if (Strings.isNullOrEmpty(filename)) {
            filename = getRandomFileName();
        }
        return filename;
    }

    /**
     * http请求返回一个String
     * @param url 请求路径
     * @param params 请求参数
     * @param method 请求方法
     * @return 返回一个String 请求失败返回一个null
     */
    private String getStringByUrl(String url, Object params, HttpMethod method) throws Exception{
        //设置http头
        HttpHeaders headers = handleHeader();

        StringBuilder urlString = handleURL(url, method);

        if(params!=null){
            log.info("--- params :"+ JSON.toJSONString(params));
        }
        long start = System.currentTimeMillis();
        //执行http请求
        HttpEntity<Object> requestEntity = new HttpEntity<>(params, headers);
        ResponseEntity<String> response;
        response = restTemplate.exchange(
                urlString.toString(),
                method,
                requestEntity,
                String.class);
        log.info("--- "+ ContextUtils.getUserName()+" odata调用耗时：【"+(System.currentTimeMillis()-start)/1000.0+"】"+method.name()+"  "+urlString.toString());
        return checkResponse(response);
    }

    /**
     * 获取odata的token
     * */
    private String getOdataToken(){
        //拼接获取token的url
        StringBuilder tokenString = new StringBuilder();
        tokenString.append(config.getOdataIp()).append(":").append(config.getOdataPort())
                .append(odataString)
                .append(OdataUtils.Token)
                .append("?")
                .append(odataClient)
                .append(config.getOdataClient());
        //设置header
        HttpHeaders headers = new HttpHeaders();
        headers.add(odataTokenHeader, "Fetch");
        //设置账号密码
        restTemplate.getInterceptors().add(
                new BasicAuthenticationInterceptor(config.getOdataUser(), config.getOdataPass()));
        ResponseEntity<String> responseEntity;
        try{
            //执行odata调用
            responseEntity = restTemplate.exchange(tokenString.toString(), HttpMethod.GET, new HttpEntity(headers), String.class);
        }catch (HttpServerErrorException e){
            log.error("获取token失败：{}", e.getMessage());
            return "";
        }
        //从http header中获取token
        List<String> headerList = responseEntity.getHeaders().get(odataTokenHeader);
        if(headerList==null || headerList.size() < 1){
            log.error("获取token失败：{}-{}",responseEntity.getStatusCodeValue(),responseEntity.getBody());
            return null;
        }
        return headerList.get(headerList.size() - 1);
    }

    /**
     * 校验调用结果
     * */
    private String checkResponse(ResponseEntity<String> response) throws Exception{

        HttpStatus statusCode = response.getStatusCode();
        log.info("响应状态：" + statusCode.value());
        if(!statusCode.is2xxSuccessful()){
            throw new Exception("---------调用httpClient出错返回状态值" + statusCode.value());
        }
        return response.getBody();
    }

    private StringBuilder handleURL(String url, HttpMethod method){
        StringBuilder urlString = new StringBuilder();

        if("odata".equals(config.getType())) {
            //拼接url
            urlString.append(config.getOdataIp()).append(":").append(config.getOdataPort())
                    .append(odataString)
                    .append(url)
                    .append(odataClient)
                    .append(config.getOdataClient());

        }
        if("esb".equals(config.getType())) {
            //拼接url
            String esbString = "";
            if(HttpMethod.GET.equals(method)) {
                esbString = "/ESBWeb/servlets/15300.CM.OdataGet@1.0@zn.cm.xt?";
            }
            if(HttpMethod.POST.equals(method)) {
                esbString = "/ESBWeb/servlets/15301.CM.OdataPost@1.0@zn.cm.xt?";
            }
            if(HttpMethod.PUT.equals(method)) {
                esbString = "/ESBWeb/servlets/15303.CM.OdataPut@1.0@zn.cm.xt?";
            }
            if(HttpMethod.DELETE.equals(method)) {
                esbString = "/ESBWeb/servlets/15302.CM.OdataDelete@1.0@zn.cm.xt?";
            }
            urlString.append(config.getEsbIp())
                    .append(esbString)
                    .append(url)
                    .append(odataClient)
                    .append(config.getOdataClient());
        }
        //设置suffix
        String format = "$format=json";
        if(HttpMethod.GET.equals(method)) {
            if (urlString.indexOf("?") != -1) {
                if (urlString.indexOf(format) == -1) {
                    urlString.append("&").append(format);
                }
            } else {
                if (urlString.indexOf(format) == -1) {
                    urlString.append("?").append(format);
                }
            }
        }
        log.info("---" + method.name() + "  " + urlString.toString());
        return urlString;
    }

    private HttpHeaders handleHeader(){
        //设置http头
        HttpHeaders headers = new HttpHeaders();
        List<MediaType> mt = new ArrayList<>();
        mt.add(MediaType.APPLICATION_JSON);
        headers.setAccept(mt);
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        if("odata".equals(config.getType())) {
            //设置sap token，如果获取失败，则最多重试5次。
            String token = "";
            int i = 0;
            while (Strings.isNullOrEmpty(token)){
                token = this.getOdataToken();
                i++;
                if(i>5){
                    log.error("获取sap token失败！");
                    break;
                }
            }
            headers.add(odataTokenHeader, token);
        }
        return headers;
    }
}
