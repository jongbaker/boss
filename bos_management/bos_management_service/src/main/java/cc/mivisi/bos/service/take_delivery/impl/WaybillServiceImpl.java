package cc.mivisi.bos.service.take_delivery.impl;

import org.springframework.beans.factory.annotation.Autowired;

import cc.mivisi.bos.dao.base.take_delivery.WaybillJpaRepository;
import cc.mivisi.bos.domain.take_delivery.WayBill;
import cc.mivisi.bos.service.take_delivery.WaybillService;

/**  
 * ClassName:WaybillServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月25日 下午11:37:09 <br/>       
 */
public class WaybillServiceImpl implements WaybillService {
    @Autowired
    private WaybillJpaRepository waybillJpaRepository;

    @Override
    public void save(WayBill model) {
          
      waybillJpaRepository.save(model);
        
    }

}
  
