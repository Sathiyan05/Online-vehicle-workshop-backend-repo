package com.ovw.junit;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ovw.bean.ServiceRequest;
import com.ovw.controller.ServiceRequestController;

@SpringBootTest
class ServiceReuqestTest {
	
	
	@Autowired
	ServiceRequestController sr;

	@Test
	void getServiceRequestById() {
		ServiceRequest sr1=sr.getRequestById(5);
		assertNotNull(sr1);
	}
	
	@Test
	void getServiceHistoryById() {
		List<ServiceRequest> sr2=sr.getHistory(1);
		assertNotNull(sr2);
	}
	
	@Test
	void getServicePending() {	
		List<ServiceRequest> sr3=sr.getPendingRequets(1);
		assertNotNull(sr3);
	}
	
	@Test
	void getServiceApproved() {
		List<ServiceRequest> sr3=sr.getApprovedRequets(1);
		assertNotNull(sr3);
	}
	
	@Test
	void getServiceAllHistory() {
		List<ServiceRequest> sr4=sr.getOverAllHistory(1);
		assertNotNull(sr4);
	}
	

}
