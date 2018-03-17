package cc.mivisi.web.action.base;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.data.domain.Page;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import cc.mivisi.bos.domain.Area;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**  
 * ClassName:CommonAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月17日 上午10:55:40 <br/>       
 */

//T表示引用数据类型,需要操作谁的时候
public class CommonAction<T> extends ActionSupport implements ModelDriven<T> {
	
	private T model;
	
	private Class<T> clazz;
	

	
	public CommonAction(Class<T> clazz) {

		this.clazz = clazz;
	}
	

	protected int page;
	protected int rows;

	public void setPage(int page) {
		this.page = page;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}


	@Override
	public T getModel() {
		if (model==null) {
			try {
				model=clazz.newInstance();
			} catch (InstantiationException e) {
				  
				// TODO Auto-generated catch block  
				e.printStackTrace();  
				
			} catch (IllegalAccessException e) {
				  
				// TODO Auto-generated catch block  
				e.printStackTrace();  
				
			}
		}
		return model;
	}

	
	public void toJons(JsonConfig jsonConfig ,Page<T> page) throws IOException{
		
		// 总数据条数
		long total = page.getTotalElements();
		// 当前页要实现的内容
		List<T> list = page.getContent();
		// 封装数据
		Map<String, Object> map = new HashMap<>();

		map.put("total", total);
		map.put("rows", list);
		
		String json;
			//jsonconfig有可能为空,那么就会在内部进行计算的时候,会发生空指针,所以需要进行判断
		if (jsonConfig != null) {
			json = JSONObject.fromObject(map, jsonConfig).toString();
		} else {
			json = JSONObject.fromObject(map).toString();
		}

		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write(json);
	}
	
	public void list2json(List<T> list,JsonConfig jsonConfig) throws IOException{
		String json;
        if (jsonConfig != null) {
            json = JSONArray.fromObject(list, jsonConfig).toString();
        } else {
            json = JSONArray.fromObject(list).toString();
        }
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(json);
	}
}
  
