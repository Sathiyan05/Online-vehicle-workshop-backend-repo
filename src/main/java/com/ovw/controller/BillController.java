package com.ovw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.ovw.bean.Bill;
import com.ovw.service.BillService;

@RestController
@CrossOrigin("*")
@RequestMapping("bill")
public class BillController {
	
	@Autowired
	private BillService billService;

    @PostMapping("/create")
	public ResponseEntity<?> registerUser(@RequestBody Bill bill, @RequestParam long scId, @RequestParam long cId, @RequestParam long srId) {
    	return billService.createBill(bill, scId, cId, srId);
	}
    
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateAdmin(@PathVariable("id") long billId,
                                          @RequestBody Bill updatedBill) {
        return billService.updateBill(billId, updatedBill);
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAdmin(@PathVariable("id") long billId) {
        return billService.deleteBill(billId);
    }
    
    @GetMapping("history/{id}")
    public List<Bill> getHistory(@PathVariable("id") long id){
    	return billService.getHistory(id);
    }
    
    @GetMapping("serviceCenter/{id}")
    public List<Bill> getBillByServiceCenterId(@PathVariable("id") long id){
    	return billService.getBIllByServiceCenter(id);
    }
    
    @GetMapping("getAllBills")
    public List<Bill> getAllBills(){
    	return billService.getAllBills();
    }
    
    

}
