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

import com.ovw.bean.ServiceRequest;
import com.ovw.service.ServiceRequestService;

@RestController
@CrossOrigin("*")
@RequestMapping("serviceRequest")
public class ServiceRequestController {
	
	@Autowired
	private ServiceRequestService srService;

    @PostMapping("/register")
	public ResponseEntity<?> registerService(@RequestBody ServiceRequest Service, @RequestParam long cId, @RequestParam long scId) {
    	return srService.createService(Service, cId, scId);
	}
    
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateService(@PathVariable("id") long serviceId,
                                          @RequestBody ServiceRequest updatedService,
                                          @RequestParam long scId) {
        return srService.updateService(serviceId, updatedService, scId);
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteService(@PathVariable("id") long serviceId) {
        return srService.deleteService(serviceId);
    }
    
    @GetMapping("getRequestbyId/{id}")
    public ServiceRequest getRequestById(@PathVariable("id") long srId) {
    	return srService.getRequestbyId(srId);
    }
    
    @GetMapping("history/{id}")
    public List<ServiceRequest> getHistory(@PathVariable("id") long id){
    	return srService.getHistory(id);
    }
    
    @GetMapping("allServiceCenterHistory/{serviceCenterId}")
    public List<ServiceRequest> getOverAllHistory(@PathVariable("serviceCenterId") long serviceCenterId){
    	return srService.getOverallHistory(serviceCenterId);
    }
    
    @GetMapping("getpendingrequets/{servicecenterid}")
    public List<ServiceRequest> getPendingRequets(@PathVariable("servicecenterid") long serviceCenterId){
    	return srService.getPendingRequests(serviceCenterId);
    }
    
    @GetMapping("getapprovedrequets/{servicecenterid}")
    public List<ServiceRequest> getApprovedRequets(@PathVariable("servicecenterid") long serviceCenterId){
    	return srService.getApprovedRequets(serviceCenterId);
    }
       

}
