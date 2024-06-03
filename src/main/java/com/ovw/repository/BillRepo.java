package com.ovw.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ovw.bean.Bill;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Repository
public class BillRepo {

	
	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	public void saveTicket(Bill bill) {
		entityManager.persist(bill);
	}

	public Bill getTicketById(long id) {
		return entityManager.find(Bill.class, id);
	}

	public List<Bill> getAllTickets() {
		TypedQuery<Bill> query = entityManager.createQuery("FROM Bill", Bill.class);
		return query.getResultList();
	}
	

	@Transactional
	public void deleteTicket(long id) {
		Bill ticket = getTicketById(id);
		if (ticket != null) {
			entityManager.remove(ticket);
		}
	}
	
	public List<Bill> getHistory(long customerId){
		try {
			TypedQuery<Bill> query = entityManager.createQuery("FROM Bill WHERE customer.id = :customerId", Bill.class);
			query.setParameter("customerId", customerId);
			return query.getResultList();
		} catch (Exception e) {
			return null;
		}
		
	}
	
	public List<Bill> getBillbyServiceCenterId(long scId){
		try {
			TypedQuery<Bill> query = entityManager.createQuery("FROM Bill WHERE serviceCenter.id = :scId", Bill.class);
			query.setParameter("scId", scId);
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();;
		}
		return null;
		
	}
	

	
}
