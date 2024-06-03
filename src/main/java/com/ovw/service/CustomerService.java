package com.ovw.service;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.ovw.bean.Admin;
import com.ovw.bean.Customer;

public interface CustomerService {
	
	Customer createCustomer(Customer customer, MultipartFile image) throws IOException;
	
	ResponseEntity<?> updateCustomer(long customerId, Customer updatedCustomer);
	
	ResponseEntity<?> deleteCustomer(long cutsomerId);
	
	Customer getCustomerById(long customerId);
	
	List<Customer> getAllCustomers();

	
}
