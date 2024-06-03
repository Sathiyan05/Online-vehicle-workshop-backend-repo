package com.ovw.serviceImpl;

import java.io.IOException;
import java.util.List;

import org.jvnet.hk2.annotations.Service;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.ovw.bean.Admin;
import com.ovw.bean.Customer;
import com.ovw.bean.Login;
import com.ovw.repository.CustomerRepo;
import com.ovw.repository.LoginRepo;
import com.ovw.service.CustomerService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
@Component
public class CustomerServiceImpl implements CustomerService {
	
	@Autowired
    private CustomerRepo customerRepository;
    
    @Autowired
    private LoginRepo loginRepository;
    
    @PersistenceContext
	private EntityManager entityManager;
    
	@Autowired
	private JavaMailSender mailsender;
    
    
	@Override
	public Customer createCustomer(Customer customer, MultipartFile image) throws IOException {
	    if (customerRepository.findCustomerByEmail(customer.getEmail()) != null || loginRepository.findByEmail(customer.getEmail()) != null) {
	        throw new IllegalArgumentException("Customer with this email already exists.");
	    }

	    if (!customer.getPassword().equals(customer.getConfirmPassword())) {
	        throw new IllegalArgumentException("Passwords do not match.");
	    }

	    customer.setImage(image.getBytes());
	    customerRepository.saveCustomer(customer);

	    Login login = new Login();
	    login.setEmail(customer.getEmail());
	    login.setPassword(customer.getPassword());
	    login.setRole(customer.getRole());
	    login.setSessionId(customer.getId());

	    SimpleMailMessage message = new SimpleMailMessage();
	    message.setFrom("no.reply.autorepair@gmail.com");
	    message.setTo(customer.getEmail());
	    message.setText("Hii" + customer.getName() + "\nGreeting of the day...!!!\n"
	            + "Thank you for registering with us. \n\nWe're excited to have you as part of our community."
	            + "at Auto repair, we strive to provide you with the best experience and resources available."
	            + "\n\nYour Application is successfully registered\n\n"
	            + "Now You can login with your credentials..."
	            + "\n\n"
	            + "Best Regards\n\n Auto Repair ");
	    message.setSubject("Welcome to Auto repair...!!!");
	    mailsender.send(message);

	    loginRepository.saveLogin(login);

	    return customer;
	}


    @Override
    public ResponseEntity<?> deleteCustomer(long customerId) {
        customerRepository.deleteCustomer(customerId);
        return ResponseEntity.ok("Customer Profile deleted successfully");
    }

    @Override
    public ResponseEntity<?> updateCustomer(long customerId, Customer updatedCustomer) {
        Customer existingCustomer = customerRepository.getCustomerById(customerId);
        if (existingCustomer == null) {
            return ResponseEntity.badRequest().body("Customer Details not found");
        }
        // Update all fields except password
        existingCustomer.setName(updatedCustomer.getName());
        existingCustomer.setEmail(updatedCustomer.getEmail());
        existingCustomer.setPhone(updatedCustomer.getPhone());
        existingCustomer.setLocation(updatedCustomer.getLocation());

        // Save the updated Customer
        customerRepository.saveCustomer(existingCustomer);

        // Update corresponding entry in login table
        Login login = loginRepository.getUserBySessionId(existingCustomer.getId());
        if (login != null) {
            login.setEmail(existingCustomer.getEmail());
            loginRepository.saveLogin(login);
        }
        
        
        SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("no.reply.autorepair@gmail.com");
		message.setTo(updatedCustomer.getEmail());
		message.setText("Hii" + updatedCustomer.getName() + "\nGreeting of the day...!!!\n"
				+ "Your details are successfully Updated you can login with your updated credentials..." + "\n email: "
				+ updatedCustomer.getEmail() + "\n\n\n" + "Best Regards\n Auto repair");
		message.setSubject("Profile Updated...!!!");
		mailsender.send(message);
        
        return ResponseEntity.ok("Details updated successfully");
        
    }

	@Override
	public Customer getCustomerById(long customerId) {		
		return customerRepository.getCustomerById(customerId);
	}

	@Override
	public List<Customer> getAllCustomers() {
		return customerRepository.getAllCustomers();
	}
    
}
