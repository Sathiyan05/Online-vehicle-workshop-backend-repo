package com.ovw.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ovw.bean.Bill;
import com.ovw.bean.Customer;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Repository
public class CustomerRepo {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Transactional
	public void saveCustomer(Customer customer) {
		entityManager.persist(customer);
	}
	
	public Customer findCustomerByEmail(String email) {
		try {
			TypedQuery<Customer> query = entityManager.createQuery("SELECT cust FROM Customer cust WHERE cust.email = :email",
					Customer.class);
			query.setParameter("email", email);
			return query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
	
	public Customer getCustomerById(long id) {
		return entityManager.find(Customer.class, id);
	}

	public List<Customer> getAllCustomers() {
		TypedQuery<Customer> query = entityManager.createQuery("SELECT a FROM Customer a", Customer.class);
		return query.getResultList();
	}

	@Transactional
	public void deleteCustomer(long id) {
		Customer admin = getCustomerById(id);
		if (admin != null) {
			entityManager.remove(admin);
		}
	}

	public Customer getcountCustomer() {
		TypedQuery<Customer> query=entityManager.createQuery("Select count(id) From Bill", Customer.class);
		return query.getSingleResult();
	}
}
