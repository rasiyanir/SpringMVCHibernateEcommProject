package com.riyaz.dao;

import java.sql.SQLException;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.springframework.stereotype.Component;


@Component
public class UserDao {
	
	
	public boolean checkInDB(String user, String pass) {
		
		
		EntityManager em = null;
		
		try {
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("project");
			em = emf.createEntityManager();
			em.getTransaction().begin();
			
			Query query = em.createQuery("select u from User u where u.username =?1 and u.password =?2");
			query.setParameter(1, user);
			query.setParameter(2, pass);
			List rs = query.getResultList();
			int size = rs.size();
			if(size!=0) {
				return true;
			}
			
		}catch(Exception e) {
			System.out.println(e);
		}
		finally {
			em.close();
		}
		
		
		return false;
	}
}
