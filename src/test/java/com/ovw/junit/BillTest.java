package com.ovw.junit;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import com.ovw.bean.Bill;
import com.ovw.controller.BillController;

@SpringBootTest
class BillTest {
	
	@Autowired
	BillController billController;

	@Test
	void createBill() {
		
		Bill bill=new Bill();
		bill.setBillEstimation("34567");
		bill.setDate("12-12-2024");
		bill.setDescription("Tinkering works");
		bill.setStatus("PAID");
		
		ResponseEntity<?> result=billController.registerUser(bill, 4, 1, 7);
		assertEquals(ResponseEntity.ok("Ticket created successfully"), result);
		
	}
	
	
	@Test
	void billHistory() {
		List<Bill> bill=billController.getHistory(1);
		assertNotNull(bill);
	}
	
	
	@Test
	void ListAllBill() {
		List<Bill> bill=billController.getAllBills();
		assertNotNull(bill);
				
	}

}
