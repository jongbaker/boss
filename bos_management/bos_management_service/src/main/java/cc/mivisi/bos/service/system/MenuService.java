package cc.mivisi.bos.service.system;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cc.mivisi.bos.domain.system.Menu;
import cc.mivisi.bos.domain.system.User;

/**  
 * ClassName:MenuService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月28日 下午9:00:27 <br/>       
 */

public interface MenuService {

    List<Menu> findByLevelOne();

    void save(Menu model);


    Page<Menu> findAll(Pageable pageRequest);

    List<Menu> findbyUser(User user);

}
  
