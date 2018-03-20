package cc.mivisi.crm.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import cc.mivisi.crm.domain.Customer;

/**  
 * ClassName:CustomerJpaRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月18日 下午4:10:30 <br/>       
 */
public interface CustomerJpaRepository extends JpaRepository<Customer, Long> {
	//根据语法生成,不需要进行操作.修饰
	List<Customer> findByFixedAreaIdIsNull();

	List<Customer> findByFixedAreaIdIsNotNull();

	List<Customer> findByFixedAreaId(String fixedAreaId);


	@Modifying
	@Query("update Customer set fixedAreaId = null where fixedAreaId=?")
	void resetByFixedAreaId(String fixedAreaId);
	
	@Modifying
	@Query("update Customer set fixedAreaId=? where id=?")
	void fixCustomer(String fixedAreaId, Long customerId);
	
	
}
  
