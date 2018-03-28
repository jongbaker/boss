package cc.mivisi.web.action.base;

import java.util.UUID;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.opensymphony.xwork2.ActionSupport;

/**  
 * ClassName:DemoAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月27日 下午10:40:56 <br/>       
 */
public class DemoAction extends ActionSupport {
    
    @Action(value="DemoAction_importXLS",interceptorRefs={},results={@Result(name="success",location="/pages/base/area.html",type="redirect")})
    public String demo01(){
      
        
        return NONE;
    }

}
  
