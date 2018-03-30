package cc.mivisi.web.action.base.system;

import java.io.IOException;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableLoadTimeWeaving;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;

import cc.mivisi.bos.domain.system.Role;
import cc.mivisi.bos.service.system.RoleService;
import cc.mivisi.web.action.base.CommonAction;
import net.sf.json.JsonConfig;

/**
 * ClassName:RoleAction <br/>
 * Function: <br/>
 * Date: 2018年3月29日 下午4:42:35 <br/>
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class RoleAction extends CommonAction<Role> {
    @Autowired
    private RoleService roleService;

    public RoleAction() {
        super(Role.class);
    }

    private String ids;
    private Long[] permissionIds;

    public void setPermissionIds(Long[] permissionIds) {
        this.permissionIds = permissionIds;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    @Action("roleAction_pageQuery")
    public String pageQuery() throws IOException {

        PageRequest pageRequest = new PageRequest(page - 1, rows);
        Page<Role> pages = roleService.findAll(pageRequest);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[] {"users", "permissions", "menus"});
        List<Role> list = pages.getContent();
        list2json(list, jsonConfig);
        return NONE;
    }

    @Action(value = "roleAction_save", results = {
            @Result(name = "success", location = "/pages/system/role.html", type = "redirect")})
    public String save() {

        roleService.save(getModel(), ids, permissionIds);

        return SUCCESS;
    }
    
   
    @Action("roleAction_findAll")
    public String findAll() throws IOException{
        
        Page<Role> pages = roleService.findAll(null);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[] {"users", "permissions", "menus"});
        List<Role> list = pages.getContent();
        list2json(list, jsonConfig);
        return NONE;
    }
    

}
