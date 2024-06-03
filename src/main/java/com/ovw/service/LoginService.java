package com.ovw.service;

import org.springframework.http.ResponseEntity;

public interface LoginService {
	
	ResponseEntity<?> login(String email, String password);

}
