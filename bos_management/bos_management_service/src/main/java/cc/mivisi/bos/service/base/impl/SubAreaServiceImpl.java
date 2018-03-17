package cc.mivisi.bos.service.base.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cc.mivisi.bos.dao.base.SubAreaJpaRepository;
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

}
  
