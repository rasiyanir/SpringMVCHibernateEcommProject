package com.riyaz.controller;

import java.io.IOException;

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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.mysql.cj.Session;
import com.riyaz.config.RiyazConfig;
import com.riyaz.service.LoginService;

/**
 * Servlet implementation class Login
 */
@Controller
@SessionAttributes("username")
public class LoginController{
	
	@PostMapping("Login")
	public ModelAndView login(@RequestParam("user") String username, @RequestParam("psw") String password, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ApplicationContext context = new AnnotationConfigApplicationContext(RiyazConfig.class);
		LoginService service = context.getBean(LoginService.class);
		ModelAndView mv = new ModelAndView();
		
		if(service.verifyUser(username, password)) {
			
			
			mv.addObject("username", username);
			mv.setViewName("redirect:/Products");
			
		
		}
		else {
			
			mv.addObject("username", username);
			mv.setViewName("invalidlogin");
			
		}
		return mv;
	}
	
	
	
}
