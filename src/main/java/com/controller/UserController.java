package com.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.model.User;
import com.server.UserServer;

@RestController
@RequestMapping(value = "/users")
// 通过这里配置使下面的映射都在/users下，可去除
public class UserController {

  @Resource
  private UserServer userServer;

  @RequestMapping(value = "/num", method = RequestMethod.GET)
  public Integer getUserList() {
    return userServer.getAllUsers();
  }

  @RequestMapping(value = "/", method = RequestMethod.POST)
  public String postUser(@ModelAttribute User user) {
    // 处理"/users/"的POST请求，用来创建User
    // 除了@ModelAttribute绑定参数之外，还可以通过@RequestParam从页面中传递参数
    userServer.create(user.getName(), user.getAge());
    return "success";
  }

  @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
  public User getUser(@PathVariable String id) {
    return userServer.getUser(id);
  }
}
