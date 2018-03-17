package cc.mivisi.bos.service.base.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cc.mivisi.bos.dao.base.AreaReposity;
import cc.mivisi.bos.domain.Area;
import cc.mivisi.bos.domain.Courier;
import cc.mivisi.bos.service.base.AreaService;
import cc.mivisi.bos.service.base.CourierService;

/**  
 * ClassName:AreaServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月16日 下午9:13:16 <br/>       
 */
@Service
@Transactional
public class AreaServiceImpl implements AreaService {
	@Autowired
	private AreaReposity areaReposity;

	
		
	

	@Override
	public void save(ArrayList<Area> list) {
		  
		areaReposity.save(list);
		
	}





	@Override
	public Page<Area> findAll(Pageable pageable) {
		  
		
		return areaReposity.findAll(pageable);
	}





	@Override
	public List<Area> findByQ(String q) {
		  
		// TODO Auto-generated method stub  
		
		q="%"+q.toUpperCase()+"%";
		return areaReposity.findByQ(q);
	}

	

}
  
