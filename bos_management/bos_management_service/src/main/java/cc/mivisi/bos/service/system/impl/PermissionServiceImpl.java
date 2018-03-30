package cc.mivisi.bos.service.system.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cc.mivisi.bos.dao.base.system.PermissionJpaRepository;
import cc.mivisi.bos.domain.system.Permission;
import cc.mivisi.bos.service.system.PermissionService;

/**  
 * ClassName:PermissionServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月29日 下午3:58:11 <br/>       
 */

@Service
@Transactional
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionJpaRepository permissionJpaRepository;

    @Override
    public Page<Permission> findAll(Pageable pageable) {
          
        // TODO Auto-generated method stub  
        return permissionJpaRepository.findAll(pageable);
    }

    @Override
    public void save(Permission model) {
          
        // TODO Auto-generated method stub  
        permissionJpaRepository.save(model);
    }

}
  
