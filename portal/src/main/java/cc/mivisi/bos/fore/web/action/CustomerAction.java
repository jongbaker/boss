package cc.mivisi.bos.fore.web.action;

import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.ObjectUtils.Null;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.junit.Test;
import org.omg.PortableInterceptor.ACTIVE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;

import com.aliyuncs.exceptions.ClientException;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import cc.mivisi.bos.fore.domain.Customer;
import cc.mivisi.bos.fore.utils.MailUtils;
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
			model = new Customer();
		}

		return model;
	}

	private String telephone;

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	@Action("customerAction_sendSms")
	public String sendSms() {
		String code = RandomStringUtils.randomNumeric(6);
		try {
			// SmsUtils.sendSms(telephone, checkcode);
			System.out.println("code------------" + code);
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

	@Autowired
	private RedisTemplate<String, String> template;

	@Action(value = "customerAction_regist", results = {
			@Result(name = "success", location = "/signup-success.html", type = "redirect"),
			@Result(name = "error", location = "/signup-fail.html", type = "redirect") })
	public String regist() {
		String code = (String) ServletActionContext.getRequest().getSession().getAttribute("code");
		if (code.equals(checkcode)) {
			WebClient.create("http://localhost:8180/crm28/webService/customerService/register")
					.accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON).post(model);
			// 1.生成校验码,2.存到redis
			String emailcode = RandomStringUtils.randomNumeric(32);
			// 怎样存呢,采用手机号的作为key,这样就能保证唯一
			template.opsForValue().set(model.getTelephone(), emailcode);

			// 设置连接---拼接地址
			String url = "http://localhost:8080/portal/customerAction_activeAcount.action?emailcode=" + emailcode
					+ "&telephone=" + model.getTelephone();
			// 发送到邮箱需要携带什么信息
			MailUtils.sendMail(model.getEmail(), "激活邮件", "感谢您注册本网站,请在24小时之内<a href='" + url + "'>激活</a>");

			return SUCCESS;
		}
		return ERROR;

	}

	private String emailcode;

	public void setEmailcode(String emailcode) {
		this.emailcode = emailcode;
	}

	@Action(value = "customerAction_activeAcount", results = {
			@Result(name = "success", location = "http://localhost:8080/portal/login.html", type = "redirect"),
			@Result(name = "isActived", location = "http://localhost:8080/portal/checked.html", type = "redirect"),
			@Result(name = "error", location = "http://localhost:8080/portal/signup-fail.html", type = "redirect") })
	public String activeAcount() {
		String rediscode = template.opsForValue().get(model.getTelephone());
		// 这里到底是用Stringutils呢还是比较null
		if (emailcode != null && rediscode != null && emailcode.equals(rediscode)) {
			// 防止用户二次点击,需要在点击之前进行校验,有没有激活
			Customer customer = WebClient.create("http://localhost:8180/crm28/webService/customerService/checkActive")
					.accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON)
					.query("telephone", model.getTelephone()).get(Customer.class);
			if (customer != null) {
				return "isActived";
			}

			// 激活操作
			WebClient.create("http://localhost:8180/crm28/webService/customerService/active")
					.accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON)
					.query("telephone", model.getTelephone()).put(null);
			System.out.println("22222222222222222222222222222222222222");
			return SUCCESS;
		}

		return ERROR;
	}


	

	@Action(value = "customerAction_login", results = {
			@Result(name = "success", location = "/index.html", type = "redirect"),
			@Result(name = "error", location = "/login2.html", type = "redirect"),
			@Result(name = "unactived", location = "/login3.html", type = "redirect") })
	public String login() {
		// 校验验证码
		String code = (String) ServletActionContext.getRequest().getSession().getAttribute("validateCode");
		System.out.println("code" + code);
		System.out.println("vcode" + checkcode);
		// TODO
		if (StringUtils.isNotEmpty(checkcode) && StringUtils.isNotEmpty(code) && code.equals(checkcode)) {
			// 判断用户是否激活
			Customer customer = WebClient.create("http://localhost:8180/crm28/webService/customerService/checkActive")
					.accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON)
					.query("telephone", model.getTelephone()).get(Customer.class);

			// 激活了
			if (customer != null) {
				Customer cc = WebClient.create("http://localhost:8180/crm28/webService/customerService/login")
						.accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON)
						.query("telephone", model.getTelephone())
						.query("password", model.getPassword())
						.get(Customer.class);
				System.out.println(cc);
				if (cc != null) {
					ServletActionContext.getRequest().getSession().setAttribute("user", cc);
					System.out.println("success");
					return SUCCESS;
				} else {
					System.out.println("error");
					return ERROR;
				}
			} else {
				System.out.println("unactived");
				return "unactived";
			}
		}
		System.out.println("laast");
		return NONE;
	}

}
