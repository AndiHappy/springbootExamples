package com.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:global.properties")
@ConfigurationProperties
public class GlobalProperties {

    private int threadPool;
    private String email;
    
    public int getThreadPool() {
      return threadPool;
    }
    public void setThreadPool(int threadPool) {
      this.threadPool = threadPool;
    }
    public String getEmail() {
      return email;
    }
    public void setEmail(String email) {
      this.email = email;
    }
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
      return "GlobalProperties [threadPool=" + threadPool + ", email=" + email + "]";
    }

    
}