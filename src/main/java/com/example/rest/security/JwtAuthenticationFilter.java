/**
 * 
 */
package com.example.rest.security;

import static com.example.util.Constant.AUTH_HEADER;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import com.example.util.Constant;
import com.example.util.JwtToken;

/**
 * @author mindfire
 *
 */
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	@Autowired
	private JwtToken jwtToken;

	@Autowired
	private UserDetailsService userDetailsService;
	
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException, AuthenticationException{

		HttpServletResponse resp = (HttpServletResponse) response;
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE, PATCH");
		resp.setHeader("Access-Control-Max-Age", "3600");
		resp.setHeader("Access-Control-Allow-Headers",
				"Origin, X-Requested-With, Content-Type, Accept, " + AUTH_HEADER);

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		
		// Get the header value from the request
		String authToken = httpRequest.getHeader(Constant.AUTH_HEADER);
		
		// Get the username from the token if exists
		String username = this.jwtToken.getUsernameFromToken(authToken);
		
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

			if (this.jwtToken.validateToken(authToken, userDetails)) {
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}

		chain.doFilter(request, response);
	}

}
