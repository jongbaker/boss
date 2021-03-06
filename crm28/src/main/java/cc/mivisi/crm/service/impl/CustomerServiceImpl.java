package cc.mivisi.crm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cc.mivisi.crm.dao.CustomerJpaRepository;
import cc.mivisi.crm.domain.Customer;
import cc.mivisi.crm.service.CustomerService;

/**  
 * ClassName:CustomerServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月18日 下午4:09:13 <br/>       
 */
@Service
@Transactional
public class CustomerServiceImpl implements CustomerService{
		
	@Autowired
	private CustomerJpaRepository  customerJpaRepository;
	
	
	
	@Override
	public List<Customer> findAll() {
		  
		// TODO Auto-generated method stub  
		return customerJpaRepository.findAll();
	}


	//查询未关联的客户
	@Override
	public List<Customer> findCustomerUnAssociated() {
		  
		
		return customerJpaRepository.findByFixedAreaIdIsNull();
	}

	//查询指定的关联的客户
	@Override
	public List<Customer> findCustomerAssociated(String fixedAreaId) {
		  
		
		return customerJpaRepository.findByFixedAreaId(fixedAreaId);
	}


	@Override
	public void assignCustomers2FixedArea(String fixedAreaId, Long[] customerIds) {
		  
		
		
		//把该分区的管理者全变为none
		customerJpaRepository.resetByFixedAreaId(fixedAreaId);
		//添加customer
		for (Long customerId : customerIds) {
			customerJpaRepository.fixCustomer(fixedAreaId,customerId);
		}
		
		
	}


	@Override
	public void register(Customer customer) {
		  
		// TODO Auto-generated method stub  
		customerJpaRepository.save(customer);
	}


	@Override
	public void active(String telephone) {
		  System.out.println(telephone);
		// TODO Auto-generated method stub  
		customerJpaRepository.active(telephone);
	}


	@Override
	public Customer checkActive(String telephone) {
		  
		
		return customerJpaRepository.checkActive(telephone);
	}


	@Override
	public Customer findByTelephoneAndPassword(String telephone, String password) {
		  System.out.println("telephone"+telephone+"password"+password);
		// TODO Auto-generated method stub  
		return customerJpaRepository.findByTelephoneAndPassword(telephone,password);
	}


	@Override
	public String findFixedAreaIdByAddress(String address) {
		  
		// TODO Auto-generated method stub  
		return customerJpaRepository.findFixedAreaIdByAddress(address);
	}
	



	
}
  
