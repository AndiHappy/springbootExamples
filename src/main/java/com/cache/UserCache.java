package com.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import com.model.User;

/**
 * @author zhailz
 * 
 * @Date 2017年7月21日 - 下午2:04:38 - 
 */
@Component
public class UserCache {

  @Autowired
  private StringRedisTemplate stringRedisTemplate;

  @Autowired
  private RedisTemplate<String, User> redisTemplate;

  public boolean cacheUser(User user) {
    redisTemplate.opsForValue().set(user.getName(), user);
    return true;
  }

  public User getcacheUser(String name) {
    User user = redisTemplate.opsForValue().get(name);
    return user;
  }

  public boolean cacheStringValue(String key, String value) {
    stringRedisTemplate.opsForValue().set(key, value);
    return true;
  }

  public String getcacheStringValue(String key) {
    String value = stringRedisTemplate.opsForValue().get(key);
    return value;
  }

}
