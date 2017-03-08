package com.example.services;

import com.example.model.UserInfo;


public interface UserService {
	/**
	 * 
	 * @param username
	 * @return {@link UserInfo}
	 */
	UserInfo getUserByUsername(String username);
}
