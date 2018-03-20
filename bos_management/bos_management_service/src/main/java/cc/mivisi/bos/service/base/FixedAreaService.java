package cc.mivisi.bos.service.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cc.mivisi.bos.domain.FixedArea;

/**  
 * ClassName:FixedAreaService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月18日 下午8:30:32 <br/>       
 */
public interface FixedAreaService {

	Page<FixedArea> findAll(Pageable pageable);

	void associationCourierToFixedArea(Long id, Long courierId, Long takeTimeId);

}
  
