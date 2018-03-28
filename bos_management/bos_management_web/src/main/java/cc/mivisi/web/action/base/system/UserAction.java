package cc.mivisi.web.action.base.system;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cc.mivisi.bos.domain.system.User;
import cc.mivisi.web.action.base.CommonAction;

/**  
 * ClassName:UserAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月26日 下午5:51:13 <br/>       
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class UserAction extends CommonAction<User> {

    public UserAction() {
        super(User.class);  
    }
    
    private String checkcode;
    public void setCheckcode(String checkcode) {
        this.checkcode = checkcode;
    }
    
    @Action(value="userAction_Login",results={
            @Result(name="success",location="/index.html",type="redirect"),
            @Result(name="login",location="/login.html",type="redirect")
            })
    public String login(){
        System.out.println("-----------");
        String validatecode = (String) ServletActionContext.getRequest().getSession().getAttribute("validateCode");
        System.out.println(validatecode);
        System.out.println(checkcode);
        
        if (StringUtils.isNotEmpty(checkcode)
                &&StringUtils.isNotEmpty(validatecode)
                &&validatecode.equals(checkcode)) {
          
            //获取当前校验主体
            Subject subject = SecurityUtils.getSubject();
            
            AuthenticationToken token=new UsernamePasswordToken(getModel().getUsername(), getModel().getPassword());
            try {
                
                subject.login(token);
                
                //方法防护值有第一个参数确定.
                User user = (User) subject.getPrincipal();
                
                ServletActionContext.getRequest().getSession().setAttribute("user", user);
                return SUCCESS;
            } catch (UnknownAccountException e) {
                  
                
                e.printStackTrace();  
                System.out.println("用户名错误");
            } catch (IncorrectCredentialsException e) {
                
                // TODO Auto-generated catch block  

                e.printStackTrace();  
                System.out.println("密码错误");
            } catch (Exception e) {
                
                // TODO Auto-generated catch block  
                e.printStackTrace();  
                System.out.println("其他错误");
                
            }
            
        }
        System.out.println("nnnnnnnnnnnnnnnnnnnnn");
        
        return LOGIN;
    }

}
  
