package com.riyaz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.riyaz.bean.CheckoutBean;
import com.riyaz.dao.CheckoutDao;

@Component
public class CheckoutService {
		
		@Autowired
		private CheckoutDao checkoutDao;
		
		public CheckoutService() {
			//checkoutDao = new CheckoutDao();
		}
		
		public List<CheckoutBean> gettingCheckout(String username) {
			return checkoutDao.getCheckout(username);
		}
		
		public void deletingCartUpdatingItem(String username) {
			checkoutDao.deleteCartUpdateItem(username);
		}
}
