package com.ovw.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.ovw.bean.Bill;

public interface BillService {
	
	ResponseEntity<?> createBill(Bill bill, long scId, long cId, long srId);
	
	ResponseEntity<?> updateBill(long ticketId, Bill updatedTicket);
	
	ResponseEntity<?> deleteBill(long ticketId);
	
	List<Bill> getHistory(long customerId);
	
	List<Bill> getBIllByServiceCenter(long customerId);

	List<Bill> getAllBills();
}
