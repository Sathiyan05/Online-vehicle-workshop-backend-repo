package com.ovw.junit;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ovw.bean.*;

import com.ovw.controller.ServiceCenterController;

@SpringBootTest
class ServiceCenterTest {
	

	@Autowired
	ServiceCenterController scController;
	
	
	@Test
	void getServiceByLocation() {
		List<ServiceCenter> result=scController.getServiceCenterByLoc("Chennai");
		assertNotNull(result);
	}
	
	
	@Test
	void getServiceByApproved() {
		List<ServiceCenter> result=scController.getAllApprovedCenter();
		assertNotNull(result);
	}
	
	
	@Test
	void getServiceByPending() {
		List<ServiceCenter> result=scController.getAllPendingCenter();
		assertNotNull(result);
	}
	
	
	@Test
	void getServiceByRejected() {
		List<ServiceCenter> result=scController.getAllWaitingListCenter();
		assertNotNull(result);
	}
	
	
	@Test
	void getServiceById() {
		ServiceCenter result=scController.getServiceCenterById(4);
		assertNotNull(result);
	}

}
