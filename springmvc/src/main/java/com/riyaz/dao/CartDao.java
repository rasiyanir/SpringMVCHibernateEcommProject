package com.riyaz.dao;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.springframework.stereotype.Component;

import com.riyaz.bean.CartBean;
import com.riyaz.bean.ProductBean;

@Component
public class CartDao {

	
public String addToCart(String username, int itemID, int quantity, int price, String itemName) {
	
		
		int itemTotal = quantity*price;
		int stockCount = 0;
		int cartCount = 0;
		
		EntityManager em = null;
		
		try {
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("project");
			em = emf.createEntityManager();
			em.getTransaction().begin();
			
			Query query = em.createQuery("select c from CartBean c where c.username =?1 and c.itemID =?2");
			query.setParameter(1, username);
			query.setParameter(2, itemID);
			List rs = query.getResultList();
			int size = rs.size();
			if(size!=0) {
				CartBean c = (CartBean) rs.get(0);
				cartCount = c.getItemQuantity();
					Query query2 = em.createQuery("select p from ProductBean p where p.itemID =?1");
					query2.setParameter(1, itemID);
					List rs1 = query2.getResultList();
					if(!(rs1.isEmpty())) {
						ProductBean p = (ProductBean) rs1.get(0);
						stockCount = p.getItemCount();
						if(quantity <= (stockCount - cartCount)) {
							int qty = c.getItemQuantity();
							int tot = c.getItemTotal();
							int totalQuantity = qty + quantity; 
							int finalTotal = tot + itemTotal;
							Query query3 = em.createQuery("update CartBean c set c.itemQuantity =?1, c.itemTotal =?2 where c.username =?3 and c.itemID =?4");
							query3.setParameter(1, totalQuantity);
							query3.setParameter(2, finalTotal);
							query3.setParameter(3, username);
							query3.setParameter(4, itemID);
							query3.executeUpdate();
							em.getTransaction().commit();
							return "add";
						}
						else {
							return "excessQuantity";
						}
						
					}
					
			}
			else {
				em.persist(new CartBean(username, itemID, itemName, price, quantity, itemTotal, "no"));
				em.getTransaction().commit();
				return "add";
			}
			
		}catch(Exception e) {
			System.out.println(e);
		}
		finally {
			em.close();
		}
		
		return "exception";
		
		
	
	}


public String deleteFromCart(String username, int itemID, int quantity, int price) {
	
	
	int itemTotal = quantity*price;
	EntityManager em = null;
	
	try {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("project");
		em = emf.createEntityManager();
		em.getTransaction().begin();
		
		Query query = em.createQuery("select c from CartBean c where c.username =?1 and c.itemID =?2");
		query.setParameter(1, username);
		query.setParameter(2, itemID);
		List rs = query.getResultList();
		int size = rs.size();
		if(size!=0) {
			CartBean c = (CartBean) rs.get(0);
			int qty = c.getItemQuantity();
			int tot = c.getItemTotal();
			int totalQuantity = qty - quantity; 
			int finalTotal = tot - itemTotal;
			if(totalQuantity == 0) {
				Query query4 = em.createQuery("delete from CartBean c where c.username =?1 and c.itemID=?2");
				query4.setParameter(1, username);
				query4.setParameter(2, itemID);
				query4.executeUpdate();
			}
			else {
				Query query3 = em.createQuery("update CartBean c set c.itemQuantity =?1, c.itemTotal =?2 where c.username =?3 and c.itemID =?4");
				query3.setParameter(1, totalQuantity);
				query3.setParameter(2, finalTotal);
				query3.setParameter(3, username);
				query3.setParameter(4, itemID);
				query3.executeUpdate();
			}
			
			em.getTransaction().commit();
			return "delete";
		}
		else {
			return "no record";
		}
		
	}catch(Exception e) {
		System.out.println(e);
	}
	finally {
		em.close();
	}
	
	return "exception";
	
	
	
	
	
	
	
	
	
	
	
		
}


public String checkMessage(String username) {
	
	
		EntityManager em = null;
		EntityManager em2 = null;
		String changeMessage = null;
		
		try {
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("project");
			em = emf.createEntityManager();
			em.getTransaction().begin();
			
			Query query = em.createQuery("select c from CartBean c where c.updateMessage = 'zero' and c.username =?1");
			query.setParameter(1, username);
			List rs = query.getResultList();
			int size = rs.size();
			if(size!=0) {
				changeMessage = " Some items have been updated according to availability!!!!!";
				Query query2 = em.createQuery("delete from CartBean c where c.updateMessage = 'zero' and c.username =?1");
				query2.setParameter(1, username);
				query2.executeUpdate();
				em.getTransaction().commit();
			}
			
			em2 = emf.createEntityManager();
			em2.getTransaction().begin();
			Query query3 = em2.createQuery("select c from CartBean c where c.updateMessage = 'yes' and c.username =?1");
			query3.setParameter(1, username);
			List rs2 = query3.getResultList();
			int size2 = rs2.size();
			if(size2!=0) {
				changeMessage = " Some items have been updated according to availability!!!!!";
				Query query4 = em2.createQuery("update CartBean c set c.updateMessage = 'no' where c.updateMessage = 'yes' and c.username =?1");
				query4.setParameter(1, username);
				query4.executeUpdate();
				em2.getTransaction().commit();
			}
			
		}catch(Exception e) {
			System.out.println(e);
		}
		finally {
			em.close();
			em2.close();
		}
	
	
	return changeMessage;
	
	
	
		
	
}

public List<CartBean> getCart(String username) {
	
	
	EntityManager em = null;
	List<CartBean> cartList = null;
	
	try {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("project");
		em = emf.createEntityManager();
		em.getTransaction().begin();
		Query query = em.createQuery("select c from CartBean c where c.username =?1");
		query.setParameter(1, username);
		cartList = query.getResultList();
		
	}catch(Exception e) {
		System.out.println(e);
	}
	finally {
		em.close();
	}
	
	return cartList;
	
	
	
	
		
}

public int getTotal(String username) {
	
	Integer total = 0;	
	EntityManager em = null;
	
	try {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("project");
		em = emf.createEntityManager();
		em.getTransaction().begin();
		Query query = em.createQuery("select SUM(c.itemTotal) from CartBean c where c.username =?1");
		query.setParameter(1, username);
		total = ((Long) query.getSingleResult()).intValue();
		System.out.println(total);
	}catch(Exception e) {
		System.out.println(e);
	}
	finally {
		em.close();
	}
	
	return total;
	
	
	
	
		
}

}
	

