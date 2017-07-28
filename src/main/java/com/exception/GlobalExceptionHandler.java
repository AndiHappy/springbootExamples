package com.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @ControllerAdvice注解内部使用@ExceptionHandler、@InitBinder、@ModelAttribute注解的方法应用到所有的 @RequestMapping注解的方法。
 * 非常简单，不过只有当使用@ExceptionHandler最有用，另外两个用处不大。
 * */
@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(value = Exception.class)
  public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
    e.printStackTrace();
    ModelAndView mav = new ModelAndView();
    mav.addObject("exception", e);
    mav.addObject("url", req.getRequestURL());
    mav.setViewName("error");
    return mav;
  }

  @ExceptionHandler(value = MyException.class)
  @ResponseBody
  public MouseError<String> jsonErrorHandler(HttpServletRequest req, MyException e)
      throws Exception {
    MouseError<String> r = new MouseError<String>();
    r.setMessage(e.getMessage());
    r.setCode(MouseError.ERROR);
    r.setData("Some Data");
    r.setUrl(req.getRequestURL().toString());
    return r;
  }

}