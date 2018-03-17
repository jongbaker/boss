package cc.mivisi.web.action.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;

import cc.mivisi.bos.domain.Area;
import cc.mivisi.bos.service.base.AreaService;
import cc.mivisi.utils.PinYin4jUtils;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * ClassName:AreaAction <br/>
 * Function: <br/>
 * Date: 2018年3月16日 下午7:06:37 <br/>
 */
// struts
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class AreaAction extends CommonAction<Area> {
	
	
	public AreaAction() {
		  
		super(Area.class);  
		
		
	}

	@Autowired
	private AreaService areaService;

	// 使用set获取接收
	private File file;

	public void setFile(File file) {
		this.file = file;
	}

	@Action(value="areaAction_importXLS",results={@Result(name="success",location="/pages/base/area.html",type="redirect")})
	public String getfile(){
	
		try {
			//1、 得到Excel常用对象  
			HSSFWorkbook hssfWorkbook = new HSSFWorkbook(new FileInputStream(file));
			//2.读取第一个工作簿
			HSSFSheet sheet = hssfWorkbook.getSheetAt(0);
			//3.存储对象的集合
			ArrayList<Area> list = new ArrayList<>();
			
			//开始遍历读取到的工作薄
			for (Row row : sheet) {
				//第一行不需要读取,跳过第一行
				if(row.getRowNum()==0){
					continue;
				}
				//读取单远哥的内容
				String province = row.getCell(1).getStringCellValue();
				System.out.println("第一次"+province);
				String city = row.getCell(2).getStringCellValue();
				String district = row.getCell(3).getStringCellValue();
				String postcode = row.getCell(4).getStringCellValue();
				
				//截掉最后一个字符
				province = province.substring(0, province.length()-1);
				System.out.println("第二次"+province);
				city = city.substring(0, city.length()-1);
				district = district.substring(0, district.length()-1);
				
				//获取城市编码和简码
				String citycode = PinYin4jUtils.hanziToPinyin(city, "").toUpperCase();
				String[] headByString = PinYin4jUtils.getHeadByString(province+city+district);
				String shortcode = PinYin4jUtils.stringArrayToString(headByString);
			
				//封装数据
				Area area = new Area();
				
				area.setProvince(province);
				area.setCity(city);
				area.setDistrict(district);
				area.setPostcode(postcode);
				area.setCitycode(citycode);
				area.setShortcode(shortcode);
				System.out.println(area);
				list.add(area);
			}
			
			areaService.save(list);
			//释放资源
			hssfWorkbook.close();
		} catch (FileNotFoundException e) {
			  
			// TODO Auto-generated catch block  
			e.printStackTrace();  
			
		} catch (IOException e) {
			  
			// TODO Auto-generated catch block  
			e.printStackTrace();  
			
		}
		return SUCCESS;
	}

	@Action("areaAction_pageQuery")
	public String pageQuery() throws IOException {
		
		//这里这一段有areaService,不方便抽取.
		//jsonConfig 因为不确定是排除什么条件所以,在这里确定条件在传过去
		//抽取原则---本质就是抽取共性的东西
		//成员元变量不可抽取,一起他使用的参数,变量方法都不可以抽取
		//不确定因素不可以抽取
		
		Pageable pageable = new PageRequest(page - 1, rows);
		
		Page<Area> page = areaService.findAll(pageable);
		
		//jsonConfig 因为不确定是排除什么条件所以,在这里确定条件在传过去
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(new String[] { "subareas" });
		
		toJons(jsonConfig, page);
	
		return NONE;
	}
	
	   private String q;

	    public void setQ(String q) {
	        this.q = q;
	    }
	@Action("areaAction_findAll")
	public String findAll() throws IOException{
		List<Area> list=new ArrayList<>();
		if (StringUtils.isNotEmpty(q)) {
			//根據用戶模糊查詢
			list=areaService.findByQ(q);
		}else{
			//查詢所有
			Page<Area> page = areaService.findAll(null);
			
			list = page.getContent();
		}
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(new String[]{"subareas"});
		list2json(list, jsonConfig);
		
		return NONE;
	}

}
