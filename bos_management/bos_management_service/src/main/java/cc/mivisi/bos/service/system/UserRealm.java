package cc.mivisi.bos.service.system;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.util.finder.ClassFinder.Info;

import cc.mivisi.bos.dao.base.system.PermissionJpaRepository;
import cc.mivisi.bos.dao.base.system.RoleJpaRepository;
import cc.mivisi.bos.dao.base.system.UserJpaRepository;
import cc.mivisi.bos.domain.system.Permission;
import cc.mivisi.bos.domain.system.Role;
import cc.mivisi.bos.domain.system.User;

/**  
 * ClassName:UserRealm <br/>  
 * Function:  <br/>  
 * Date:     2018年3月26日 下午5:49:04 <br/>       
 */
@Component
public class UserRealm extends AuthorizingRealm {
    @Autowired
    private UserJpaRepository userJpaRepository;
    @Autowired
    private RoleJpaRepository roleJpaRepository;
    @Autowired
    private PermissionJpaRepository permissionJpaRepository;
    
    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //进行授权的类
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            
        //获取当前用户
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        String name = user.getUsername();
        if ("admin".equals(name)) {
            //授权全部权限
            List<Role> roles = roleJpaRepository.findAll();
            for (Role role : roles) {
                //?有什么用呢
                info.addRole(role.getKeyword());
                
            }
            List<Permission> permissions = permissionJpaRepository.findAll();
            for (Permission permission : permissions) {
                info.addStringPermission(permission.getKeyword());
            }
            
        }else {
            //不使用springjpag规则
            List<Role> roles=roleJpaRepository.findbyUid(user.getId());
            for (Role role : roles) {
                //?有什么用呢
                info.addRole(role.getKeyword());
                
            }
            List<Permission> permissions=permissionJpaRepository.findbyUid(user.getId());
            for (Permission permission : permissions) {
                info.addStringPermission(permission.getKeyword());
            }
            
            
          
        }
        
        
        
        
        
        
        return null;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
       
        UsernamePasswordToken usernamePasswordToken =(UsernamePasswordToken)token;
        User user=userJpaRepository.findByUsername(usernamePasswordToken.getUsername()); 
        System.out.println("user"+user);
        if (user!=null) {
            //为啥么----内部源码校验
           SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(),getName());
           return info;
        }
        return null;
    }

}
  
