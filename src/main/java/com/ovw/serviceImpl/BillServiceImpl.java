package com.ovw.serviceImpl;

import java.util.List;

import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.ovw.bean.Bill;
import com.ovw.bean.Customer;
import com.ovw.bean.ServiceCenter;
import com.ovw.bean.ServiceRequest;
import com.ovw.repository.BillRepo;
import com.ovw.repository.ServiceCenterRepo;
import com.ovw.repository.ServiceRequestRepo;
import com.ovw.service.BillService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
@Component
public class BillServiceImpl implements BillService {
	
	@Autowired
	private BillRepo ticketRepo;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private JavaMailSender emailSender;

	@Override
	public ResponseEntity<?> createBill(Bill bill, long scId, long cId, long srId) {
		
		ServiceCenter sc = entityManager.find(ServiceCenter.class, scId);
		bill.setServiceCenter(sc);
		
		Customer cust= entityManager.find(Customer.class, cId);
		bill.setCustomer(cust);
		
		ServiceRequest sr=entityManager.find(ServiceRequest.class, srId);
		bill.setServiceRequest(sr);
		
		ticketRepo.saveTicket(bill);
		
		
		SimpleMailMessage message=new SimpleMailMessage();
		message.setFrom("no.reply.autorepaird@gmail.com");
		message.setTo(cust.getEmail());
		message.setSubject("Bill from Auto Repair...!");
		message.setText("Dear "+cust.getName()+"\n\n"
				+"Your Bill has been generated for your Service"+sr.getVehicleType()
				+"with the amount "+bill.getBillEstimation()
				+"\n\n\t You can view your bill by Login to our application in Future"
				+"\n\nThank you for your time and consideration..."
				+ "\n\n Best Regards."
				+ "\n Auto Repair"
				);
		
		emailSender.send(message);
		
		
	    return ResponseEntity.ok("Ticket created successfully");
	}

	@Override
	public ResponseEntity<?> updateBill(long ticketId, Bill updatedTicket) {
		Bill existingTicket = ticketRepo.getTicketById(ticketId);
        if (existingTicket == null) {
            return ResponseEntity.badRequest().body("Ticket not found");
        }
        
        // Update
        existingTicket.setStatus(updatedTicket.getStatus());
        existingTicket.setServiceCenter(updatedTicket.getServiceCenter());

        // Save the update
        ticketRepo.saveTicket(existingTicket);
        return ResponseEntity.ok("Details updated successfully");
	}

	@Override
	public ResponseEntity<?> deleteBill(long ticketId) {
		ticketRepo.deleteTicket(ticketId);
        return ResponseEntity.ok("Deleted successfully");
	}
	
	@Override
	public List<Bill> getHistory(long customerId) {
		return ticketRepo.getHistory(customerId);
	}

	@Override
	public List<Bill> getAllBills() {
		return ticketRepo.getAllTickets();
	}

	@Override
	public List<Bill> getBIllByServiceCenter(long scId) {
		return ticketRepo.getBillbyServiceCenterId(scId);
	}
	

}
