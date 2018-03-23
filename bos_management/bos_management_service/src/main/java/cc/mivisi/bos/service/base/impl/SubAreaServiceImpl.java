package cc.mivisi.bos.service.base.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cc.mivisi.bos.dao.base.SubAreaJpaRepository;
import cc.mivisi.bos.domain.FixedArea;
import cc.mivisi.bos.domain.SubArea;
import cc.mivisi.bos.service.base.SubAreaService;

/**  
 * ClassName:SubAreaServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月17日 下午5:10:31 <br/>       
 */
@Service
@Transactional
public class SubAreaServiceImpl implements SubAreaService {
	@Autowired
	private SubAreaJpaRepository subAreaJpaRepository;
	
	@Override
	public void save(SubArea model) {
			
		subAreaJpaRepository.save(model);
		
		
	}

	@Override
	public Page<SubArea> findAll(Pageable pageable) {
		  
		return (Page<SubArea>) subAreaJpaRepository.findAll(pageable);
	}

	@Override
	public List<SubArea> findUnAssociation() {
		  
		
		return subAreaJpaRepository.findByFixedAreaIsNull();
	}

	@Override
	public List<SubArea> findAssociation(Long id) {
		  System.out.println(id);
		//用面向对象的思维,全都是对象
		FixedArea fixedArea=new FixedArea();
		fixedArea.setId(id);
		
		return subAreaJpaRepository.findByFixedArea(fixedArea);
	}

	@Override
	public void associationSubArea2FixedArea(Long id, Long[] customerIds) {
		
		
		subAreaJpaRepository.setSubArea2FixedAreaNull(id); 
		
		for (Long SubareaId : customerIds) {
			subAreaJpaRepository.setSubArea2FixedAreaNull(id,SubareaId);
		}
		
	}
	
	
	
	
	

}
  
