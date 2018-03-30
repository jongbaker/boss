package cc.mivisi.web.action.base.system;

import java.io.IOException;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;

import cc.mivisi.bos.domain.system.Permission;
import cc.mivisi.bos.service.system.PermissionService;
import cc.mivisi.web.action.base.CommonAction;
import net.sf.json.JsonConfig;
import schemasMicrosoftComOfficeOffice.impl.STInsetModeImpl;

/**  
 * ClassName:PermissionAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月29日 下午3:58:52 <br/>       
 */

@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class PermissionAction extends CommonAction<Permission> {
    @Autowired
    private PermissionService permissionService;

    public PermissionAction( ) {
        super(Permission.class);  
    }
    
    @Action("permissionAction_pageQuery")
    public String pageQuery() throws IOException{
       
        Pageable pageable=new PageRequest(page-1, rows);
        
        Page<Permission> pages=permissionService.findAll(pageable);
        List<Permission> list = pages.getContent();
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[]{"roles"});
        list2json(list, jsonConfig);
        
        return NONE;
    }
    
  @Action(value="permissionAction_save",results={
          @Result(name="success",location="/pages/system/permission.html",type="redirect")
          })
  public String save(){
      permissionService.save(getModel());
      
      return SUCCESS;
  }

  @Action("permissionAction_findAll")
  public String findAll() throws IOException{
      //关于这里为啥传null就可以呢,底层看不懂,只能说拼经验
      Page<Permission> page = permissionService.findAll(null);
      
      List<Permission> list = page.getContent();
      
      JsonConfig jsonConfig = new JsonConfig();
      jsonConfig.setExcludes(new String[]{"roles"});
      list2json(list, jsonConfig);
      
      return NONE;
  }
  
  
}
  
