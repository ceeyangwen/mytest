package cn.demo.jpa.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**  
 * @author GavinCee  
 * @date 2017年2月19日  
 *
 */
@ControllerAdvice
public class GlobalDefaultExceptionHandler {
	
	@ExceptionHandler(Exception.class)
	/**
	 * 如果返回的是View 方法的返回值是ModelAndView
	 * 如有返回的是String或者json，需要在方法上添加@@ResponseBody注解
	 * @return
	 */
	@ResponseBody
	public String doException(HttpServletRequest req, Exception e) {
		return "exception";
	}

}
