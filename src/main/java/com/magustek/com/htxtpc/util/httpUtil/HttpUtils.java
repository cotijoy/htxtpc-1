package com.magustek.com.htxtpc.util.httpUtil;

import org.springframework.http.HttpMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface HttpUtils {
    Map<String, Object> getMapByUrl(String url, Object params, HttpMethod method) throws Exception;
    List<Map<String,Object>> getListByUrl(String url, Object params, HttpMethod method) throws Exception;

    String getResultByUrl(String url, Object params, HttpMethod method) throws Exception;
    void downloadFile(HttpServletRequest request, String url, HttpServletResponse response) throws Exception;
    String uploadFile(String url, MultipartFile[] file);
}
