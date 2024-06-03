package com.ovw.service;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.ovw.bean.Admin;

public interface AdminService {

	Admin createAdmin(Admin admin, MultipartFile image) throws IOException;
	
	ResponseEntity<?> updateAdmin(long adminId, Admin updatedAdmin);
	
	ResponseEntity<?> deleteAdmin(long adminId);
	
	Admin getAdminById(long id);
	
	
}
