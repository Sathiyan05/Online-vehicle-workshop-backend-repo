package com.ovw.repository;

import com.ovw.bean.Admin;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AdminRepo {

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	public void saveAdmin(Admin admin) {
		entityManager.persist(admin);
	}

	public Admin findAdminByEmail(String email) {
		try {
			TypedQuery<Admin> query = entityManager.createQuery("SELECT adm FROM Admin adm WHERE adm.email = :email",
					Admin.class);
			query.setParameter("email", email);
			return query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	public Admin getAdminById(long id) {
		return entityManager.find(Admin.class, id);
	}

	public List<Admin> getAllAdmins() {
		TypedQuery<Admin> query = entityManager.createQuery("SELECT a FROM Admin a", Admin.class);
		return query.getResultList();
	}

	@Transactional
	public void deleteAdmin(long id) {
		Admin admin = getAdminById(id);
		if (admin != null) {
			entityManager.remove(admin);
		}
	}

}
