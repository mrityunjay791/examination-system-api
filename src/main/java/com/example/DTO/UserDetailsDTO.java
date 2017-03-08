/**
 * 
 */
package com.example.DTO;

/**
 * @author mindfire
 *
 */
public class UserDetailsDTO {
	
	private String userName;
	private String password;
	
	public UserDetailsDTO(){
		
	}
	
	public UserDetailsDTO(String sUserName, String password) {
		super();
		this.userName = sUserName;
		this.password = password;
	}
	/**
	 * @return the sUserName
	 */
	public String getsUserName() {
		return userName;
	}
	/**
	 * @param sUserName the sUserName to set
	 */
	public void setsUserName(String sUserName) {
		this.userName = sUserName;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
}
