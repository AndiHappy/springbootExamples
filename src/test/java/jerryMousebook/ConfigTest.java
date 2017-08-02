package jerryMousebook;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.App;
import com.config.AppProperties;
import com.config.GlobalProperties;
import com.config.GlobalPropertiesValue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = App.class)
public class ConfigTest {
  private static Logger log = LoggerFactory.getLogger(ConfigTest.class);

  @Autowired
  private AppProperties appProperties;

  @Autowired
  private GlobalProperties globalProperties;
  
  @Autowired
  private GlobalPropertiesValue globalPropertiesValue;

  @Test
  public void testProperties() throws Exception {
    log.info(appProperties.toString());
    log.info(globalProperties.toString());
    log.info(globalPropertiesValue.toString());
  }

}
