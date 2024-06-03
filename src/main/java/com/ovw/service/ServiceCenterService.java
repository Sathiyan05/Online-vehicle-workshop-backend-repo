package com.ovw.service;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.ovw.bean.Customer;
import com.ovw.bean.ServiceCenter;

/**
 * 
 */
public interface ServiceCenterService {

	ServiceCenter createServiceCenter(ServiceCenter serviceCenter, MultipartFile image) throws IOException;

	ResponseEntity<?> updateServiceCenter(long customerId, ServiceCenter updatedCustomer, long adminId);
	
	ResponseEntity<?> updateServiceProfile(long customerId, ServiceCenter updatedCustomer);

	ResponseEntity<?> deleteServiceCenter(long cutsomerId);

	List<ServiceCenter> getCenterByLocation(String location);
	
	List<ServiceCenter> getAllApprovedServiceCenter();

	List<ServiceCenter> getAllPendingServiceCenter();

	List<ServiceCenter> getAllServiceCenter();
	
	ServiceCenter getServiceCenterById(long id);
	
	
	
	
	

}
