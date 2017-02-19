package cn.other;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**  
 * 
 * springboot默认可以扫描到的是@SpringBootApplication所在类的同包或者子包下的类
 * @author GavinCee  
 * @date 2017年2月19日  
 *
 */
@RestController
public class HelloWorldController {
	
	@RequestMapping(value="/hello")
	public String helloWorld() {
		return "Hello World";
	}
	
}
