package jerryMousebook;

import java.util.concurrent.Future;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.App;
import com.task.AsyncTaskService;
import com.task.ScheduledTasks;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = App.class)
public class TaskTests {

  @Autowired
  private AsyncTaskService task;

  @Autowired
  private ScheduledTasks tasks;

  @Test
  public void test() throws Exception {
    tasks.reportCurrentTime();

    long start = System.currentTimeMillis();
    Future<String> task1 = task.doTaskOne();
    Future<String> task2 = task.doTaskTwo();
    Future<String> task3 = task.doTaskThree();
    while (true) {
      /**
       * 任务如果没有完成，直接阻塞着
       * */
      if (task1.isDone() && task2.isDone() && task3.isDone()) {
        break;
      }
      Thread.sleep(1000);
    }

    long end = System.currentTimeMillis();
    System.out.println("任务全部完成，总耗时：" + (end - start) + "毫秒");

  }

}
