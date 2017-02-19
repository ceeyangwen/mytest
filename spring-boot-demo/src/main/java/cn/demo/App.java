package cn.demo;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

/**  
 * @author GavinCee  
 * @date 2017年2月19日  
 * 默认端口是8080，修改端口
 * 1、如何修改端口
 * 只需要在application.properties文件中添加server.port=8081
 * 2、如何配置上下文（项目的根路径，context-path）
 * 只需要在application.properties文件中添加server.context-path=/test
 */
@SpringBootApplication
public class App extends WebMvcConfigurerAdapter {
	
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		super.configureMessageConverters(converters);
		//先定义convert，转换消息的对象
		FastJsonHttpMessageConverter fastConvert = new FastJsonHttpMessageConverter();
		FastJsonConfig config = new FastJsonConfig();
		config.setSerializerFeatures(SerializerFeature.PrettyFormat);
		fastConvert.setFastJsonConfig(config);
		converters.add(fastConvert);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

}
