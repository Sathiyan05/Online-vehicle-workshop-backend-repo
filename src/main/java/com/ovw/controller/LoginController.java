package com.ovw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ovw.bean.Login;
import com.ovw.service.LoginService;

@RestController
@CrossOrigin("*")
public class LoginController {
	
	@Autowired
	private LoginService loginService;
	
	
	@PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Login loginRequest) {
        return loginService.login(loginRequest.getEmail(), loginRequest.getPassword());
    }

}
