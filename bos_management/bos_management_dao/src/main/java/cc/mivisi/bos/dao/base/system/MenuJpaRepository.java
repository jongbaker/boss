package cc.mivisi.bos.dao.base.system;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cc.mivisi.bos.domain.system.Menu;

/**  
 * ClassName:MenuJpaRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月28日 下午8:56:17 <br/>       
 */
public interface MenuJpaRepository extends JpaRepository<Menu, Long> {
    List<Menu> findByParentMenuIsNull();
    
    @Query("select m from Menu m inner join m.roles r inner join r.users u on u.id=?")
    List<Menu> findbyUser(Long id);
    
}
  
