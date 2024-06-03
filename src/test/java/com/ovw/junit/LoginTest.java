package com.ovw.junit;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ovw.bean.Login;
import com.ovw.controller.LoginController;

@SpringBootTest
class LoginTest {
	
	
	@Autowired
	LoginController lc;

	@Test
	void Failure() {
		
		Login login=new Login();
		
		login.setEmail("sathyaracer05@gmil.com");
		login.setPassword("Sathya@12345");
		
		ResponseEntity<?> result=lc.login(login);
		assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
		
	}
	
	
	@Test
	void Success() {
		
		Login login=new Login();
		
		login.setEmail("sathyaracer05@gmail.com");
		login.setPassword("Sathya@5901");
		
		ResponseEntity<?> result=lc.login(login);
		assertEquals(HttpStatus.OK, result.getStatusCode());
		
	}

}
