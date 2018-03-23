package cc.mivisi.bos.fore.web.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import cc.mivisi.bos.domain.Area;
import cc.mivisi.bos.domain.take_delivery.Order;

/**  
 * ClassName:OrderAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月23日 下午3:59:22 <br/>       
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class OrderAction extends ActionSupport implements ModelDriven<Order> {

	
	private Order model;
	
	@Override
	public Order getModel() {
		if (model==null) {
			model= new Order();
		}  
		return model;
	}
	//使用属性驱动接收详细地址
	private String recAreaInfo;
	private String sendAreaInfo;
	public void setRecAreaInfo(String recAreaInfo) {
		this.recAreaInfo = recAreaInfo;
	}
	
	public void setSendAreaInfo(String sendAreaInfo) {
		this.sendAreaInfo = sendAreaInfo;
	}
	
	
	@Action(value="orderAction_add",results={@Result(name="success",location="/index.html",type="redirect")})
	public String orderAdd() throws IOException{
		//获取发件区域的数据
		if(StringUtils.isNotEmpty(sendAreaInfo)){
			System.out.println(sendAreaInfo);
		//开始切割数据
		String[] str = sendAreaInfo.split("/");

			//去掉最后一个字
		 String province = str[0].substring(0,str[0].length()-1);
		 String city = str[1].substring(0,str[1].length()-1);
		 String district = str[2].substring(0,str[2].length()-1);
		 //封装area,注意这里是瞬时态的地址
		 Area area = new Area();
		 area.setProvince(province);
		 area.setCity(city);
		 area.setDistrict(district);
		 
		 //写到模型中去
		 model.setSendArea(area);
		}
		
		if(StringUtils.isNotEmpty(recAreaInfo)){
			System.out.println(recAreaInfo);
			//开始切割数据
			String[] str = sendAreaInfo.split("/");
			
			//去掉最后一个字
			String province = str[0].substring(0,str[0].length()-1);
			String city = str[1].substring(0,str[1].length()-1);
			String district = str[2].substring(0,str[2].length()-1);
			//封装area,注意这里是瞬时态的地址
			Area recArea = new Area();
			recArea.setProvince(province);
			recArea.setCity(city);
			recArea.setDistrict(district);
			
			//写到模型中去
			model.setRecArea(recArea);
		}
		
		System.out.println(model);
		
		//调用webService
		//crm是客户资源管理系统,后台 是处理数据的,所以去bosmanagement中去调用业务.
		WebClient.
				create("http://localhost:8880/bos_management_web/webService/orderService/saveOrder")
				.type(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.post(model);
		//创建webservice的步骤
		//1.导包
		//2.web.xml设置CXFServlet
		//3.在applicationContext.xml中使用扫描
		//3.在需要使用的bean中使用xmlRootElement进行标注
	
		//4.在service层直接书写接口
		
		
		
		
		
	
		return SUCCESS;
	}
	
	
}
  
