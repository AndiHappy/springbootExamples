package com.task;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @EnableScheduling
 * 这个注解也可以放在 App 启动类的上面，统一的配置比较的常见
 * */
@Component
@EnableScheduling
public class ScheduledTasks {

  private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
  private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

  /**
   * 只有@Scheduled注解是无法工作的，还需要一个 @EnableScheduling 注解
   * initialDelay=1000, fixedRate=5000 第一次延迟1秒后执行，之后按fixedRate的规则每5秒执行一次
   * cron= "0 * * * * MON-FRI" means once per minute on weekdays (
   *  at the top of the minute - the 0th second).
   * */
  @Scheduled(fixedRate = 50000000)
  public void reportCurrentTime() {
    log.info("当前时间：" + dateFormat.format(new Date()));
  }

  /**
   * cron 的说明：
   * 
   * @reboot         Run once, at startup.
           @yearly         Run once a year, "0 0 1 1 *".
           @annually       (same as @yearly)
           @monthly        Run once a month, "0 0 1 * *".
           @weekly         Run once a week, "0 0 * * 0".
           @daily          Run once a day, "0 0 * * *".
           @midnight       (same as @daily)
           @hourly         Run once an hour, "0 * * * *".
     # run five minutes after midnight, every day
     5 0 * * *       $HOME/bin/daily.job >> $HOME/tmp/out 2>&1
     # run at 2:15pm on the first of every month -- output mailed to paul
     15 14 1 * *     $HOME/bin/monthly
     # run at 10 pm on weekdays, annoy Joe
     0 22 * * 1-5    mail -s "It's 10pm" joe%Joe,%%Where are your kids?%
     23 0-23/2 * * * echo "run 23 minutes after midn, 2am, 4am ..., everyday"
     5 4 * * sun     echo "run at 5 after 4 every sunday"
  
   * */

}