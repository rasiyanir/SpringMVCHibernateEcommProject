package com.riyaz.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.riyaz.bean.CartBean;
import com.riyaz.bean.CheckoutBean;
import com.riyaz.config.RiyazConfig;
import com.riyaz.service.CartService;
import com.riyaz.service.CheckoutService;

/**
 * Servlet implementation class Checkout
 */
@Controller
@SessionAttributes("checkoutList")
public class CheckoutController{
	
	
	
	@PostMapping("checkout")
	public ModelAndView Checkout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		ModelAndView mv = new ModelAndView();
		String username = (String) session.getAttribute("username");
	
		ApplicationContext context = new AnnotationConfigApplicationContext(RiyazConfig.class);
		CheckoutService service = context.getBean(CheckoutService.class);
		
		
		List<CheckoutBean> checkoutList =  service.gettingCheckout(username);
		
		service.deletingCartUpdatingItem(username);
		
		if(checkoutList != null) {
			mv.addObject("checkoutList", checkoutList);
			mv.setViewName("purchases");
			
		}
		return mv;
	}
	
	
	
}
