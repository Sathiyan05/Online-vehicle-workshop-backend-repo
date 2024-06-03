package com.ovw.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ovw.bean.ServiceRequest;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import java.util.Collections;

@Repository
public class ServiceRequestRepo {

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	public void saveService(ServiceRequest serviceRequest) {
		entityManager.persist(serviceRequest);
	}

	public ServiceRequest getServiceById(long id) {
		return entityManager.find(ServiceRequest.class, id);
	}

	public List<ServiceRequest> getAllServices() {
		TypedQuery<ServiceRequest> query = entityManager.createQuery("FROM ServiceRequest", ServiceRequest.class);
		return query.getResultList();
	}

	@Transactional
	public void deleteServiceRequest(long id) {
		ServiceRequest ar = getServiceById(id);
		if (ar != null) {
			entityManager.remove(ar);
		}
	}

	public List<ServiceRequest> getHistory(long customerId) {
		try {
			TypedQuery<ServiceRequest> query = entityManager
					.createQuery("FROM ServiceRequest WHERE customer.id = :customerId", ServiceRequest.class);
			query.setParameter("customerId", customerId);
			return query.getResultList();
		} catch (Exception e) {
			return null;
		}

	}

	public List<ServiceRequest> getAllServiceCenterHistory(long id) {
		TypedQuery<ServiceRequest> query = entityManager.createQuery(
				"FROM ServiceRequest WHERE serviceCenter.id= :id AND (status=\"RESOLVED\" OR status=\"REJECTED\")", ServiceRequest.class);
		query.setParameter("id", id);
		return query.getResultList();
	}

	public List<ServiceRequest> getPendingRequests(long id) {
		TypedQuery<ServiceRequest> query = entityManager.createQuery(
				"FROM ServiceRequest WHERE serviceCenter.id= :id AND (status=\"PENDING\")", ServiceRequest.class);
		query.setParameter("id", id);
		return query.getResultList();
	}

	public List<ServiceRequest> getApprovedRequests(long id) {
		TypedQuery<ServiceRequest> query = entityManager.createQuery(
				"FROM ServiceRequest WHERE serviceCenter.id= :id AND (status=\"APPROVED\" OR status=\"WORKING\")", ServiceRequest.class);
		query.setParameter("id", id);
		return query.getResultList();
	}
	

}
