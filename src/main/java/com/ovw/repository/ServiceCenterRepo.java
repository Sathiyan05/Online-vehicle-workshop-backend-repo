package com.ovw.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ovw.bean.ServiceCenter;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Repository
public class ServiceCenterRepo {

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	public void saveServiceCenter(ServiceCenter center) {
		entityManager.persist(center);
	}

	public ServiceCenter findServiceCenterByEmail(String email) {
		try {
			TypedQuery<ServiceCenter> query = entityManager
					.createQuery("SELECT sc FROM ServiceCenter sc WHERE sc.email = :email", ServiceCenter.class);
			query.setParameter("email", email);
			return query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	public List<ServiceCenter> findServiceByLoc(String location){
		try {
			TypedQuery<ServiceCenter> query = entityManager.createQuery("Select sc from ServiceCenter sc WHERE sc.location = :location AND sc.status=\"APPROVED\"",ServiceCenter.class);
			query.setParameter("location", location);
			return query.getResultList();
		}catch(Exception e) {
			return null;
		}
	}

	public ServiceCenter getServiceCenterById(long id) {
		return entityManager.find(ServiceCenter.class, id);
	}

	public List<ServiceCenter> getAllServiceCenters() {
		TypedQuery<ServiceCenter> query = entityManager.createQuery("FROM ServiceCenter", ServiceCenter.class);
		return query.getResultList();
	}
	
	public List<ServiceCenter> getAllApprovedServiceCenters() {
		TypedQuery<ServiceCenter> query = entityManager.createQuery("FROM ServiceCenter where status = \"APPROVED\"", ServiceCenter.class);
		return query.getResultList();
	}
	
	public List<ServiceCenter> getAllPendingServiceCenters() {
		TypedQuery<ServiceCenter> query = entityManager.createQuery("FROM ServiceCenter where status = \"PENDING\"", ServiceCenter.class);
		return query.getResultList();
	}

	@Transactional
	public void deleteServiceCenter(long id) {
		ServiceCenter serviceCenter = getServiceCenterById(id);
		if (serviceCenter != null) {
			entityManager.remove(serviceCenter);
		}
	}

}
