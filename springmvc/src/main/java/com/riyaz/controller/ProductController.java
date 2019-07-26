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

import com.riyaz.bean.ProductBean;
import com.riyaz.config.RiyazConfig;
import com.riyaz.service.CartService;
import com.riyaz.service.ProductService;

/**
 * Servlet implementation class Products
 */
@Controller
@SessionAttributes("productList")
public class ProductController{
	
	@GetMapping("Products")
	public ModelAndView Product() throws IOException {
		ModelAndView mv = new ModelAndView();
		ApplicationContext context = new AnnotationConfigApplicationContext(RiyazConfig.class);
		ProductService service = context.getBean(ProductService.class);
		List<ProductBean> products = service.gettingProduct();
		
		if(products != null) {
			mv.addObject("productList", products);
			mv.setViewName("product");
			
		}
		return mv;
	}
	
	
	

}
