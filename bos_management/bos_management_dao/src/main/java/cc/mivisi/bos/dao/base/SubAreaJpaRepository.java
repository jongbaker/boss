package cc.mivisi.bos.dao.base;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import cc.mivisi.bos.domain.FixedArea;
import cc.mivisi.bos.domain.SubArea;

/**  
 * ClassName:SubAreaJpaRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月17日 下午5:12:23 <br/>       
 */
public interface SubAreaJpaRepository extends JpaRepository<SubArea, Long>{

	List<SubArea> findByFixedAreaIsNull();

	List<SubArea> findByFixedArea(FixedArea fixedArea);
	
	
	//错误写法.
	@Modifying
	@Query("update SubArea set fixedArea = null where fixedArea=?")
	void setSubArea2FixedAreaNull(Long fixedArea);
	
	@Modifying
	@Query("update SubArea set fixedArea = ? where id = ?")
	void setSubArea2FixedAreaNull(Long fixedArea, Long subareaId);

}
  
