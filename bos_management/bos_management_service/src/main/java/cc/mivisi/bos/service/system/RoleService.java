package cc.mivisi.bos.service.system;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import cc.mivisi.bos.domain.system.Role;

/**  
 * ClassName:RoleService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月29日 下午4:40:59 <br/>       
 */
public interface RoleService {

    Page<Role> findAll(PageRequest pageRequest);


    void save(Role model, String ids, Long[] permissionIds);

}
  
