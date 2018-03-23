package cc.mivisi.bos.dao.base;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cc.mivisi.bos.domain.Area;

/**  
 * ClassName:AreaReposity <br/>  
 * Function:  <br/>  
 * Date:     2018年3月16日 下午9:19:52 <br/>       
 */
public interface AreaReposity extends JpaRepository<Area, Long> {
	
	@Query("from Area where province like ?1 or city like ?1 or district like ?1 or postcode like ?1 or shortcode like ?1 or citycode like ?1")
	List<Area> findByQ(String q);

	Area findByProvinceAndCityAndDistrict(String province, String city, String district);

}
  
