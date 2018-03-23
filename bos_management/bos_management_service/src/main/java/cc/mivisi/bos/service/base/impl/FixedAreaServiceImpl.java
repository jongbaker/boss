package cc.mivisi.bos.service.base.impl;

import java.util.List;
import java.util.Set;

import org.bouncycastle.jce.provider.JDKDSASigner.noneDSA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cc.mivisi.bos.dao.base.CourierReposity;
import cc.mivisi.bos.dao.base.FixedJpaRepository;
import cc.mivisi.bos.dao.base.SubAreaJpaRepository;
import cc.mivisi.bos.dao.base.TakeTimeRepository;
import cc.mivisi.bos.domain.Courier;
import cc.mivisi.bos.domain.FixedArea;
import cc.mivisi.bos.domain.SubArea;
import cc.mivisi.bos.domain.TakeTime;
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
	@Autowired
	private CourierReposity courierReposity;
	@Autowired
	private TakeTimeRepository takeTimeRepository;
	@Autowired
	private SubAreaJpaRepository subAreaJpaRepository;
	
	@Override
	public Page<FixedArea> findAll(Pageable pageable) {
		  
		// TODO Auto-generated method stub  
		
		return fixedJpaRepository.findAll(pageable);
	}

	@Override
	public void associationCourierToFixedArea(Long id, Long courierId, Long takeTimeId) {
		  
		// 都是持久态
		
		FixedArea fixedArea = fixedJpaRepository.findOne(id);
		Courier courier = courierReposity.findOne(courierId);
		TakeTime takeTime = takeTimeRepository.findOne(takeTimeId);
		
		//让谁进行维护
		//快递员,让分区管理
		fixedArea.getCouriers().add(courier);
		
		//分时间,让多的一方管理
		courier.setTakeTime(takeTime);
	}

	@Override
	public void associationSubArea2FixedArea(Long id, Long[] customerIds) {
		//采用持久态的思想.  
		FixedArea fixedArea = fixedJpaRepository.findOne(id);
		Set<SubArea> subareas = fixedArea.getSubareas();
		for (SubArea subArea : subareas) {
			subArea.setArea(null);
		}
		
		for (Long subAreaId : customerIds) {
			SubArea subArea = subAreaJpaRepository.findOne(subAreaId);
			subArea.setFixedArea(fixedArea);
		}
		
		
	}
}
  
