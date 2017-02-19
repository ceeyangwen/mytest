package cn.demo;

import java.util.Date;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.demo.model.DemoModel;

/**  
 * @author GavinCee  
 * @date 2017年2月19日  
 *
 */
@RestController
public class HelloWorldController {
	
	@RequestMapping(value="/")
	public String helloWorld() {
		return "Hello World";
	}
	
	/**
	 * Springboot 默认使用的json解析框架式jackson
	 * @return
	 */
	@RequestMapping(value="/getDemo")
	public DemoModel getDemo() {
		DemoModel demo = new DemoModel();
		demo.setId(2);
		demo.setName("张三");
		demo.setCreateDate(new Date());
		demo.setMark("这是备注");
		return demo;
	}
	
}
