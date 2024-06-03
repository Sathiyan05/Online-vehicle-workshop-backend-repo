package com.ovw.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ovw.bean.Admin;
import com.ovw.bean.ServiceCenter;
import com.ovw.service.ServiceCenterService;

@RestController
@CrossOrigin("*")
@RequestMapping("serviceCenter")
public class ServiceCenterController {

	@Autowired
	private ServiceCenterService serviceCenterService;

	@PostMapping("register")
    public ResponseEntity<ServiceCenter> createProduct(@RequestParam("name") String name,
                                                      @RequestParam("email") String email,
                                                      @RequestParam("password") String password,
                                                      @RequestParam("confirmPassword") String confirmPassword,
                                                      @RequestParam("phone") String phone,
                                                      @RequestParam("location") String location,
                                                      @RequestParam("gstNumber") String gstNumber,
                                                      @RequestParam("role") String role,
                                                      @RequestParam("status") String status,
                                                      @RequestParam("image") MultipartFile image) throws IOException {
		ServiceCenter admin = new ServiceCenter();
        admin.setName(name);admin.setPassword(password);
        admin.setEmail(email);admin.setConfirmPassword(confirmPassword);
        admin.setPhone(phone);admin.setLocation(location);
        admin.setGstNumber(gstNumber);admin.setStatus(status);
        admin.setRole(role);admin.setImage(image.getBytes());
        
        ServiceCenter savedAdmin = serviceCenterService.createServiceCenter(admin, image);
        return new ResponseEntity<>(savedAdmin, HttpStatus.CREATED);
    }

	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateCustomer(@PathVariable("id") long serviceCenterId,
			@RequestBody ServiceCenter updatedServiceCenter, @RequestParam long adminId) {
		return serviceCenterService.updateServiceCenter(serviceCenterId, updatedServiceCenter, adminId);
	}

	@PutMapping("/updateProfile/{id}")
    public ResponseEntity<?> updateServiceCenterProfile(@PathVariable("id") long serviceCenterId,
                                          @RequestBody ServiceCenter updatedServiceCenter) {
        return serviceCenterService.updateServiceProfile(serviceCenterId, updatedServiceCenter);
    }

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteServiceCenter(@PathVariable("id") long serviceCenterId) {
		return serviceCenterService.deleteServiceCenter(serviceCenterId);
	}

	@GetMapping("/getByLocation/{location}")
	public List<ServiceCenter> getServiceCenterByLoc(@PathVariable("location") String location) {
		return serviceCenterService.getCenterByLocation(location);
	}

	@GetMapping("getApprovedCenter")
	public List<ServiceCenter> getAllApprovedCenter() {
		return serviceCenterService.getAllApprovedServiceCenter();
	}

	@GetMapping("getPendingCenter")
	public List<ServiceCenter> getAllPendingCenter() {
		return serviceCenterService.getAllPendingServiceCenter();
	}

	@GetMapping("getAllCenter")
	public List<ServiceCenter> getAllWaitingListCenter() {
		return serviceCenterService.getAllApprovedServiceCenter();
	}

	@GetMapping("getserviceCenterById/{id}")
	public ServiceCenter getServiceCenterById(@PathVariable("id") long id) {
		return serviceCenterService.getServiceCenterById(id);
	}
	
	@GetMapping("getallCenters")
	public List<ServiceCenter> getAllServiceCenter() {
		return serviceCenterService.getAllServiceCenter();
	}
}
