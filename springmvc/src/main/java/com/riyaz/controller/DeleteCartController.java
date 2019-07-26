package com.riyaz.controller;

import java.io.IOException;
import java.io.PrintWriter;

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

import com.riyaz.config.RiyazConfig;
import com.riyaz.service.CartService;

/**
 * Servlet implementation class DeleteCart
 */
@Controller
@SessionAttributes({"cartItemName","cartItemQuantity","cartItemPrice", "option"})
public class DeleteCartController {

	@PostMapping("deleteCart")
	public ModelAndView DeleteCart(@RequestParam("id") int itemID,
						   @RequestParam("quantity") int quantity,
						   @RequestParam("price") int price,
						   @RequestParam("pName") String itemName, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		HttpSession session = request.getSession();
		ModelAndView mv = new ModelAndView();
		
		String username = (String) session.getAttribute("username");
		
		
		mv.addObject("cartItemName", itemName);
		mv.addObject("cartItemQuantity", quantity);
		mv.addObject("cartItemPrice", price);
		
		
		
		ApplicationContext context = new AnnotationConfigApplicationContext(RiyazConfig.class);
		CartService service = context.getBean(CartService.class);
		String deleteres = service.deleteProduct(username, itemID, quantity, price);
		if (deleteres == "delete") {
			mv.addObject("option", deleteres);
			
		} else {
			mv.addObject("option", deleteres);
			
		}
		  RequestDispatcher rd = request.getRequestDispatcher("viewCart");
		  rd.forward(request, response);
		 
		return mv;
	}

	
}
