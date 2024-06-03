package com.ovw.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.ovw.bean.ServiceRequest;

public interface ServiceRequestService {
	
	ResponseEntity<?> createService(ServiceRequest service, long cId, long scId);
	
	ResponseEntity<?> updateService(long serviceId, ServiceRequest updatedService, long scId);
	
	ResponseEntity<?> deleteService(long serviceId);
	
	ServiceRequest getRequestbyId(long srId);
	
	List<ServiceRequest> getHistory(long customerId);

	List<ServiceRequest> getOverallHistory (long id);
	
	List<ServiceRequest> getPendingRequests (long id);
	
	List<ServiceRequest> getApprovedRequets (long id);
	
	

}
