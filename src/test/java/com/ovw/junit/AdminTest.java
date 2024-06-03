package com.ovw.junit;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ovw.bean.Admin;
import com.ovw.controller.AdminController;


@SpringBootTest
class AdminTest {
	
	
	@Autowired
	AdminController admController;

	@Test
	void getAdminByController() {	
		
		Admin admin=admController.getAdminById(1);	
		System.out.println(admin);
		assertNotNull(admin);
		
	}
	
	

}
