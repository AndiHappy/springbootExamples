package com.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("app") // prefix app, find app.* values
public class AppProperties {

    private String error;
    private List<Menu> menus = new ArrayList<>();
    private Compiler compiler = new Compiler();

    public static class Menu {
        private String name;
        private String path;
        private String title;
        //getters and setters
        @Override
        public String toString() {
            return "Menu{" +
                    "name='" + name + '\'' +
                    ", path='" + path + '\'' +
                    ", title='" + title + '\'' +
                    '}';
        }
    }

    public static class Compiler {
        private String timeout;
        private String outputFolder;
        //getters and setters
        @Override
        public String toString() {
            return "Compiler{" +
                    "timeout='" + timeout + '\'' +
                    ", outputFolder='" + outputFolder + '\'' +
                    '}';
        }

    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
      return "AppProperties [error=" + error + ", menus=" + menus + ", compiler=" + compiler + "]";
    }

    
}