package com.dao;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.model.User;

/**
 * @author zhailz
 * 
 * @Date 2017年7月20日 - 下午3:11:02 - 
 */

@Component
public class UserDao {

  // @Autowired
  // private JdbcTemplate jdbcTemplate;

  @Autowired
  private DataSourceConfig dataSourceCongfig;

  public void createInprimaryDataSource(String name, Integer age) {
    getJdbcTemplate1().update("insert into USER(NAME, AGE) values(?, ?)", name, age);
  }

  public void createsecondaryDataSource(String name, Integer age) {
    getJdbcTemplate2().update("insert into USER(NAME, AGE) values(?, ?)", name, age);
  }

  public User getUserFromFirstDataSource(String name) {
    Map<String, Object> map = getJdbcTemplate1().queryForMap("select * from user where name = '"
        + name + "'");
    return convert(map);
  }

  public User getUserFromSecondDataSource(String name) {
    Map<String, Object> map = getJdbcTemplate2().queryForMap("select * from user where name = '"
        + name + "'");
    return convert(map);
  }

  /* public void create(String name, Integer age) {
   * jdbcTemplate.update("insert into USER(NAME, AGE) values(?, ?)", name, age);
   * }
   * 
   * public void deleteByName(String name) {
   * jdbcTemplate.update("delete from USER where NAME = ?", name);
   * }
   * 
   * public Integer getAllUsers() {
   * return jdbcTemplate.queryForObject("select count(1) from USER",
   * Integer.class);
   * }
   * 
   * public void deleteAllUsers() {
   * jdbcTemplate.update("delete from USER");
   * }
   * 
   * public User getUser(String name) {
   * Map<String, Object> map =
   * jdbcTemplate.queryForMap("select * from user where name = '" + name
   * + "'");
   * return convert(map);
   * } */

  private User convert(Map<String, Object> map) {
    if (map != null) {
      User user = new User();
      user.setAge((Integer) map.get("age"));
      user.setName((String) map.get("name"));
      return user;
    }
    return null;
  }

  public JdbcTemplate getJdbcTemplate1() {
    return new JdbcTemplate(dataSourceCongfig.primaryDataSource());
  }

  public JdbcTemplate getJdbcTemplate2() {
    return new JdbcTemplate(dataSourceCongfig.secondaryDataSource());
  }

}
