package cc.mivisi.bos.service.system.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import cc.mivisi.bos.dao.base.system.RoleJpaRepository;
import cc.mivisi.bos.dao.base.system.UserJpaRepository;
import cc.mivisi.bos.domain.system.Role;
import cc.mivisi.bos.domain.system.User;
import cc.mivisi.bos.service.system.UserService;

/**  
 * ClassName:UserServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月30日 上午11:01:57 <br/>       
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserJpaRepository userJpaRepository;
    
    @Autowired
    private RoleJpaRepository roleJpaRepository;

    @Override
    public void save(User model, Long[] roleIds) {
          
      userJpaRepository.save(model);
      //持有瞬时态才会报瞬时态异常
      
      method01(model, roleIds);
        
    }
    
    
    //游离态---托管态--通过持久态model保存
    public void method01(User model,Long[] roleIds){
        
        if (roleIds!=null&&roleIds.length>0) {
            for (Long roleId : roleIds) {
                
                Role role = new Role();
                role.setId(roleId);
                
                model.getRoles().add(role);
                
            }
        }
        
    };
    
    //持久态(数据库)
    public void method02(User model,Long[] roleIds){
        
        

        if (roleIds!=null&&roleIds.length>0) {
            for (Long roleId : roleIds) {
                
              Role role = roleJpaRepository.findOne(roleId);
                
                model.getRoles().add(role);
                
            }
        }
        
        
        
    }


    @Override
    public  Page<User> findAll(PageRequest pageable) {
          
       Page<User> page=userJpaRepository.findAll(pageable);
        return page;
    };
}
  
