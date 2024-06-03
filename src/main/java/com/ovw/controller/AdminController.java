package com.ovw.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ovw.bean.Admin;
import com.ovw.service.AdminService;


@RestController
@CrossOrigin("*")
@RequestMapping("admin")
public class AdminController {
	
	@Autowired
	private AdminService adminService;
    
    @PostMapping("register")
    public ResponseEntity<Admin> createProduct(@RequestParam("name") String name,
                                                      @RequestParam("email") String email,
                                                      @RequestParam("password") String password,
                                                      @RequestParam("confirmPassword") String confirmPassword,
                                                      @RequestParam("phone") String phone,
                                                      @RequestParam("location") String location,
                                                      @RequestParam("role") String role,
                                                      @RequestParam("image") MultipartFile image) throws IOException {
        Admin admin = new Admin();
        admin.setName(name);admin.setPassword(password);
        admin.setEmail(email);admin.setConfirmPassword(confirmPassword);
        admin.setPhone(phone);admin.setLocation(location);
        admin.setRole(role);admin.setImage(image.getBytes());
        
        Admin savedAdmin = adminService.createAdmin(admin, image);
        return new ResponseEntity<>(savedAdmin, HttpStatus.CREATED);
    }
    
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateAdmin(@PathVariable("id") long adminId,
                                          @RequestBody Admin updatedAdmin) {
        return adminService.updateAdmin(adminId, updatedAdmin);
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAdmin(@PathVariable("id") long adminId) {
        return adminService.deleteAdmin(adminId);
    }
    
    @GetMapping("/getAdminById/{id}")
    public Admin getAdminById(@PathVariable("id") long adminId) {
    	return adminService.getAdminById(adminId);
    }
    
}