package com.util;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author zhailz
 * 
 * @Date 2017年7月25日 - 上午9:15:22 - 
 */
public class InfoUtil {

  public static JSONObject getRequestInfo(HttpServletRequest request, ProceedingJoinPoint joinPoint)
      throws JSONException {
    // 基本信息
    JSONObject r = new JSONObject();
    r.put("requestURL", request.getRequestURL().toString());
    r.put("requestURI", request.getRequestURI());
    r.put("queryString", request.getQueryString());
    r.put("remoteAddr", request.getRemoteAddr());
    r.put("remoteHost", request.getRemoteHost());
    r.put("remotePort", request.getRemotePort());
    r.put("localAddr", request.getLocalAddr());
    r.put("localName", request.getLocalName());
    r.put("method", request.getMethod());
    r.put("headers", getHeadersInfo(request));
    r.put("parameters", request.getParameterMap());
    r.put("classMethod", joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint
        .getSignature().getName());
    r.put("args", Arrays.toString(joinPoint.getArgs()));
    return r;
  }

  protected static Map<String, String> getHeadersInfo(HttpServletRequest request) {
    Map<String, String> map = new HashMap<>();
    Enumeration<?> headerNames = request.getHeaderNames();
    while (headerNames.hasMoreElements()) {
      String key = (String) headerNames.nextElement();
      String value = request.getHeader(key);
      map.put(key, value);
    }
    return map;
  }
}
