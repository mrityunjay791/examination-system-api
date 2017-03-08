package com.example.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.model.UserInfo;
import com.example.rest.security.SpringSecurityUser;

@Service
public class SpringSecurityUserService implements UserDetailsService {
	
	@Autowired
	private UserService userService;

	@Override
    public UserDetails loadUserByUsername(String email) throws AuthenticationException {
    	
		UserInfo userInfo = this.userService.getUserByUsername(email);
        
        if (userInfo == null) {
        	throw new UsernameNotFoundException(String.format("No appUser found with email '%s'.", email));
        } else {
            return new SpringSecurityUser(
            		userInfo.getId(),
            		userInfo.getUserName(),
            		userInfo.getPassword(),
            		userInfo.getEmail(),
            		userInfo.getEnabled());
        }
    }
}
