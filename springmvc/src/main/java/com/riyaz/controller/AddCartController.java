package com.riyaz.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.riyaz.bean.CartBean;
import com.riyaz.config.RiyazConfig;
import com.riyaz.service.CartService;
import com.riyaz.service.LoginService;

/**
 * Servlet implementation class Cart
 */
@Controller
@SessionAttributes({"option","cartItemName","cartItemQuantity","cartItemPrice"})
public class AddCartController {
	
	@PostMapping("addCart")
	public ModelAndView AddCart(@RequestParam("id") int itemID,
						@RequestParam("quantity") int quantity,
						@RequestParam("price") int price,
						@RequestParam("pName") String itemName,HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		ModelAndView mv = new ModelAndView();
		
		String username = (String) session.getAttribute("username");
		
		
		mv.addObject("cartItemName", itemName);
		mv.addObject("cartItemQuantity", quantity);
		mv.addObject("cartItemPrice", price);
		
		
		ApplicationContext context = new AnnotationConfigApplicationContext(RiyazConfig.class);
		CartService service = context.getBean(CartService.class);
		
		String addRes = service.addProduct(username, itemID, quantity, price, itemName);
		
		if(addRes == "add") {
			mv.addObject("option", addRes);
		}
		else {
			mv.addObject("option", addRes);
		}
			RequestDispatcher rd = request.getRequestDispatcher("viewCart");
			rd.forward(request, response);
			return mv;
		
		  
		 
	}
	
	

	

}
