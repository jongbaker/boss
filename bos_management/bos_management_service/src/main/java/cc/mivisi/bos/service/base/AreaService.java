package cc.mivisi.bos.service.base;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cc.mivisi.bos.domain.Area;

/**  
 * ClassName:AreaService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月16日 下午7:57:24 <br/>       
 */
public interface AreaService {

	void save(ArrayList<Area> list);

	Page<Area> findAll(Pageable pageable);

	List<Area> findByQ(String q);

}
  
