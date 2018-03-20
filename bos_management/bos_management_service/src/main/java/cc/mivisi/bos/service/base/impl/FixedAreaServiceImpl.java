package cc.mivisi.bos.service.base.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cc.mivisi.bos.dao.base.FixedJpaRepository;
import cc.mivisi.bos.domain.FixedArea;
import cc.mivisi.bos.service.base.FixedAreaService;

/**  
 * ClassName:FixedAreaServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月18日 下午8:31:19 <br/>       
 */
@Transactional
@Service
public class FixedAreaServiceImpl implements FixedAreaService {
	@Autowired
	private FixedJpaRepository fixedJpaRepository;

	@Override
	public Page<FixedArea> findAll(Pageable pageable) {
		  
		// TODO Auto-generated method stub  
		
		return fixedJpaRepository.findAll(pageable);
	}
}
  
