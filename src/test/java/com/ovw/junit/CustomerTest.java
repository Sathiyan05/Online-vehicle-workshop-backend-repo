package com.ovw.junit;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ovw.bean.Customer;
import com.ovw.controller.CustomerController;


@SpringBootTest
class CustomerTest {
	
	
	@Autowired
	CustomerController cc;

	@Test
	void getCustomerById() {
		Customer cust=cc.getCustomerById(1);
		assertNotNull(cust);
	}
//	
//	
	//Negative Cases
	@Test
	void getCustomerByIdNull() {
		Customer cust=cc.getCustomerById(4);
		assertNull(cust);
	}
//	
//	
	@Test
	void getAllCustomer() {
		List<Customer> cust=cc.getAllCustomers();
		assertNotNull(cust);		
	}

}
