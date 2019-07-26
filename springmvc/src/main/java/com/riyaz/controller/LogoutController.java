package com.riyaz.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Servlet implementation class Logout
 */
@Controller
public class LogoutController{
	
	@GetMapping("Logout")
	public void Logout(HttpServletRequest request, HttpServletResponse response) throws IOException {

		HttpSession session = request.getSession();
		
		session.removeAttribute("username");
		session.invalidate();
		response.sendRedirect("index.jsp");
	}
	
	

}
