package cc.mivisi.bos.dao.base.system;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cc.mivisi.bos.domain.system.Permission;

/**  
 * ClassName:PermissionJpaRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月29日 下午3:55:41 <br/>       
 */
public interface PermissionJpaRepository extends JpaRepository<Permission, Long> {

    @Query("select p from Permission p inner join p.roles r inner join r.users u on u.id = ? ")
    List<Permission> findbyUid(Long id);

}
  
