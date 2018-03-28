package cc.mivisi.bos.service.system;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.util.finder.ClassFinder.Info;

import cc.mivisi.bos.dao.base.system.UserJpaRepository;
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
    
    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            info.addStringPermission("courier");
        
        return info;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        System.out.println("ssssssssssssss");
        UsernamePasswordToken usernamePasswordToken =(UsernamePasswordToken)token;
        User user=userJpaRepository.findByUsername(usernamePasswordToken.getUsername()); 
        System.out.println("user"+user);
        if (user!=null) {
            //为啥么
           SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(),getName());
           return info;
        }
        return null;
    }

}
  
