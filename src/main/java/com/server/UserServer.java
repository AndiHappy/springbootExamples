package com.server;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cache.UserCache;
import com.dao.UserDao;
import com.model.User;

/**
 * @author zhailz
 * 
 * @Date 2017年7月20日 - 下午3:10:05 -
 * 
 *  服务
 */
@Service
public class UserServer {

  @Resource
  private UserDao dao;

  @Resource
  private UserCache userCache;

  public void create(String name, Integer age) {
    dao.createInprimaryDataSource(name, age);
  }

  public void deleteByName(String name) {
    // dao.deleteByName(name);
  }

  public Integer getAllUsers() {
    // return dao.getAllUsers();
    return 0;
  }

  public void deleteAllUsers() {
    // dao.deleteAllUsers();
  }

  public User getUser(String name) {
    return dao.getUserFromFirstDataSource(name);
  }

}
