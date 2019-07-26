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
import com.riyaz.bean.CheckoutBean;
import com.riyaz.bean.ProductBean;
@Component
public class CheckoutDao {
	
	
	public List<CheckoutBean> getCheckout(String username) {
		
		
		
		EntityManager em = null;
		EntityManager em2 = null;
		List<CheckoutBean> checkoutList = null;
		
		try {
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("project");
			em = emf.createEntityManager();
			em.getTransaction().begin();
			Query query = em.createQuery("select c from CartBean c where c.username =?1");
			query.setParameter(1, username);
			List<CartBean> rs = query.getResultList();
			for(int i = 0; i < rs.size();i++) {
				CartBean c = rs.get(i);
				em.persist(new CheckoutBean(username, c.getItemID(), c.getItemName(), c.getItemPrice(), c.getItemQuantity(), c.getItemTotal()));
			}
			em.getTransaction().commit();
			em2 = emf.createEntityManager();
			em2.getTransaction().begin();
			Query query2 = em2.createQuery("select ch from CheckoutBean ch where ch.username =?1");
			query2.setParameter(1, username);
			checkoutList = query2.getResultList();
		}catch(Exception e) {
			System.out.println(e);
		}
		finally {
			em.close();
		}
		
		return checkoutList;
			
		
		
		
		
		
		
		
	}
	
	
public void deleteCartUpdateItem(String username) {
		
		
		
	EntityManager em = null;
	EntityManager em2 = null;
	EntityManager em3 = null;
			
			try {
				EntityManagerFactory emf = Persistence.createEntityManagerFactory("project");
				em = emf.createEntityManager();
				em.getTransaction().begin();
				
				Query query = em.createQuery("select c.itemID, c.itemQuantity from CartBean c where c.username =?1");
				query.setParameter(1, username);
				List rs = query.getResultList();
				for(int i = 0; i < rs.size();i++) {
					Object[] c = (Object[]) rs.get(i);
					int cartItemID = (Integer) c[0];
					int cartItemQuantity = (Integer) c[1];
						Query query2 = em.createQuery("select p from ProductBean p where p.itemID =?1");
						query2.setParameter(1, cartItemID);
						List rs1 = query2.getResultList();
						if(!(rs1.isEmpty())) {
							ProductBean p = (ProductBean) rs1.get(0);
							int stockQuantity = p.getItemCount();
							int actualQuantity = stockQuantity - cartItemQuantity;
							Query query3 = em.createQuery("update ProductBean p set p.itemCount =?1 where itemID =?2");
							query3.setParameter(1, actualQuantity);
							query3.setParameter(2, cartItemID);
							query3.executeUpdate();
							
							
						}
						
				}
				em.getTransaction().commit();
				
				
				em2 = emf.createEntityManager();
				em2.getTransaction().begin();
				Query query4 = em2.createQuery("delete from CartBean c where c.username =?1");
				query4.setParameter(1, username);
				query4.executeUpdate();
				em2.getTransaction().commit();
				
				em3 = emf.createEntityManager();
				em3.getTransaction().begin();
				Query query5 = em3.createQuery("select c from CartBean c");
				List rs2 = query5.getResultList();
				int size = rs2.size();
				if(size != 0) {
					for(int i = 0; i < size; i++) {
						CartBean c = (CartBean) rs2.get(i);
						String fieldCartUsername = c.getUsername();
						int fieldCartItemID = c.getItemID();
						int fieldCartCount = c.getItemQuantity();
						int fieldCartItemPrice = c.getItemPrice();
						Query query6 = em3.createQuery("select p from ProductBean p where p.itemID =?1");
						query6.setParameter(1, fieldCartItemID);
						List rs3 = query6.getResultList();
						if(!rs3.isEmpty()) {
							ProductBean p = (ProductBean) rs3.get(0);
							int stockCount = p.getItemCount();
							if(stockCount == 0) {
								Query query7 = em3.createQuery("update CartBean c set c.itemQuantity = '0', c.itemTotal = '0', c.updateMessage = 'zero' where c.itemID =?1 and c.username =?2");
								query7.setParameter(1, fieldCartItemID);
								query7.setParameter(2, fieldCartUsername);
								query7.executeUpdate();
								
							}
							else if(fieldCartCount > stockCount) {
								Query query8 = em3.createQuery("update CartBean c set c.itemQuantity =?1, c.itemTotal =?2, c.updateMessage = 'yes' where c.itemID =?3 and c.username =?4");
								query8.setParameter(1, stockCount);
								query8.setParameter(2, stockCount*fieldCartItemPrice);
								query8.setParameter(3, fieldCartItemID);
								query8.setParameter(4, fieldCartUsername);
								query8.executeUpdate();
							
							}
						}
					}
					em3.getTransaction().commit();
				}
				
				
			}catch(Exception e) {
				System.out.println(e);
			}
			finally {
				em.close();
				em2.close();
				em3.close();
			}
		
		
	}
}
