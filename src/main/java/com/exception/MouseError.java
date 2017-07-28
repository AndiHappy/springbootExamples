package com.exception;

/**
 * @author zhailz
 * @param <T>
 * 
 * @Date 2017年7月20日 - 上午11:41:08 - 
 */
public class MouseError<T> {

  public static final Integer OK = 0;
  public static final Integer ERROR = 100;

  private Integer code;
  private String message;
  private String url;
  private T data;

  public Integer getCode() {
    return code;
  }

  public void setCode(Integer code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }

}
