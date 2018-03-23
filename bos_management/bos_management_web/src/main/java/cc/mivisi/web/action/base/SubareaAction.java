package cc.mivisi.web.action.base;

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

import cc.mivisi.bos.domain.SubArea;
import cc.mivisi.bos.service.base.SubAreaService;
import net.sf.json.JsonConfig;

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
    
    @Action("subAreaAction_pageQuery")
    public String pageQuery() throws IOException{
    
    	Pageable pageable = new PageRequest(page-1, rows);
    	Page<SubArea> page=subAreaService.findAll(pageable);
    	
    	JsonConfig jsonConfig = new JsonConfig();
    	jsonConfig.setExcludes(new String[]{"subareas"});
		toJons(jsonConfig, page);
    	
    	return NONE;
    }
    
    @Action("subAreaAction_unAssociation")
    public String unAssociation() throws IOException{
    	List<SubArea> list=subAreaService.findUnAssociation();
    	

    	JsonConfig jsonConfig = new JsonConfig();
    	jsonConfig.setExcludes(new String[]{"subareas"});
    	
    	list2json(list, jsonConfig);
    	
    	return NONE;
    }
    
    @Action("subAreaAction_Association")
    public String Association() throws IOException{
    	List<SubArea> list=subAreaService.findAssociation(getModel().getId());
    	JsonConfig jsonConfig = new JsonConfig();
    	jsonConfig.setExcludes(new String[]{"subareas"});
    	System.out.println(list);
    	list2json(list, jsonConfig);
    	
    	return NONE;
    }
    
    private Long[] customerIds;
    public void setCustomerIds(Long[] customerIds) {
		this.customerIds = customerIds;
	}
    
    @Action(value="subAreaAction_associationSubArea2FixedArea")
    public String associationSubArea2FixedArea(){
    	System.out.println("id:"+getModel().getId());
    	System.out.println("customerIds"+customerIds[0]);
    	
    	subAreaService.associationSubArea2FixedArea(getModel().getId(),customerIds);
    	
    	return NONE;
    }

}
  
