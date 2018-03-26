package cc.mivisi.web.action.base;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.bouncycastle.jce.provider.JDKDSASigner.noneDSA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;

import cc.mivisi.bos.domain.take_delivery.WayBill;
import cc.mivisi.bos.service.take_delivery.WaybillService;

/**  
 * ClassName:WaybillAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月25日 下午11:32:56 <br/>       
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class WaybillAction extends CommonAction<WayBill> {
        
   @Autowired
   private WaybillService waybillService;
    
    public WaybillAction() {
          
        super(WayBill.class);  
        // TODO Auto-generated constructor stub  
        
    }
    
    @Action("waybillAction_save")
    public String save(){
        waybillService.save(getModel());
        return NONE;
    }

    
}
  
