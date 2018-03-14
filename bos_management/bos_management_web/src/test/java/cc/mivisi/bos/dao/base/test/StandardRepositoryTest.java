package cc.mivisi.bos.dao.base.test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cc.mivisi.bos.dao.base.StandardRepository;
import cc.mivisi.bos.domain.Standard;

/**  
 * ClassName:StandardRepositoryTest <br/>  
 * Function:  <br/>  
 * Date:     2018年3月14日 下午8:13:12 <br/>       
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class StandardRepositoryTest {
	@Autowired
	private StandardRepository standardRepository;
	
	//save
	@Test
	public void save() {
		Standard standard= new Standard();
		standard.setName("大老张");
		standard.setMaxWeight(100);
		standardRepository.save(standard);
		System.out.println(standardRepository.getClass().getName());
	}
	
	//save兼具修改和保存的功能
	//修改的话传入ID
	@Test
	public void testFindByNameLike() {
		Standard standard=new Standard();
		standard.setId(1L);
		standard.setName("习大大");
		standard.setMaxWeight(800);
		standardRepository.save(standard);
	}
	//findAll
	@Test
	public void findAll() {
		List<Standard> list = standardRepository.findAll();
		for (Standard standard : list) {
			System.out.println(standard);
			
		}
	}
	
	//根据名字查找
	@Test
	public void findByNameLike() {
		List<Standard> list = standardRepository.findByNameLike("%张%");
		for (Standard standard : list) {
			System.out.println(standard);
			
		}
	}
	@Test
	public void findByNameAndMaxWeight() {
		List<Standard> list = standardRepository.findByNameAndMaxWeight("大老张", 100);
		for (Standard standard : list) {
			System.out.println(standard);
			
		}
	}
	
	//事务回滚了
	@Test
	public void test() {
		standardRepository.updateWeightByName(200, "大老张");
	
	}

	@Test
	public void testDeleteByBame() {
		standardRepository.deleteByBame("大老张");
	}

}
  
