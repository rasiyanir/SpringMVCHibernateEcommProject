package com.riyaz.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.riyaz.dao.CartDao;
import com.riyaz.dao.CheckoutDao;
import com.riyaz.dao.ProductDao;
import com.riyaz.dao.UserDao;
import com.riyaz.service.CartService;
import com.riyaz.service.CheckoutService;
import com.riyaz.service.LoginService;
import com.riyaz.service.ProductService;

@org.springframework.context.annotation.Configuration
@ComponentScan("com.riyaz.controller")
public class RiyazConfig {
	
	@Bean
	public InternalResourceViewResolver viewResolver() {
		InternalResourceViewResolver vr = new InternalResourceViewResolver();
		vr.setPrefix("/");
		vr.setSuffix(".jsp");
		return vr;
	}
	
	@Bean
	public LoginService getLoginService() {
		return new LoginService();
	}
	
	@Bean
	public ProductService getProductService() {
		return new ProductService();
	}
	
	@Bean
	public CartService getCartService() {
		return new CartService();
	}
	
	@Bean
	public CheckoutService getCheckoutService() {
		return new CheckoutService();
	}
	@Bean
	public CartDao getCartDao() {
		return new CartDao();
	}
	@Bean
	public CheckoutDao getCheckoutDao() {
		return new CheckoutDao();
	}
	@Bean
	public ProductDao getProductDao() {
		return new ProductDao();
	}
	@Bean
	public UserDao getUserDao() {
		return new UserDao();
	}
}
