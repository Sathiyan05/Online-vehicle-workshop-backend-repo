package com.ovw.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ovw.bean.Login;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Repository
public class LoginRepo {

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	public void saveLogin(Login login) {
		entityManager.persist(login);
	}

	public Login findByEmail(String email) {
		TypedQuery<Login> query = entityManager.createQuery("SELECT log FROM Login log WHERE log.email = :email",
				Login.class);
		query.setParameter("email", email);
		List<Login> results = query.getResultList();
		return results.isEmpty() ? null : results.get(0);
	}
	
	public Login getUserBySessionId(long id) {
		TypedQuery<Login> query = entityManager.createQuery("FROM Login log WHERE log.sessionId = :id",
				Login.class);
		query.setParameter("id", id);
		List<Login> results = query.getResultList();
		return results.isEmpty() ? null : results.get(0);
	}
	
	

}
