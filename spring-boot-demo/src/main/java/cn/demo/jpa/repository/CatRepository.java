package cn.demo.jpa.repository;

import org.springframework.data.repository.CrudRepository;

import cn.demo.jpa.bean.Cat;

/**  
 * @author GavinCee  
 * @date 2017年2月19日  
 *
 */
public interface CatRepository extends CrudRepository<Cat, Integer> {
	
}
