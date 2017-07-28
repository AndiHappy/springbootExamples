package com.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.exception.MyException;
import com.util.ConfigCenter;

@Controller
public class SampleController {

  @Resource
  private ConfigCenter configCenter;

  /**
   * @ResponseBody 这个标注影响着：
   *               URL handler的选择
   *               有ResponseBody的时候，处理的handler是：
   *               org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor
   *               没有的情况是：
   *               org.springframework.web.servlet.mvc.method.annotation.ViewNameMethodReturnValueHandler
   *               影响的方法名称是：
   *               org.springframework.web.method.support.HandlerMethodReturnValueHandlerComposite.selectHandler(Object, MethodParameter)
   */
  @RequestMapping("/")
  public String home(ModelMap map) {
    map.addAttribute("host", "http://github.com");
    return "index";
  }

  @RequestMapping("/hello")
  public String hello() {
    return "index";
  }
  
  @RequestMapping("/login")
  public String login() {
    return "login";
  }

  /**
   * 统一的错误处理机制
   * */
  @RequestMapping("/error1")
  public ModelAndView generalError() throws Exception {
    throw new Exception("发生错误");
  }

  /**
   * 个性化的错误处理
   * */
  @RequestMapping("/error2")
  public String specialError() throws MyException {
    throw new MyException("发生错误2");
  }

  @RequestMapping("/config")
  @ResponseBody
  public String config() {
    return configCenter.toString();
  }

}