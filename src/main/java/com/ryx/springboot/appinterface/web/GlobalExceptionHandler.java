package com.ryx.springboot.appinterface.web;

import com.ryx.springboot.appinterface.domain.ErrorInfo;
import com.ryx.springboot.appinterface.others.MyException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final String DEFAULT_ERROR_VIEW = "error";

    /***
     * 创建异常处理返回指定页面
     * @param request
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest request, Exception e) throws Exception{
        ModelAndView mav=new ModelAndView();
        mav.setViewName(DEFAULT_ERROR_VIEW);
        mav.addObject("exception",e);
        mav.addObject("url",request.getRequestURL());
        return mav;
    }

    /**
     * 为MyException 创建对应的异常处理，返回json
     * @param request
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = MyException.class)
    @ResponseBody
    public ErrorInfo<String> jsonErrorHandler(HttpServletRequest request, MyException e) throws  Exception {
        ErrorInfo<String> err=new ErrorInfo<>();
        err.setMessage(e.getMessage());
        err.setCode(ErrorInfo.ERROR);
        err.setData("error data");
        err.setUrl(request.getRequestURL().toString());
        return err;
    }
}
