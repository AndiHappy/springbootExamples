package com.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author zhailz
 * @date 17/7/13 - 上午10:13.
 *
 * 配置文件的配置，可以通过application.properties 文件设置
 */

@Component
public class ConfigCenter {

  @Value("${com.configCenter.name}")
  private String name;


  @Value("${com.configCenter.number}")
  private Integer number;


  @Value("${com.configCenter.bignumber}")
  private Long bignumber;


  @Value("${com.configCenter.test1}")
  private Integer test1;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public Integer getNumber() {
    return number;
  }

  public void setNumber(Integer number) {
    this.number = number;
  }

  public Long getBignumber() {
    return bignumber;
  }

  public void setBignumber(Long bignumber) {
    this.bignumber = bignumber;
  }

  public Integer getTest1() {
    return test1;
  }

  public void setTest1(Integer test1) {
    this.test1 = test1;
  }

  @Override public String toString() {
    return "ConfigCenter{" +
        "name='" + name + '\'' +
        ", number=" + number +
        ", bignumber=" + bignumber +
        ", test1=" + test1 +
        '}';
  }
}
