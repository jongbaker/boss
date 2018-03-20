package cc.mivisi.crm.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import cc.mivisi.crm.domain.Customer;


/**  
 * ClassName:CustomerService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月18日 下午4:06:02 <br/>       
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface CustomerService {

		@GET
		@Path("/findAll")
		public List<Customer> findAll();
		
		//查询未关联到分区的客户
		@GET
		@Path("/findCustomerUnAssociated")
		public List<Customer> findCustomerUnAssociated();
		
		//查询到关联到定区的客户
		@GET
		@Path("/findCustomerAssociated")
		public List<Customer> findCustomerAssociated(@QueryParam(value = "fixedAreaId")String fixedAreaId);
		
		
		@PUT
		@Path("/assignCustomers2FixedArea")
		public void assignCustomers2FixedArea(@QueryParam("fixedAreaId")String fixedAreaId,@QueryParam("customerIds")Long[] customerIds);
			
	
}
  
