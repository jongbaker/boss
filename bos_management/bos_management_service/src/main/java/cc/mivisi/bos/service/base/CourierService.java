package cc.mivisi.bos.service.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import cc.mivisi.bos.domain.Courier;
import cc.mivisi.bos.domain.Standard;

/**  
 * ClassName:CourierService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月15日 下午8:31:09 <br/>       
 */
public interface CourierService {

	void save(Courier model);

	Page<Courier> findAll(Pageable pageable);

	void del(String ids);

	Page<Courier> findAll(Specification<Courier> specification, Pageable pageable);

}
  
