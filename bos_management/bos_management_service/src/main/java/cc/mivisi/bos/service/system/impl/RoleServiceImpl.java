package cc.mivisi.bos.service.system.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import cc.mivisi.bos.dao.base.system.MenuJpaRepository;
import cc.mivisi.bos.dao.base.system.PermissionJpaRepository;
import cc.mivisi.bos.dao.base.system.RoleJpaRepository;
import cc.mivisi.bos.domain.system.Menu;
import cc.mivisi.bos.domain.system.Permission;
import cc.mivisi.bos.domain.system.Role;
import cc.mivisi.bos.service.system.RoleService;

/**  
 * ClassName:RoleServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月29日 下午4:41:25 <br/>       
 */

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
        
    @Autowired
    private RoleJpaRepository roleJpaRepository;
    
    @Autowired
    private MenuJpaRepository menuJpaRepository;
    
    @Autowired 
    private PermissionJpaRepository permissionJpaRepository;

    @Override
    public Page<Role> findAll(PageRequest pageRequest) {
          
        return roleJpaRepository.findAll(pageRequest);
    }


    @Override
    public void save(Role model,String ids, Long[] permissionIds) {
        //ids--权限文件夹???
        
        //permissionIds 权限????
        roleJpaRepository.save(model);
        method01(model, ids, permissionIds);
        
    }
    
    
    
    //创建托管态
    
    public void method01(Role model,String ids, Long[] permissionIds){
        
        if (StringUtils.isNotEmpty(ids)) {
            String[] split = ids.split(",");
            
            for (String menuId : split) {
                Menu menu = new Menu();
                menu.setId(Long.parseLong(menuId));
                model.getMenus().add(menu);
            }
        }
        
        if (permissionIds!=null&&permissionIds.length>0) {
            for (Long permissionId : permissionIds) {
                Permission permission = new Permission();
                permission.setId(permissionId);
                model.getPermissions().add(permission);
            }
        }
        
    }
    
    //游离态---持久态.
    public void method02 (Role model, String ids, Long[] permissionIds){
        
        if (StringUtils.isNotEmpty(ids)) {
               String[] split = ids.split(",");
            
            for (String menuId : split) {
                Menu menu=menuJpaRepository.findOne(Long.parseLong(menuId));
            
              model.getMenus().add(menu);
            }
        }
        //注意条件判断为啥,这就要思考,数组什么情况下才能遍历,全部考虑进去
        if (permissionIds!=null&&permissionIds.length>0) {
            for (Long permissionId : permissionIds) {
               Permission permission=permissionJpaRepository.findOne(permissionId);
               model.getPermissions().add(permission);
            }
        }
        
        
    }
        
    
}
  
