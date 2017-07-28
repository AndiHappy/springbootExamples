package com.controller;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhailz
 * @date 17/7/17 - 上午11:32.
 *
 *       RestController 和 Controller 的注解的时候，返回是String类型的时候，还是有点不同的情况：
 *       例如请求：" / " 和请求 "/simple/"
 */

@RestController
@RequestMapping("/simple")
public class SimpleRestController {

  @RequestMapping("/")
  @ResponseBody
  public String home(ModelMap map) {
    map.addAttribute("host", "@RestController");
    return "index";
  }
}
