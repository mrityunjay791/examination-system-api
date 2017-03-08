package com.example.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.UserInfo;
import com.example.repository.UserRepository;
import com.example.services.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	/* (non-Javadoc)
	 * @see com.mindfire.phew.web.service.UserService#getUserByUsername(java.lang.String)
	 */
	@Override
	public UserInfo getUserByUsername(String username){
		if(username.trim().length() == 0){
			return null;
		}
		
		return userRepository.findByEmail(username);
	}
	
}
