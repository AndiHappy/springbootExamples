package com.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:global.properties")
public class GlobalPropertiesValue {

    @Value("${thread-pool}")
    private int threadPool;

    @Value("${email}")
    private String email;

    /**
     * @return the threadPool
     */
    public int getThreadPool() {
      return threadPool;
    }

    /**
     * @param threadPool the threadPool to set
     */
    public void setThreadPool(int threadPool) {
      this.threadPool = threadPool;
    }

    /**
     * @return the email
     */
    public String getEmail() {
      return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
      this.email = email;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
      return "GlobalPropertiesValue [threadPool=" + threadPool + ", email=" + email + "]";
    }

   

}