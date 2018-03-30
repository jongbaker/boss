package cc.mivisi.bos.dao.base.system;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cc.mivisi.bos.domain.system.Menu;

/**  
 * ClassName:MenuJpaRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月28日 下午8:56:17 <br/>       
 */
public interface MenuJpaRepository extends JpaRepository<Menu, Long> {
    List<Menu> findByParentMenuIsNull();



}
  
