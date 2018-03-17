package cc.mivisi.bos.service.base.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cc.mivisi.bos.dao.base.StandardRepository;
import cc.mivisi.bos.domain.Standard;
import cc.mivisi.bos.service.base.StandardService;

/**  
 * ClassName:StandardServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月14日 下午10:19:11 <br/>       
 */
@Service
@Transactional
public class StandardServiceImpl implements StandardService {
	@Autowired
	private StandardRepository standardRepository;

	@Override
	public void save(Standard model) {
		standardRepository.save(model);
	}

	//springData
	@Override
	public Page<Standard> findAll(Pageable pageable) {
		  
		Page<Standard> page = standardRepository.findAll(pageable);
		return page;
	}



}
  
