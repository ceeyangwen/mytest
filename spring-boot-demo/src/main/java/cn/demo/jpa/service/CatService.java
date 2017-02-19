package cn.demo.jpa.service;

import java.util.Iterator;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import cn.demo.jpa.bean.Cat;
import cn.demo.jpa.dao.CatDao;
import cn.demo.jpa.repository.Cat2Repository;
import cn.demo.jpa.repository.CatRepository;

/**  
 * @author GavinCee  
 * @date 2017年2月19日  
 *
 */
@Service
public class CatService {

	@Resource
	private CatRepository catRepository;
	
	@Resource
	private Cat2Repository cat2Repository;
	
	@Resource
	private CatDao catDao;
	
	@Transactional
	public void save(Cat cat) {
		catRepository.save(cat);
	}
	
	@Transactional
	public void update() {
		
	}
	@Transactional
	public void delete(int id) {
		catRepository.delete(id);
	}
	
	public Iterable<Cat> getAll() {
		return catRepository.findAll();
	}
	
	public Cat findByCatName(String catName) {
		//return cat2Repository.findByCatName(catName);
		//return cat2Repository.findMyCatName(catName);
		return catDao.selectByCatName(catName);
	}
}
