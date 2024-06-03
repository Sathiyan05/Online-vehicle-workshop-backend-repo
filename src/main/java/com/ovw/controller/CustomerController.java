package com.ovw.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.ovw.bean.Customer;
import com.ovw.service.CustomerService;


@RestController
@CrossOrigin("*")
@RequestMapping("customer")
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	@PostMapping("register")
    public ResponseEntity<Customer> createProduct(@RequestParam("name") String name,
                                                      @RequestParam("email") String email,
                                                      @RequestParam("password") String password,
                                                      @RequestParam("confirmPassword") String confirmPassword,
                                                      @RequestParam("phone") String phone,
                                                      @RequestParam("location") String location,
                                                      @RequestParam("role") String role,
                                                      @RequestParam("age") int age,
                                                      @RequestParam("gender") String gender,
                                                      @RequestParam("image") MultipartFile image) throws IOException {
        Customer admin = new Customer();
        admin.setName(name);admin.setPassword(password);
        admin.setEmail(email);admin.setConfirmPassword(confirmPassword);
        admin.setPhone(phone);admin.setLocation(location);
        admin.setAge(age);admin.setGender(gender);
        admin.setRole(role);admin.setImage(image.getBytes());
        
        Customer savedAdmin = customerService.createCustomer(admin, image);
        return new ResponseEntity<>(savedAdmin, HttpStatus.CREATED);
    }
	
	@PutMapping("/update/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable("id") long customerId,
                                          @RequestBody Customer updatedCustomer) {
        return customerService.updateCustomer(customerId, updatedCustomer);
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable("id") long customerId) {
        return customerService.deleteCustomer(customerId);
    }
    
    @GetMapping("getCustomer/{id}")
    public Customer getCustomerById(@PathVariable("id") long customerId){
    	return customerService.getCustomerById(customerId);
    }
    
    @GetMapping("getAllCustomers")
    public List<Customer> getAllCustomers(){
    	return customerService.getAllCustomers();
    }
}
