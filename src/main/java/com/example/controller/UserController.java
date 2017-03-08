/**
 * 
 */
package com.example.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.DTO.UserAuthenticationRequestDTO;
import com.example.rest.exception.DtoValidationException;
import com.example.rest.response.DTO.JwtTokenResponseDTO;
import com.example.services.SpringSecurityUserService;
import com.example.util.EntityMapper;
import com.example.util.JwtToken;
import com.example.util.Messages;

/**
 * @author mindfire
 *
 */
@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class UserController {
	
//	@Autowired
//	private UserService userService;
	
	/**
	 * {@link JwtToken} bean autowired
	 */
	@Autowired
	private JwtToken jwtToken;

	/*
	 * Spring's {@link AuthenticationManager} bean autowired
	 */
	@Autowired
	private AuthenticationManager authenticationManager;

	/**
	 * Spring's {@link UserDetailsService} implementation {@link SpringSecurityUserService} injected over here
	 */
	@Autowired
	private UserDetailsService userDetailsService;

	/**
	 * {@link EntityMapper} bean autowired
	 */
	@Autowired
	private EntityMapper entityMapper;

//	@GetMapping("/users")
//	public List<UserDetails> getAllUser(){
//		return userService.getUsers();
//		return null;
//	}
	
	
	@PostMapping("/users")
	public ResponseEntity<JwtTokenResponseDTO> authenticationRequest(@RequestBody @Valid UserAuthenticationRequestDTO authenticationRequestDTO, BindingResult bindingResult) {
		
		// Check if the dto validations encountered any errors
		if (bindingResult.hasErrors()) {
			throw new DtoValidationException(Messages.AUTHENTICATION_FAILURE.getMessageText(),bindingResult);
		}

		// Authenticate the user using Spring's Authentication Manager
		Authentication authentication = this.authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authenticationRequestDTO.getEmail(), authenticationRequestDTO.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(authenticationRequestDTO.getEmail());
		// Generate the token by passing in the UserDetails object
		String token = this.jwtToken.generateToken(userDetails);
		// Return the token
		return ResponseEntity.ok(new JwtTokenResponseDTO(token));
	}
}
