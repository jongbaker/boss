package cc.mivisi.bos.service.system;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import cc.mivisi.bos.domain.system.User;

/**  
 * ClassName:UserService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月30日 上午11:01:36 <br/>       
 */
public interface UserService {

    void save(User model, Long[] roleIds);

     Page<User> findAll(PageRequest pageable);

}
  
