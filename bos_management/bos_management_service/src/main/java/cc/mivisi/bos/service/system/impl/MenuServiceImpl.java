package cc.mivisi.bos.service.system.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cc.mivisi.bos.dao.base.system.MenuJpaRepository;
import cc.mivisi.bos.domain.system.Menu;
import cc.mivisi.bos.service.system.MenuService;

/**  
 * ClassName:MenuServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月28日 下午9:01:08 <br/>       
 */
@Service
@Transactional
public class MenuServiceImpl implements MenuService {
    
    @Autowired
    private MenuJpaRepository menuJpaRepository;

    @Override
    public List<Menu> findByLevelOne() {
          
       
        return  menuJpaRepository.findByParentMenuIsNull();  
    }

    @Override
    public void save(Menu model) {
          //判断是不是父节点,那么就去数据库查找,如果父节点有值,那么就能查到,没哟就查不到..
           Menu parentMenu = model.getParentMenu();
            if (parentMenu!=null&&parentMenu.getId()==null) {
               //框架帮我们new了一个parentMenu,没有id,就是一个瞬时态的对象.但是有地址值
                //我们只有把他重置为null,他才不会去保存一个瞬时态的对象.
                model.setParentMenu(null);
            }
            

            
            //这是有父类id的
            menuJpaRepository.save(model);
    }


    @Override
    public Page<Menu> findAll(org.springframework.data.domain.Pageable pageRequest) {
          
        // TODO Auto-generated method stub  
        return menuJpaRepository.findAll(pageRequest);
    }

}
  
