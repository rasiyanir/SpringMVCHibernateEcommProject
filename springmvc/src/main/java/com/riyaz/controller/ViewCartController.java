package com.riyaz.controller;

import java.io.IOException;
import java.io.PrintWriter;
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
import com.riyaz.config.RiyazConfig;
import com.riyaz.service.CartService;

/**
 * Servlet implementation class ViewServlet
 */
@Controller
@SessionAttributes({"msg","cartList","checkoutTotal"})
public class ViewCartController {
	
	@PostMapping("viewCart")
	public ModelAndView viewCart(HttpServletRequest request) throws IOException {
		HttpSession session = request.getSession();
		ModelAndView mv = new ModelAndView();
		
		String username = (String) session.getAttribute("username");
		ApplicationContext context = new AnnotationConfigApplicationContext(RiyazConfig.class);
		CartService service = context.getBean(CartService.class);
			
		String msg = service.checkingMessage(username);
		
		List<CartBean> cartList = service.gettingCart(username);
		
		int checkoutTotal = service.getFinalTotal(username);
		
		String optioncoming = (String) session.getAttribute("option");
		
		mv.addObject("option", optioncoming);
		mv.addObject("msg", msg);
		mv.addObject("cartList", cartList);
		mv.addObject("checkoutTotal", checkoutTotal);
		
		mv.setViewName("cart");
		
		return mv;
		
	}
	
	
	

}
