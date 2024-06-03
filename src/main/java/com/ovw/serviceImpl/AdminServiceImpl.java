package com.ovw.serviceImpl;

import com.ovw.bean.Admin;
import com.ovw.bean.Login;
import com.ovw.repository.AdminRepo;
import com.ovw.repository.LoginRepo;
import com.ovw.service.AdminService;

import java.io.IOException;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepo adminRepository;

    @Autowired
    private LoginRepo loginRepository;

    @Override
    public Admin createAdmin(Admin admin, MultipartFile image) throws IOException {
        if (adminRepository.findAdminByEmail(admin.getEmail()) != null || loginRepository.findByEmail(admin.getEmail()) != null) {
            throw new IllegalArgumentException("Admin with this email already exists.");
        }

        if (!admin.getPassword().equals(admin.getConfirmPassword())) {
            throw new IllegalArgumentException("Passwords do not match.");
        }

        admin.setImage(image.getBytes());
        adminRepository.saveAdmin(admin);

        Login login = new Login();
        login.setEmail(admin.getEmail());
        login.setPassword(admin.getPassword());
        login.setRole(admin.getRole());
        login.setSessionId(admin.getId());
        loginRepository.saveLogin(login);

        return admin;
    }


    @Override
    public ResponseEntity<?> deleteAdmin(long adminId) {
        adminRepository.deleteAdmin(adminId);
        return ResponseEntity.ok("Admin deleted successfully");
    }

    @Override
    public ResponseEntity<?> updateAdmin(long adminId, Admin updatedAdmin) {

        Admin existingAdmin = adminRepository.getAdminById(adminId);
        if (existingAdmin == null) {
            return ResponseEntity.badRequest().body("Admin not found");
        }

        existingAdmin.setName(updatedAdmin.getName());
        existingAdmin.setEmail(updatedAdmin.getEmail());
        existingAdmin.setPhone(updatedAdmin.getPhone());
        existingAdmin.setLocation(updatedAdmin.getLocation());

        adminRepository.saveAdmin(existingAdmin);

        Login login = loginRepository.findByEmail(existingAdmin.getEmail());
        if (login != null) {
            login.setEmail(existingAdmin.getEmail());
            loginRepository.saveLogin(login);
        }

        return ResponseEntity.ok("Admin updated successfully");
    }

	@Override
	public Admin getAdminById(long id) {
		return adminRepository.getAdminById(id);
	}
}
