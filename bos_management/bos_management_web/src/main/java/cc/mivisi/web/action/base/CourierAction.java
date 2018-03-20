package cc.mivisi.web.action.base;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.bouncycastle.jce.provider.JDKDSASigner.noneDSA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import com.ctc.wstx.util.StringUtil;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import aj.org.objectweb.asm.Type;
import cc.mivisi.bos.domain.Courier;
import cc.mivisi.bos.domain.Standard;
import cc.mivisi.bos.service.base.CourierService;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**  
 * ClassName:CourierAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月15日 下午8:27:10 <br/>       
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class CourierAction extends CommonAction<Courier> {
	public CourierAction() {
		  
		super(Courier.class);  
		
		
	}


	@Autowired
	private CourierService courierService;
	
	private Courier model;
	
	
	@Action(value="courierAction_save",results={@Result(name="success",location="/pages/base/courier.html",type="redirect")})
	public String save(){
		
		courierService.save(model);
		
		return SUCCESS;
	}


	@Override
	public Courier getModel() {
		  
		// TODO Auto-generated method stub  
		if (model==null) {
			model = new Courier();
			
		}
		return model;
	}



	
	//Ajax不需要跳转页面
	@Action("courierAction_pageQuery")
	public String pageQuery() throws IOException{
		
		//其实就是新引入的方法,写写就会了
			Specification<Courier> specification = new Specification<Courier>() {
				 /**
	             * 创建一个查询的where语句
	             * 
	             * @param root : 根对象.可以简单的认为就是泛型对象----------------important
	             * @param cb : 构建查询条件--------------------------------------important
	             * @return a {@link Predicate}, must not be {@literal null}.
	             */
				@Override
				public Predicate toPredicate(Root<Courier> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
					  
					// TODO Auto-generated method stub  
					String courierNum = model.getCourierNum();
					String company = model.getCompany();
					String type = model.getType();
					Standard standard = model.getStandard();
					//存储条件的集合
					ArrayList<Predicate> list = new ArrayList<>();
					//开始判断添加条件
					if (StringUtils.isNotEmpty(courierNum)) {
						//工号
						//构造一个等值查询条件
						Predicate p1 = cb.equal(root.get("courierNum").as(String.class), courierNum);
						list.add(p1);
					}
					if(StringUtils.isNotEmpty(company)){
						//公司
						//继续构造一个等值查询条件
						Predicate p2 = cb.equal(root.get("company").as(String.class), company);
						list.add(p2);
					}
					
					if(StringUtils.isNotEmpty(type)){
						Predicate p3 = cb.equal(root.get("type").as(String.class),type);
						list.add(p3);
						
					}
					//不是很---------------??
					if (standard!=null) {
						String name = standard.getName();
						if(StringUtils.isNotEmpty(name)){
							//连表查询,查询标准名字
							Join<Object, Object> join = root.join("standard");
							Predicate p4 = cb.equal(join.get("name").as(String.class), name);
							list.add(p4);
						}
					}
					//没有输入条件
					
					System.out.println("list size:"+list.size());
					if (list.size()==0) {
						return null;
					}
					//用户输入了多条件查询,
					//将多个条件进行组合----构建数据
					Predicate[] arr=new Predicate[list.size()];
					//就是由于这里没有写,所以就空指针了
					list.toArray(arr);
					//用户输入了多少条件ina,就让多少条件同时满足
					Predicate predicate = cb.and(arr);
					return predicate;
				}
			};
		

		
		//pageable不知道是啥-----------属于SpringData里面的东西,可以用于分页
		Pageable pageable = new PageRequest(page-1, rows);
		System.out.println(courierService);
		System.out.println(specification);
		Page<Courier> page = courierService.findAll(specification,pageable);
		
		//进行数据封装并转换到json
		long total = page.getTotalElements();
		List<Courier> list = page.getContent();
		
		//封装
		HashMap<String, Object> map = new HashMap<String,Object>();
		map.put("total", total);
		map.put("rows", list);
		
		//使用json-lib来进行封装
		//复习.map和object  JSONObject
		// * 	  数组,list,集合     JSONArray
		 //* 
		 
		//灵活控制输出内容
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(new String[]{"fixedAreas", "takeTime"});
		
		//String json = JSONObject.jsc(map).toString();
		String json = JSONObject.fromObject(map, jsonConfig).toString();
		
		HttpServletResponse response = ServletActionContext.getResponse();
		
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write(json);
		
		return NONE;
	}
	
	//使用属性驱动来注入数据
	private String ids;
	public void setIds(String ids) {
		this.ids = ids;
	}
	
	@Action(value="courierAction_batchDel",results={@Result(name="success",type="redirect",location="/pages/base/courier.html")})
	public String del(){
		//在下一层切分ids,这样的话就可以快速调用dao层来修改数据
		courierService.del(ids);
		return NONE;
	}
	
	@Action("courierAction_listajax")
	public String findAll() throws IOException{
		
		System.out.println("courierAction_listajax.....come..........");
		Specification<Courier> specification = new Specification<Courier>() {

			@Override
			public Predicate toPredicate(Root<Courier> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				  
				 /**CriteriaBuilder  ------------一个构建安全查询者
	             * 创建一个查询的where语句
	             * 
	             * @param root : 根对象.可以简单的认为就是泛型对象----------------important
	             * @param cb : 构建查询条件--------------------------------------important
	             * @return a {@link Predicate}, must not be {@literal null}.
	             */
				//查询所有的在职的快递员
				//比较空值
				//判断表达式是否为空------记住固定写法
				//1.创建一个对应于被引用属性的路径。---源码
				//2. 对表达式执行一个类型转换，返回一个新的表达式对象 
				//Perform a typecast upon the expression, returning a new  expression object.
				 
				Predicate predicate = cb.isNull(root.get("deltag").as(Character.class));
				return predicate;
			}
		};
		//关于这里为啥传输null,可以看SimpleJpaRepository
		//return pageable == null ? new PageImpl<T>(query.getResultList()) : readPage(query, pageable, spec);
		Page<Courier> p = courierService.findAll(specification, null);
	
		//Returns the page content as {@link List}.
		//返回该page的list形式的内容
		List<Courier> list = p.getContent();
		System.out.println(list);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(new String[]{"fixedAreas","takeTime"});
		
		list2json(list, jsonConfig);
		
		return NONE;
	}
	
	@Action("courierAction_listajax2")
	public String findAll2() throws IOException{
		
		List<Courier> list=courierService.findByDeltagIsNotNull();
		
	
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(new String[]{"fixedAreas","takeTime"});
		
		list2json(list, jsonConfig);
		
		
		return NONE;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
  
