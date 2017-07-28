package com.aop;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.util.InfoUtil;

/**
 * 切面，切点，对应的函数
 * */
@Aspect
@Order(5)
@Component
public class WebLogAspect {

  private Logger logger = LoggerFactory.getLogger(getClass());

  ThreadLocal<Long> startTime = new ThreadLocal<>();

  /***
   * 类似一个flag的作用
   * */
  @Pointcut("execution(public * com.controller..*.*(..))")
  public void webLog() {}

  /* @Before("webLog()")
  public void doBefore(JoinPoint joinPoint) throws Throwable {
    startTime.set(System.currentTimeMillis());
    // 接收到请求，记录请求内容
    ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
        .getRequestAttributes();
    HttpServletRequest request = attributes.getRequest();
    // 记录下请求内容
    logger.info("URL:{},METHOD:{},IP:{}", request.getRequestURL().toString(), request.getMethod(),
        request.getRemoteAddr());
    logger.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "."
        + joinPoint.getSignature().getName());
    if (joinPoint.getArgs() != null && joinPoint.getArgs().length > 0) {
      logger.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));
    }
  }
  
  @AfterReturning(returning = "ret", pointcut = "webLog()")
  public void doAfterReturning(Object ret) throws Throwable {
    // 处理完请求，返回内容
    logger.info("RESPONSE : " + ret);
    logger.info("SPEND TIME : " + (System.currentTimeMillis() - startTime.get()));
  } */

  @Around(value = "webLog()")
  public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("执行之前》》》》》》》》》》》》》》》》》》》》》》》》》");

    // 获取HttpServletRequest
    ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
        .getRequestAttributes();
    HttpServletRequest request = attributes.getRequest();
    // 获取要记录的日志内容
    JSONObject info = InfoUtil.getRequestInfo(request, joinPoint);
    logger.info(info.toString());
    Object object = joinPoint.proceed();
    logger.info("执行的结果：{}", object.toString());
    logger.info("执行结束《《《《《《《《《《《《《《《《《《《《《《《《《《");
    return object;
  }

}
