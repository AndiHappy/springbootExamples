## spring的简单的应用   

### 1.一个简单的spring的应用，具体见: App.java 和 SimpleRestController

 @Controller so Spring will consider it when handling incoming web requests.     
 @RequestMapping annotation provides “routing” information. It is telling Spring that any HTTP request with the path “/” should be mapped to the home method.       
 @RestController annotation tells Spring to render the resulting string directly back to the caller.      
@EnableAutoConfiguration. This annotation tells Spring Boot to “guess” how you will want to configure Spring, based on the jar dependencies that you have added. Since spring-boot-starter-web added Tomcat and Spring MVC, the auto-configuration will assume that you are developing a web application and setup Spring accordingly.

所以 @EnableAutoConfiguration 一般的标注是工程文件的最外层。   

our application is the main method. This is just a standard method that follows the Java convention for an application entry point. Our main method delegates to Spring Boot’s SpringApplication class by calling run. SpringApplication will bootstrap our application, starting Spring which will in turn start the auto-configured Tomcat web server.

java启动类里面的main方法，可以用来启动整个工程。

### 2.打包

具体的maven的指令是：mvn package
具体的说明可以参见：
http://docs.spring.io/spring-boot/docs/2.0.0.BUILD-SNAPSHOT/maven-plugin//usage.html
如果有多个的启动的类，可以指定启动类，版本号之类的变量


### 3.依赖的管理    
我们选择的是maven依赖，   
Sensible resource filtering for application.properties and application.yml including profile-specific files (e.g. application-foo.properties and application-foo.yml)     配置文件的名称已经确定了。

spring-boot 提供了书写简单的依赖方案，例如如果想依赖redis，直接依赖：spring-boot-starter-redis

### 4.推荐工程层次和包的结构    

~~~
com
 +- example
     +- myproject
         +- Application.java
         |
         +- domain
         |   +- Customer.java
         |   +- CustomerRepository.java
         |
         +- service
         |   +- CustomerService.java
         |
         +- web
             +- CustomerController.java
~~~

可以作为参考





