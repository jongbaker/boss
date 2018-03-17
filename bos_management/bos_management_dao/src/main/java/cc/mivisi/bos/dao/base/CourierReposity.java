package cc.mivisi.bos.dao.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import cc.mivisi.bos.domain.Courier;

/**  
 * ClassName:CourierReposity <br/>  
 * Function:  <br/>  
 * Date:     2018年3月15日 下午8:35:51 <br/>       
 */
public interface CourierReposity extends JpaRepository<Courier, Long>,JpaSpecificationExecutor<Courier> {
	@Modifying
	@Query("update Courier set deltag=1 where id=?")
	void updateDelTagById(long l);

}
  
