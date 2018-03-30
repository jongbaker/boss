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

import cc.mivisi.bos.domain.system.Menu;
import cc.mivisi.bos.service.system.MenuService;
import cc.mivisi.web.action.base.CommonAction;
import net.sf.json.JsonConfig;

/**  
 * ClassName:MenuAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月28日 下午8:46:29 <br/>       
 */

@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class MenuAction extends CommonAction<Menu> {
    
    @Autowired
    private MenuService menuService;

    public MenuAction() {
          
        super(Menu.class);  
    }
    
    @Action(value="menuAction_findLevelone")
    public String findMenuTree() throws IOException{
        //我应该找谁呢，首先找的是父类，父类有个特征，那就是null
        
        List<Menu> list=menuService.findByLevelOne();
        JsonConfig jsonConfig = new JsonConfig();
        //关于这里为啥也要去掉childrenMenus，因为页面需要的名字不是这个，所以直接添加get方法
        jsonConfig.setExcludes(new String[]{"parentMenu","roles","childrenMenus"});
        
        System.out.println(list);
        list2json(list, jsonConfig);
        
        return NONE;
    }
    
    @Action(value="menuAction_save",results={
            @Result(name="success",location="/pages/system/menu.html",type="redirect")})
    public String save(){
       menuService.save(getModel());
        return SUCCESS;
    }
    
    //这里有与字段menu中一样的字段.所以,page在这里初始化值就是0,页面的page被封装到了menu中.
    @Action(value="menuAciton_pageQuery")
    public String pageQuery() throws IOException{
       Pageable pageRequest = new PageRequest(Integer.parseInt(getModel().getPage())-1, rows);
       
       Page<Menu> page=menuService.findAll(pageRequest);
       
       JsonConfig jsonConfig = new JsonConfig(); 
       jsonConfig.setExcludes(new String[]{"parentMenu","roles","childrenMenus"});
       toJons(jsonConfig, page);
        return NONE;
    }
    
    

}
  
