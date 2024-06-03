package com.ovw.bean;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table
public class Bill {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String date;
	private String billEstimation;
	private String description;
	private String status;
	
	
	@ManyToOne
	@JoinColumn(name="customerId")
	private Customer customer;
	
	@ManyToOne
	@JoinColumn(name="serviceCenterId")
	private ServiceCenter serviceCenter;
	
	@OneToOne
	@JoinColumn(name="requestId")
	private ServiceRequest serviceRequest;

	public long getId() {
		return id;
	}

	public ServiceRequest getServiceRequest() {
		return serviceRequest;
	}

	public void setServiceRequest(ServiceRequest serviceRequest) {
		this.serviceRequest = serviceRequest;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getBillEstimation() {
		return billEstimation;
	}

	public void setBillEstimation(String billEstimation) {
		this.billEstimation = billEstimation;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
