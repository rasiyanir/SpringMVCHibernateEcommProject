package com.riyaz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.riyaz.dao.UserDao;
@Component
public class LoginService {
	
	@Autowired
	private UserDao userDao;
	
	public LoginService() {
		//userDao = new UserDao();
	}
	
	public boolean verifyUser(String user, String pass) {
		return userDao.checkInDB(user, pass);
	}
	
}
