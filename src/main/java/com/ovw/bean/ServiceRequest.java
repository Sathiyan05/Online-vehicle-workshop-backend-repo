package com.ovw.bean;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table
public class ServiceRequest {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String vehicleType;
	private String faultInfo;
	private String status;
	private String progress;
	
	@ManyToOne
	@JoinColumn(name="customerId")
	private Customer customer;
	
	@ManyToOne
	@JoinColumn(name="serviceCenterId")
	private ServiceCenter serviceCenter;
	
	private String reason;
	
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	public String getFaultInfo() {
		return faultInfo;
	}

	public void setFaultInfo(String faultInfo) {
		this.faultInfo = faultInfo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public ServiceCenter getServiceCenter() {
		return serviceCenter;
	}

	public void setServiceCenter(ServiceCenter serviceCenter) {
		this.serviceCenter = serviceCenter;
	}

	public String getProgress() {
		return progress;
	}

	public void setProgress(String progress) {
		this.progress = progress;
	}
	
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

}
