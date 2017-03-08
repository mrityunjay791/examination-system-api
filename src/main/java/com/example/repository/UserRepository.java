/**
 * 
 */
package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.UserInfo;

/**
 * @author mindfire
 *
 */
@Repository
public interface UserRepository extends JpaRepository<UserInfo, Long> {

//	@Autowired
//	public LocalEntityManagerFactoryBean localEntityManagerFactoryBean;
	
	public List<UserInfo> findAll();
	
	public UserInfo findByUserName(String userName);
	
	public UserInfo findByEmail(String email);
}
