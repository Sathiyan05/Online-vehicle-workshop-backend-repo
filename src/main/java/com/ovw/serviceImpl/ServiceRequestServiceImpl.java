package com.ovw.serviceImpl;

import java.util.List;

import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.ovw.bean.Customer;
import com.ovw.bean.ServiceCenter;
import com.ovw.bean.ServiceRequest;
import com.ovw.repository.ServiceRequestRepo;
import com.ovw.service.ServiceRequestService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Service
@Component
public class ServiceRequestServiceImpl implements ServiceRequestService{
	
    @Autowired
    private ServiceRequestRepo ServiceRepo;
    
    @PersistenceContext
	private EntityManager entityManager;
    
    @Autowired
	private JavaMailSender mailsender;
    
    @Override
    public ResponseEntity<?> createService(ServiceRequest Service, long cId, long scId) {
    	    
    		Customer cust= entityManager.find(Customer.class, cId);
    		Service.setCustomer(cust);
    		
    		ServiceCenter sc= entityManager.find(ServiceCenter.class, scId);
    		Service.setServiceCenter(sc);
    	
    		ServiceRepo.saveService(Service);
    		
    		SimpleMailMessage message = new SimpleMailMessage();
    		message.setFrom("no.reply.autorepair@gmail.com");
    		message.setTo(cust.getEmail());
    		message.setText("Hii" + cust.getName() + "\nGreeting of the day...!!!\n"
    				+ "You are raised a Request for a Service with " +sc.getName() +" @ " +sc.getLocation()
    				+ "\n\nYou'll get Notified once your request has been approved/rejected..."
    				+ "Thank You for Your Understand and Time\n Have a great Day...\n\n\n"
    				+ "Best Regards\n Auto repair");
    		message.setSubject("Service Request Raised...!!!");
    		mailsender.send(message);
    		
    		
    	    return ResponseEntity.ok("User registered successfully");
    } 
    
    @Override
    public ResponseEntity<?> deleteService(long ServiceId) {
    	ServiceRepo.deleteServiceRequest(ServiceId);
        return ResponseEntity.ok("Deleted successfully");
    }
    
    @Override
    public ResponseEntity<?> updateService(long ServiceId, ServiceRequest updatedService, long scId) {
    	ServiceRequest existingRequest = ServiceRepo.getServiceById(ServiceId);
        if (existingRequest == null) {
            return ResponseEntity.badRequest().body("Request not found");
        }
        // Update
        existingRequest.setStatus(updatedService.getStatus());
        existingRequest.setReason(updatedService.getReason());
        existingRequest.setProgress(updatedService.getProgress());
        
        ServiceCenter sc=entityManager.find(ServiceCenter.class, scId);
        existingRequest.setServiceCenter(sc);
        
        Query query= entityManager.createQuery("FROM ServiceRequest s WHERE s.id = :id").setParameter("id", ServiceId);
        
        ServiceRequest serviceRequest=(ServiceRequest) query.getSingleResult();
        String email =  serviceRequest.getCustomer().getEmail();
        
       ServiceRepo.saveService(existingRequest); 
        
       SimpleMailMessage message=new SimpleMailMessage();
       message.setFrom("no.reply.autorepair@gmail.com");
       message.setTo(email);
       message.setSubject("Request Status Update...!!!");
       message.setText("You're Service status is updated");
       
       mailsender.send(message);
        
        return ResponseEntity.ok("Details updated successfully");
    }

	@Override
	public ServiceRequest getRequestbyId(long srId) {
		return ServiceRepo.getServiceById(srId);
	}

	@Override
	public List<ServiceRequest> getHistory(long customerId) {
		return ServiceRepo.getHistory(customerId);
	}

	@Override
	public List<ServiceRequest> getOverallHistory(long id) {
		return ServiceRepo.getAllServiceCenterHistory(id);
	}

	@Override
	public List<ServiceRequest> getPendingRequests(long id) {
		return ServiceRepo.getPendingRequests(id);
	}

	@Override
	public List<ServiceRequest> getApprovedRequets(long id) {
		return ServiceRepo.getApprovedRequests(id);
	}
}
