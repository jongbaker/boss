package cc.mivisi.web.action.base;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cc.mivisi.bos.domain.SubArea;
import cc.mivisi.bos.service.base.SubAreaService;

/**  
 * ClassName:SubareaAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月17日 下午5:02:47 <br/>       
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class SubareaAction extends CommonAction<SubArea> {

	public SubareaAction() {
		super(SubArea.class);  
		// TODO Auto-generated constructor stub  
		
	}
	@Autowired
	private SubAreaService subAreaService;
	

    @Action(value = "subareaAction_save", results = {@Result(name = "success",
            location = "/pages/base/sub_area.html", type = "redirect")})
    public String save() {
    	
    	System.out.println("打印model--------------------"+getModel().toString());
    	
        subAreaService.save(getModel());
        return SUCCESS;
    }

}
  
