package cc.mivisi.web.action.base;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Controller;

import cc.mivisi.bos.domain.FixedArea;
import cc.mivisi.bos.domain.crm.Customer;
import cc.mivisi.bos.service.base.FixedAreaService;
import freemarker.core.ReturnInstruction.Return;
import net.sf.json.JsonConfig;

/**
 * ClassName:FixedAreaAction <br/>
 * Function: <br/>
 * Date: 2018年3月18日 下午4:52:32 <br/>
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class FixedAreaAction extends CommonAction<FixedArea> {
	@Autowired
	private FixedAreaService fixedAreaService;

	public FixedAreaAction() {
		super(FixedArea.class);
	}

	@Action(value = "fixedAreaAction_pageQuery")
	public String pageQuery() throws IOException {

		Pageable pageable = new PageRequest(page - 1, rows);
		Page<FixedArea> page = fixedAreaService.findAll(pageable);

		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(new String[] { "couriers", "subareas" });
		System.out.println(page);
		toJons(jsonConfig, page);

		return NONE;
	}

	@SuppressWarnings(value = { "all" })
	@Action("fixedAreaAction_findCustomerUnAssociated")
	public String findCustomerUnAssociated() throws IOException {
		List<Customer> list = (List<Customer>) WebClient
				 .create("http://localhost:8180/crm28/webService/customerService/findCustomerUnAssociated")
				 .accept(MediaType.APPLICATION_JSON)
				 .type(MediaType.APPLICATION_JSON)
				 .getCollection(Customer.class);
		list2json(list, null);
		return NONE;
	}
	
	@SuppressWarnings(value = { "all" })
	@Action("fixedAreaAction_findCustomerAssociated")
	public String findCustomerAssociated() throws IOException{
		List<Customer> list = (List<Customer>) WebClient
				 .create("http://localhost:8180/crm28/webService/customerService/findCustomerAssociated")
				 .query("fixedAreaId",getModel().getId())
				 .accept(MediaType.APPLICATION_JSON)
				 .type(MediaType.APPLICATION_JSON)
				 .getCollection(Customer.class);
		list2json(list, null);
		
		return NONE;
	}
	
	//注意前端页面的书写手法
	private Long[] customerIds;
	
	public void setCustomerIds(Long[] customerIds) {
		this.customerIds = customerIds;
	}
	
	//进行保存用户
	@SuppressWarnings("all")
	@Action(value="fixedAreaAction_assignCustomers2FixedArea",results = {@Result(name = "success",
            location = "/pages/base/fixed_area.html",
            type = "redirect")})
	public String assignCustomers2FixedArea(){
		WebClient.create("http://localhost:8180/crm28/webService/customerService/assignCustomers2FixedArea")
				 .query("fixedAreaId", getModel().getId())
				 .query("customerIds",customerIds)
				 .accept(MediaType.APPLICATION_JSON)
				 .type(MediaType.APPLICATION_JSON)
				 .put(null);//为啥传null
		return SUCCESS;
	}
	
	

	

}
