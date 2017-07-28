package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zhailz
 *
 * @date 17/7/12 - 下午1:15.
 *
 */

/**
 * @SpringBootApplication 配置的启示
 * */
@SpringBootApplication
public class App {

  public static void main(String[] args) throws Exception {
    /**
     * @NOTE： 起始类的加载比较的重要，如果确定扫描的范围
     * */
    SpringApplication.run(App.class, args);
  }
}
