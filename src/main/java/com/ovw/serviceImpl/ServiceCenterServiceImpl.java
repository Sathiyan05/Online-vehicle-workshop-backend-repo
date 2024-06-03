package com.ovw.serviceImpl;

import java.io.IOException;
import java.util.List;

import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.ovw.bean.Admin;
import com.ovw.bean.Login;
import com.ovw.bean.ServiceCenter;
import com.ovw.repository.LoginRepo;
import com.ovw.repository.ServiceCenterRepo;
import com.ovw.service.ServiceCenterService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
@Component
public class ServiceCenterServiceImpl implements ServiceCenterService {

	@Autowired
	private ServiceCenterRepo scRepo;

	@Autowired
	private JavaMailSender mailsender;

	@Autowired
	private LoginRepo loginRepository;

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public ServiceCenter createServiceCenter(ServiceCenter serviceCenter, MultipartFile image) throws IOException {
	    if (scRepo.findServiceCenterByEmail(serviceCenter.getEmail()) != null
	            || loginRepository.findByEmail(serviceCenter.getEmail()) != null) {
	        throw new IllegalArgumentException("Service center with this email already exists.");
	    }

	    if (!serviceCenter.getPassword().equals(serviceCenter.getConfirmPassword())) {
	        throw new IllegalArgumentException("Passwords do not match.");
	    }

	    serviceCenter.setImage(image.getBytes());
	    scRepo.saveServiceCenter(serviceCenter);

	    Login login = new Login();
	    login.setEmail(serviceCenter.getEmail());
	    login.setPassword(serviceCenter.getPassword());
	    login.setRole(serviceCenter.getRole());
	    login.setSessionId(serviceCenter.getId());
	    loginRepository.saveLogin(login);

	    SimpleMailMessage message = new SimpleMailMessage();
	    message.setFrom("sathyaracer05@gmail.com");
	    message.setTo(serviceCenter.getEmail());
	    message.setText("Hii" + serviceCenter.getName() + "\nGreeting of the day...!!!\n"
	            + "Thank you for registering with us. We're excited to have you as part of our community."
	            + "At Online Vehicle Workshop, we strive to provide you with the best experience and resources available."
	            + "your Application for registration is successfully registered"
	            + "\n\nYou'll get notified once our Team approves/rejects your application." + "\n\n\n"
	            + "Best Regards\nSathiyan.S" + "\nTrainee Software Engineer"
	            + "\nRelevantz Technology services Pvt. Ltd.,");
	    message.setSubject("Registration Alert...!!!");
	    mailsender.send(message);

	    return serviceCenter;
	}


	@Override
	public ResponseEntity<?> deleteServiceCenter(long serviceCenterId) {
		scRepo.deleteServiceCenter(serviceCenterId);
		return ResponseEntity.ok("Profile deleted successfully");
	}

	@Override
	public ResponseEntity<?> updateServiceCenter(long serviceCenterId, ServiceCenter updatedserviceCenter,
			long adminId) {
		
		ServiceCenter existingServiceCenter = scRepo.getServiceCenterById(serviceCenterId);
		
		if (existingServiceCenter == null) {
			return ResponseEntity.badRequest().body("Center Details not found");
		}
		// Update all fields except password
		existingServiceCenter.setName(updatedserviceCenter.getName());
//		existingServiceCenter.setEmail(updatedserviceCenter.getEmail());
		existingServiceCenter.setPhone(updatedserviceCenter.getPhone());
		existingServiceCenter.setLocation(updatedserviceCenter.getLocation());
		existingServiceCenter.setStatus(updatedserviceCenter.getStatus());
		existingServiceCenter.setReason(updatedserviceCenter.getReason());

		Admin admin = entityManager.find(Admin.class, adminId);
		existingServiceCenter.setAdmin(admin);

		// Save the updates
		scRepo.saveServiceCenter(existingServiceCenter);

		// Update corresponding entry in login table
		Login login = loginRepository.getUserBySessionId(existingServiceCenter.getId());
		if (login != null) {
//			login.setEmail(existingServiceCenter.getEmail());
			loginRepository.saveLogin(login);
		}

		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("sathyaracer05@gmail.com");
		message.setTo(updatedserviceCenter.getEmail());
		
		message.setText("Hii" + updatedserviceCenter.getName() + "\nGreeting of the day...!!!\n"
				+ "Your details are successfully Updated you can login with your updated credentials..." + "\n email: "
				+ updatedserviceCenter.getEmail() + "\n\n\n" + "Best Regards\n Auto repair");
		message.setSubject("Details Updated Successfully...!!!");
		mailsender.send(message);

		return ResponseEntity.ok("Details updated successfully");
	}

	@Override
	public List<ServiceCenter> getCenterByLocation(String location) {
		List<ServiceCenter> sc = scRepo.findServiceByLoc(location);
		return sc;
	}

	@Override
	public List<ServiceCenter> getAllApprovedServiceCenter() {
		return scRepo.getAllApprovedServiceCenters();
	}

	@Override
	public List<ServiceCenter> getAllPendingServiceCenter() {
		return scRepo.getAllPendingServiceCenters();
	}

	@Override
	public ServiceCenter getServiceCenterById(long id) {
		return scRepo.getServiceCenterById(id);
	}

	@Override
	public ResponseEntity<?> updateServiceProfile(long customerId, ServiceCenter updatedCustomer) {

		ServiceCenter existingServiceCenter = scRepo.getServiceCenterById(customerId);
		
		if (existingServiceCenter == null) {
			return ResponseEntity.badRequest().body("Center Details not found");
		}
		
		existingServiceCenter.setName(updatedCustomer.getName());
//		existingServiceCenter.setEmail(updatedCustomer.getEmail());
		existingServiceCenter.setPhone(updatedCustomer.getPhone());
		existingServiceCenter.setLocation(updatedCustomer.getLocation());
		existingServiceCenter.setStatus(updatedCustomer.getStatus());
		existingServiceCenter.setImage(updatedCustomer.getImage());
		
		// Save the updates
		scRepo.saveServiceCenter(existingServiceCenter);

		// Update corresponding entry in login table
		Login login = loginRepository.getUserBySessionId(existingServiceCenter.getId());
		if (login != null) {
			login.setEmail(existingServiceCenter.getEmail());
			loginRepository.saveLogin(login);
		}
		return ResponseEntity.ok("Details updated successfully");
	}


	@Override
	public List<ServiceCenter> getAllServiceCenter() {
		
		return scRepo.getAllServiceCenters();
	}
}
