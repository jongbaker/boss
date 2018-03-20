package cc.mivisi.bos.fore.web.action;

import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.junit.Test;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.aliyuncs.exceptions.ClientException;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import cc.mivisi.bos.fore.domain.Customer;
import cc.mivisi.utils.SmsUtils;

/**
 * ClassName:CustomerAction <br/>
 * Function: <br/>
 * Date: 2018年3月20日 下午4:43:29 <br/>
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class CustomerAction extends ActionSupport implements ModelDriven<Customer> {

	private Customer model;

	@Override
	public Customer getModel() {
		if (model == null) {
			model= new Customer();
		}

		return model;
	}
	private String telephone;
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	

	
	@Action("customerAction_sendSms")
	public String sendSms(){
		String code = RandomStringUtils.randomNumeric(6);
		try {
			//SmsUtils.sendSms(telephone, checkcode);
			System.out.println("code------------"+code);
			ServletActionContext.getRequest().getSession().setAttribute("code", code);
			
		} catch (Exception e) {
			  
			// TODO Auto-generated catch block  
			e.printStackTrace();  
		}
		return NONE;
	}
	
	
	private String checkcode;
	public void setCheckcode(String checkcode) {
		this.checkcode = checkcode;
	}
	
	
	
	
	
	@Action(value="customerAction_regist",results={
			@Result(name="success",location="/signup-success.html",type="redirect"),
			@Result(name="error",location="/signup-fail.html",type="redirect")
	})
	public String regist(){
		String code = (String) ServletActionContext.getRequest().getSession().getAttribute("code");
		if(code.equals(checkcode)){
			WebClient
				.create("http://localhost:8180/crm28/webService/customerService/register")
				.accept(MediaType.APPLICATION_JSON)
				.type(MediaType.APPLICATION_JSON)
				.post(model);
			return SUCCESS;
		}
		return ERROR;
	}

}
