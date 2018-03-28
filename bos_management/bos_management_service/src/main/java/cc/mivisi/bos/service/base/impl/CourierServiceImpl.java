package cc.mivisi.bos.service.base.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import cc.mivisi.bos.dao.base.CourierReposity;
import cc.mivisi.bos.domain.Courier;
import cc.mivisi.bos.domain.Standard;
import cc.mivisi.bos.service.base.CourierService;

/**  
 * ClassName:CourierServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月15日 下午8:32:09 <br/>       
 */
@Service
@Transactional
public class CourierServiceImpl implements CourierService {
	
	@Autowired
	private CourierReposity courierReposity;

	@Override
	public void save(Courier model) {
		  
		// TODO Auto-generated method stub  
		courierReposity.save(model);
	}

	@Override
	public Page<Courier> findAll(Pageable pageable) {
		  
		
		return courierReposity.findAll(pageable);
	}


	
	
	
	//授权删除--需要进行开启cglib代理
	@RequiresPermissions("courier_delete")
	@Override
	public void del(String ids) {
		  
		//实际开发中只有逻辑删除
		//判断诗句是否为空..虽然在前端页面控制了,但是,如果知道地址栏的人,会进行手动输入.所以需要判断
		if(StringUtils.isNotEmpty(ids)){
			//切割数据
			String[] split = ids.split(",");
			for (String id : split) {
				courierReposity.updateDelTagById(Long.parseLong(id));
			}
		}
		
	}

	//带有条件的findAll
	@Override
	public Page<Courier> findAll(Specification<Courier> specification, Pageable pageable) {
	
		
		return courierReposity.findAll(specification, pageable);
	}

	@Override
	public List<Courier> findByDeltagIsNotNull() {
		  
		return	courierReposity.findByDeltagIsNotNull();
	}

}
  
