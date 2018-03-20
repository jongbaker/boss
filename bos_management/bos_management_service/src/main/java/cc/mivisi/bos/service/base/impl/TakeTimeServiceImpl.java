package cc.mivisi.bos.service.base.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cc.mivisi.bos.dao.base.TakeTimeRepository;
import cc.mivisi.bos.domain.TakeTime;
import cc.mivisi.bos.service.base.TakeTimeService;

/**  
 * ClassName:TakeTimeServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月20日 下午12:56:36 <br/>       
 */
@Transactional
@Service
public class TakeTimeServiceImpl implements TakeTimeService {
	@Autowired
	private TakeTimeRepository takeTimeRepository;

	@Override
	public List<TakeTime> findAll() {
		  
		// TODO Auto-generated method stub  
		takeTimeRepository.findAll();
		return takeTimeRepository.findAll();
	}

}
  
