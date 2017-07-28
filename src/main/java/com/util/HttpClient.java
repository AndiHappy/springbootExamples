package com.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.CodingErrorAction;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.MessageConstraints;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class HttpClient {
  private final static Logger logger = LoggerFactory.getLogger(HttpClient.class);
  private static CloseableHttpClient closeablehttpclient = null;
  private static IdleConnectionMonitorThread scanThread = null;
  private final static int socketTimeout = 10000; // 响应超时时间
  private final static int connectTimeout = 2000; // 链接建立的超时时间
  private final static int connectionRequestTimeout = 5000;// 拿到一个连接的时间
  private final static int maxTotal = 800;
  private final static int maxPerRoute = 200;

  public HttpClient() {
    init();
  }

  @PostConstruct
  public static void init() {
    try {

      SSLContextBuilder builder = new SSLContextBuilder();
      builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
      SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(builder.build());
      // 配置同时支持 HTTP 和 HTPPS
      Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder
          .<ConnectionSocketFactory> create().register("http", PlainConnectionSocketFactory
              .getSocketFactory()).register("https", sslsf).build();

      PoolingHttpClientConnectionManager poolManager = new PoolingHttpClientConnectionManager(
          socketFactoryRegistry);
      closeablehttpclient = HttpClients.custom().setConnectionManager(poolManager).build();
      // Create socket configuration
      SocketConfig socketConfig = SocketConfig.custom().setTcpNoDelay(true).build();
      poolManager.setDefaultSocketConfig(socketConfig);
      // Create message constraints
      MessageConstraints messageConstraints = MessageConstraints.custom().setMaxHeaderCount(200)
          .setMaxLineLength(2000).build();
      // Create connection configuration
      ConnectionConfig connectionConfig = ConnectionConfig.custom().setMalformedInputAction(
          CodingErrorAction.IGNORE).setUnmappableInputAction(CodingErrorAction.IGNORE).setCharset(
              Consts.UTF_8).setMessageConstraints(messageConstraints).build();
      poolManager.setDefaultConnectionConfig(connectionConfig);
      poolManager.setMaxTotal(maxTotal);
      poolManager.setDefaultMaxPerRoute(maxPerRoute);
      scanThread = new IdleConnectionMonitorThread(poolManager);
      scanThread.start();
      logger.info("httpclint pool init ok---------");
    } catch (Exception e) {
      logger.error("closeablehttpclient init error:", e);
    }
  }

  /**
   * 关闭连接池.
   */
  @PreDestroy
  public static void close() {
    if (closeablehttpclient != null) {
      try {
        closeablehttpclient.close();
      } catch (IOException ignored) {
      }
    }
    if (scanThread != null) {
      scanThread.shutdown();
    }
    logger.info("httpclint pool close ok---------");
  }

  public static String httpPost(String url, int socketTimeout, int connectionRequestTimeout,
      List<NameValuePair> paramList, String encoding) throws Exception {
    HttpPost post = new HttpPost(url);
    CloseableHttpResponse response = null;
    HttpEntity entity = null;
    try {
      post.setHeader("Content-type", "application/json");
      RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout)
          .setConnectTimeout(connectTimeout).setConnectionRequestTimeout(connectionRequestTimeout)
          .setExpectContinueEnabled(false).build();
      post.setConfig(requestConfig);
      post.setEntity(new UrlEncodedFormEntity(paramList, Consts.UTF_8));
      logger.info("httpPost invoke url:" + url + " , params:" + paramList);
      response = closeablehttpclient.execute(post);
      entity = response.getEntity();
      if (entity != null) {
        String str = EntityUtils.toString(entity, encoding);
        logger.info("httpPost url :" + url + " , response string :" + str);
        return str;
      }
    } finally {
      if (entity != null) {
        entity.getContent().close();
      }
      if (response != null) {
        response.close();
      }
      post.releaseConnection();
    }
    return "";

  }

  public static String httpPost(String url, int socketTimeout, int connectionRequestTimeout,
      List<NameValuePair> paramList, Header header, String encoding) throws Exception {
    HttpPost post = new HttpPost(url);
    CloseableHttpResponse response = null;
    HttpEntity entity = null;
    try {
      post.setHeader(header);
      RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout)
          .setConnectTimeout(connectTimeout).setConnectionRequestTimeout(connectionRequestTimeout)
          .setExpectContinueEnabled(false).build();
      post.setConfig(requestConfig);

      post.setEntity(new UrlEncodedFormEntity(paramList, Consts.UTF_8));
      logger.info("httpPost invoke url:" + url + " , params:" + paramList);
      response = closeablehttpclient.execute(post);
      entity = response.getEntity();
      if (entity != null) {
        String str = EntityUtils.toString(entity, encoding);
        logger.info("httpPost url :" + url + " , response string :" + str);
        return str;
      }
    } finally {
      if (entity != null) {
        entity.getContent().close();
      }
      if (response != null) {
        response.close();
      }
      post.releaseConnection();
    }
    return "";

  }

  public static String httpPost(String url, int socketTimeout, int connectionRequestTimeout,
      List<NameValuePair> paramList, Map<String, String> headerMap, String encoding)
      throws Exception {
    HttpPost post = new HttpPost(url);
    CloseableHttpResponse response = null;
    HttpEntity entity = null;
    try {
      post.setHeader("Content-type", "application/json");
      for (Map.Entry<String, String> entry : headerMap.entrySet()) {
        post.setHeader(entry.getKey(), entry.getValue());
      }

      RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout)
          .setConnectTimeout(connectTimeout).setConnectionRequestTimeout(connectionRequestTimeout)
          .setExpectContinueEnabled(false).build();
      post.setConfig(requestConfig);
      post.setEntity(new UrlEncodedFormEntity(paramList, Consts.UTF_8));
      logger.info("httpPost invoke url:" + url + " , params:" + paramList);
      response = closeablehttpclient.execute(post);
      entity = response.getEntity();
      if (entity != null) {
        String str = EntityUtils.toString(entity, encoding);
        logger.info("httpPost url :" + url + " , response string :" + str);
        return str;
      }
    } finally {
      if (entity != null) {
        entity.getContent().close();
      }
      if (response != null) {
        response.close();
      }
      post.releaseConnection();
    }
    return "";

  }

  public static String httpPost(String url, List<NameValuePair> paramList, String encoding)
      throws Exception {
    return httpPost(url, socketTimeout, connectionRequestTimeout, paramList, encoding);
  }

  public static String httpPost(String url, List<NameValuePair> paramList) throws Exception {
    return httpPost(url, socketTimeout, connectionRequestTimeout, paramList, Consts.UTF_8.name());
  }

  public static String httpPost(String url, List<NameValuePair> paramList, Header header)
      throws Exception {
    return httpPost(url, socketTimeout, connectionRequestTimeout, paramList, header, Consts.UTF_8
        .name());
  }

  public static String httpPost(String url, List<NameValuePair> paramList,
      Map<String, String> headerMap) throws Exception {
    return httpPost(url, socketTimeout, connectionRequestTimeout, paramList, headerMap, Consts.UTF_8
        .name());
  }

  public static String httpGet(String url, List<NameValuePair> paramList, int socketTimeout,
      int connectionRequestTimeout, String encode) throws Exception {
    String responseString = null;
    CloseableHttpResponse response = null;
    HttpEntity entity = null;
    RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout)
        .setConnectTimeout(connectTimeout).setConnectionRequestTimeout(connectionRequestTimeout)
        .build();
    StringBuilder sb = new StringBuilder();
    sb.append(url);
    int i = 0;
    if (paramList != null && !paramList.isEmpty()) {
      for (NameValuePair nameValuePair : paramList) {
        if (i == 0 && !url.contains("?")) {
          sb.append("?");
        } else {
          sb.append("&");
        }
        sb.append(nameValuePair.getName());
        sb.append("=");
        String value = nameValuePair.getValue();
        try {
          sb.append(URLEncoder.encode(value, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
          logger.warn("encode http get params error, value is " + value, e);
          sb.append(URLEncoder.encode(value));
        }
        i++;
      }
    }
    logger.info("invokeGet begin invoke:" + sb.toString());
    HttpGet get = new HttpGet(sb.toString());
    get.setConfig(requestConfig);
    try {
      response = closeablehttpclient.execute(get);
      entity = response.getEntity();
      if (entity != null) {
        responseString = EntityUtils.toString(entity, encode);
        return responseString;
      }
    } finally {
      if (entity != null) {
        entity.getContent().close();
      }
      if (response != null) {
        response.close();
      }
      get.releaseConnection();
    }
    return responseString;
  }

  public static String httpGet(String url, List<NameValuePair> paramList) throws Exception {
    return httpGet(url, paramList, socketTimeout, connectionRequestTimeout, Consts.UTF_8
        .toString());
  }

  public static String httpGet(String url, List<NameValuePair> paramList, int socketTimeout,
      int connectionRequestTimeout) throws Exception {
    return httpGet(url, paramList, socketTimeout, connectionRequestTimeout, Consts.UTF_8
        .toString());
  }

  public static String connectPostHttps(String reqURL, Map<String, String> params) {
    String responseContent = null;
    HttpPost httpPost = new HttpPost(reqURL);
    try {
      RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(connectTimeout)
          .setConnectTimeout(connectTimeout).setConnectionRequestTimeout(connectTimeout).build();

      List<NameValuePair> formParams = new ArrayList<>();
      httpPost.setEntity(new UrlEncodedFormEntity(formParams, Consts.UTF_8));
      httpPost.setConfig(requestConfig);
      // 绑定到请求 Entry
      for (Map.Entry<String, String> entry : params.entrySet()) {

        formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
      }

      CloseableHttpResponse response = closeablehttpclient.execute(httpPost);
      try {
        // 执行POST请求
        HttpEntity entity = response.getEntity(); // 获取响应实体
        try {
          if (null != entity) {
            responseContent = EntityUtils.toString(entity, Consts.UTF_8);
          }
        } finally {
          if (entity != null) {
            entity.getContent().close();
          }
        }
      } finally {
        if (response != null) {
          response.close();
        }
      }
      logger.info("requestURI : " + httpPost.getURI() + ", responseContent: " + responseContent);
    } catch (ClientProtocolException e) {
      logger.error("ClientProtocolException", e);
    } catch (IOException e) {
      logger.error("IOException", e);
    } finally {
      httpPost.releaseConnection();
    }
    return responseContent;

  }

  static class IdleConnectionMonitorThread extends Thread {
    private final HttpClientConnectionManager connMgr;
    private volatile boolean shutdown;

    public IdleConnectionMonitorThread(HttpClientConnectionManager connMgr) {
      super();
      this.connMgr = connMgr;
    }

    @Override
    public void run() {
      while (!shutdown) {
        synchronized (scanThread) {
          try {
            // 关闭无效连接
            connMgr.closeExpiredConnections();
            // 可选, 关闭空闲超过30秒的
            connMgr.closeIdleConnections(30, TimeUnit.SECONDS);
            scanThread.wait(5000);
          } catch (Exception e) {
            logger.error("IdleConnectionMonitorThread", e);
          }
        }
      }
    }

    public void shutdown() {
      synchronized (scanThread) {
        shutdown = true;
        scanThread.notifyAll();
      }
    }
  }

}
