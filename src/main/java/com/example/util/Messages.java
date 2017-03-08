package com.example.util;

public enum Messages {

	/**
	 * Authentication messages
	 */
	ACCOUNT_NOT_ACTIVE(100,"Account is inactive"),
	BAD_CREDENTIALS(105,"Invalid Credentials"),
	INVALID_USERNAME(101,"Invalid username, it should only contain alphanumeric characters"),
	INVALID_PASSWORD(102,"Invalid Password"),
	AUTHENTICATION_FAILURE(103,"Authentication failed"),
	AUTHENTICATION_SUCCESS(104,"Authentication successful");
	
	
	
	private final int messageCode;
	private final String messageText;
	
	/**
	 * Initialize the enum fields using constructor
	 * 
	 * @param messageCode
	 * @param messageText The message itself
	 */
	Messages(final int messageCode, final String messageText){
		this.messageCode = messageCode;
		this.messageText = messageText;
	}

	/**
	 * @return the messageCode
	 */
	public final int getMessageCode() {
		return messageCode;
	}

	/**
	 * @return the messageText
	 */
	public final String getMessageText() {
		return messageText;
	}

}
