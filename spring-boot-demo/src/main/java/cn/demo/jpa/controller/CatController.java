package cn.demo.jpa.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.demo.jpa.bean.Cat;
import cn.demo.jpa.service.CatService;

/**  
 * @author GavinCee  
 * @date 2017年2月19日  
 *
 */
@RestController
@RequestMapping("/cat")
public class CatController {
	
	@Resource
	private CatService catServcice;

	@RequestMapping("/save")
	public String save() {
		Cat cat = new Cat();
		cat.setCatAge(1);
		cat.setCatName("jack");
		catServcice.save(cat);
		return "save ok";
	}
	
	@RequestMapping("/delete")
	public String delete() {
		catServcice.delete(1);
		return "delete ok";
	}
	
	@RequestMapping("/getAll")
	public Iterable<Cat> getAll() {
		return catServcice.getAll();
	}
	
	@RequestMapping("/findByCatName")
	public Cat findByCatName(String catName) {
		return catServcice.findByCatName(catName);
	}
}
