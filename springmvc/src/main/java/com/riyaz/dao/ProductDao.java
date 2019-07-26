package com.riyaz.dao;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.springframework.stereotype.Component;

import com.riyaz.bean.ProductBean;

@Component
public class ProductDao {

public List<ProductBean> getProduct() {
		
	EntityManager em = null;
	List<ProductBean> productList = null;
	
	try {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("project");
		em = emf.createEntityManager();
		em.getTransaction().begin();
		Query query = em.createQuery("select p from ProductBean p where p.itemCount > 0");
		productList = query.getResultList();
		
	}catch(Exception e) {
		System.out.println(e);
	}
	finally {
		em.close();
	}
	
	return productList;
		
	}
	
}
