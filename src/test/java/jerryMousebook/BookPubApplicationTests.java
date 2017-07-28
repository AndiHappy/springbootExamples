package jerryMousebook;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.App;
import com.dao.UserDao;
import com.model.User;

/**
 * @author zhailz
 * 
 * @Date 2017年7月20日 - 下午4:59:56 - 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = App.class)
public class BookPubApplicationTests {

  @Resource
  private UserDao userDao;

  /* @Test
  public void getUser() {
    User value = userDao.getUser("a");
    System.out.println(value.getName());
  }*/

  @Test
  public void createUserInDifferentDataSource() {
    userDao.createInprimaryDataSource("primary", 100);
    userDao.createsecondaryDataSource("second", 200);
  }

  @Test
  public void getUserInDifferentDataSource() {
    User value = userDao.getUserFromFirstDataSource("primary");
    System.out.println(value.toString());
    value = userDao.getUserFromSecondDataSource("second");
    System.out.println(value.toString());
  }
}
