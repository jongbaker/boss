package cc.mivisi.bos.service.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cc.mivisi.bos.domain.SubArea;

/**  
 * ClassName:SubAreaService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月17日 下午5:09:35 <br/>       
 */
public interface SubAreaService {

	void save(SubArea model);

	Page<SubArea> findAll(Pageable pageable);

	List<SubArea> findUnAssociation();

	List<SubArea> findAssociation(Long id);

	void associationSubArea2FixedArea(Long id, Long[] customerIds);

}
  
