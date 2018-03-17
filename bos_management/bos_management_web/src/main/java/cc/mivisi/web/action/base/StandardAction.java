package cc.mivisi.web.action.base;




import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
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
import com.opensymphony.xwork2.ModelDriven;

import cc.mivisi.bos.domain.Standard;
import cc.mivisi.bos.service.base.StandardService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**  
 * ClassName:StandardAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月14日 下午9:51:08 <br/>       
 */
//struts注解
@Namespace("/")
@ParentPackage("struts-default")
//spirng注解
@Controller
@Scope("prototype")
public class StandardAction  extends ActionSupport implements ModelDriven<Standard> {

	private Standard model;
	
	@Autowired
	private StandardService standardService;
	
	@Override
	public Standard getModel() {
		  
		if (model==null) {
			
			model=new Standard();
		}
		return model;
	}
	@Action(value="standardAction_save" ,results={@Result(name="success",location="/pages/base/standard.html",type="redirect")})
	public String save(){
		standardService.save(model);
		return SUCCESS;
	}
	
	private int page;
	private int rows;
	public void setPage(int page) {
		this.page = page;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	
	//Ajax不需要跳转页面
	@Action("standardAction_pageQuery")
	public String pageQuery() throws IOException{
		//pageable不知道是啥-----------属于SpringData里面的东西,可以用于分页
		Pageable pageable = new PageRequest(page-1, rows);
		Page<Standard> page = standardService.findAll(pageable);
		
		//进行数据封装并转换到json
		long total = page.getTotalElements();
		List<Standard> list = page.getContent();
		
		//封装
		HashMap<String, Object> map = new HashMap<String,Object>();
		map.put("total", total);
		map.put("rows", list);
		
		//使用json-lib来进行封装
		/*复习.map和object  JSONObject
		 * 	  数组,list,集合     JSONArray
		 * 
		 */
		String json = JSONObject.fromObject(map).toString();
		HttpServletResponse response = ServletActionContext.getResponse();
		
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write(json);
		
		
		return NONE;
	}
	
	//查询所有的派送标准
	@Action(value="standard_findAll")
	public String findAll() throws IOException{
		
		//数据查询
		Page<Standard> page = standardService.findAll(null);
		//获取页面的内容
		List<Standard> list = page.getContent();
		
		JSONArray json = JSONArray.fromObject(list);
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write(json.toString());
		
		
		
		return NONE;
	}
	
	   
}
  
