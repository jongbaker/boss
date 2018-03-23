package cc.mivisi.bos.dao.base.take_delivery;

import org.springframework.data.jpa.repository.JpaRepository;

import cc.mivisi.bos.domain.take_delivery.Order;

/**  
 * ClassName:OrderJpaRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月23日 下午5:43:58 <br/>       
 */
public interface OrderJpaRepository extends JpaRepository<Order, Long> {

}
  
