package jerryMousebook;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.App;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = App.class)
public class LogbackDemo {
  private static Logger log = LoggerFactory.getLogger(LogbackDemo.class);

  /**
   * 使用的log是logback，默认配置logback.xm文件即可
   * 在工程src目录下建立logback.xml
   * 注：
   * 1.logback首先会试着查找logback.groovy文件;
   * 2.当没有找到时，继续试着查找logback-test.xml文件;
   * 3.当没有找到时，继续试着查找logback.xml文件;
   * 4.如果仍然没有找到，则使用默认配置（打印到控制台）。
   * */
  
  @Test
  public void logTest() {
    log.trace("trace ====== >");
    log.debug("debug ====== >");
    log.info("info ====== >");
    log.warn("warn ====== >");
    log.error("error ====== >");

    String name = "Aub";
    String message = "3Q";
    String[] fruits = { "apple", "banana" };

    // logback提供的可以使用变量的打印方式，结果为"Hello,Aub!"
    log.info("Hello,{}!", name);

    // 可以有多个参数,结果为“Hello,Aub! 3Q!”
    log.info("Hello,{}!   {}!", name, message);

    // 可以传入一个数组，结果为"Fruit: apple,banana"
    log.info("Fruit:  {},{}", fruits);
  }
}
