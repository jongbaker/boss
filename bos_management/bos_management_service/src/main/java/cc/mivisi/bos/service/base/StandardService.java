package cc.mivisi.bos.service.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cc.mivisi.bos.domain.Standard;

/**  
 * ClassName:StandardService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月14日 下午10:12:49 <br/>       
 */
public interface StandardService {

	void save(Standard model);

	Page<Standard> findAll(Pageable pageable);

}
  
