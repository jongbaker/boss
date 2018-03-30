package cc.mivisi.bos.dao.base.system;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cc.mivisi.bos.domain.system.Role;

/**  
 * ClassName:RoleJpaRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月29日 下午4:40:04 <br/>       
 */
public interface RoleJpaRepository extends JpaRepository<Role, Long> {

    @Query("select r from Role r inner join r.users u on u.id=? ")
    List<Role> findbyUid(Long id);

}
  
