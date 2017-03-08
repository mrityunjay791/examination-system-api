/**
 * 
 */
package com.example.DTO;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.example.util.Constant;
import com.example.util.ExceptionMessages;


public class UserAuthenticationRequestDTO {

	@NotBlank(message = ExceptionMessages.INVALID_EMAIL)
	@Pattern(regexp = Constant.EMAIL_PATTERN, message = ExceptionMessages.INVALID_EMAIL)
	@Size(min = 8, max = 254, message = ExceptionMessages.INVALID_EMAIL)
    private String email;
	
	@NotBlank(message = ExceptionMessages.INVALID_PASSWORD)
	@Size(min = 5, max = 60, message = ExceptionMessages.INVALID_PASSWORD)
    private String password;
	
	public UserAuthenticationRequestDTO(){
		
	}

    /**
     * Constructor for the DTO
     * 
     * @param username The username as a string
     * @param password The raw password as a string
     */
    public UserAuthenticationRequestDTO(String email, String password) {
        this.setEmail(email);
        this.setPassword(password);
    }
 
    public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
