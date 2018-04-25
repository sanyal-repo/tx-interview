package com.learnvest.nmlv.service;


import org.springframework.stereotype.Service;

import com.learnvest.nmlv.model.User;
import com.learnvest.nmlv.model.UserDao;

@Service
public class UserService {
	
	private final UserDao userDao;

	public UserService(UserDao userDao) {
		this.userDao = userDao;
	}

	public User getUser(String userid) {
		return userDao.findByuserid(userid);
	}
}
