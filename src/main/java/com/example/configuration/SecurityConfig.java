package com.example.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.example.rest.security.JwtAuthenticationFilter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private AuthenticationEntryPoint authenticationEntryPoint;

	/**
	 * Build a Spring authentication manager and configure it to use the Application's 
	 * UserDetailsService to handle username - password authentication. For encryption 
	 * configure the builder to use BCrypt Password Encoding technique provided by 
	 * Spring via {@link BCryptPasswordEncoder}.
	 * 
	 * 
	 * @param authenticationManagerBuilder Spring's authentication manager injected
	 * @throws Exception
	 */
	@Autowired
	public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}

	/**
	 * Create an new authentication manager bean that will take care of the authentication 
	 * needs. The authentication manager is already configured using the {@link AuthenticationManagerBuilder}
	 */
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	/**
	 * Create a bean of the custom filter {@link JwtAuthenticationFilter} that will registered 
	 * by the servlet container to intercept the requests.
	 * 
	 * @return {@link JwtAuthenticationFilter} The filter object
	 * @throws Exception
	 */
	@Bean
	public JwtAuthenticationFilter authenticationTokenFilterBean() throws Exception {
		JwtAuthenticationFilter authenticationTokenFilter = new JwtAuthenticationFilter();
		authenticationTokenFilter.setAuthenticationManager(super.authenticationManagerBean());
		return authenticationTokenFilter;
	}
	
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception{
		httpSecurity
		.cors().disable()
		.authorizeRequests()
		.antMatchers(HttpMethod.OPTIONS,"/**").permitAll()
		.antMatchers(HttpMethod.POST, "/users").permitAll()
		.antMatchers("/**").permitAll().and()
		.csrf().disable();
	}
}
