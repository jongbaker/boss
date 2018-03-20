package cc.mivisi.web.action.base;

import java.io.IOException;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;

import cc.mivisi.bos.domain.TakeTime;
import cc.mivisi.bos.service.base.TakeTimeService;

/**  
 * ClassName:TakeTimeAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月20日 下午12:58:13 <br/>       
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class TakeTimeAction extends CommonAction<TakeTime> {
	@Autowired
	private TakeTimeService takeTimeService;

	public TakeTimeAction() {
		  
		super(TakeTime.class);  
		// TODO Auto-generated constructor stub  
		
	}
	@Action(value="takeTimeAction_listajax")
	public String listajax() throws IOException{
		List<TakeTime> list=takeTimeService.findAll();
		
		list2json(list, null);
		return NONE;
	}

}
  
