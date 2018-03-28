package cc.mivisi.bos.dao.base.system;

import org.springframework.data.jpa.repository.JpaRepository;

import cc.mivisi.bos.domain.system.User;

/**  
 * ClassName:UserJpaRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月26日 下午5:47:18 <br/>       
 */
public interface UserJpaRepository extends JpaRepository<User , Long> {

    User findByUsername(String username);

}
  
