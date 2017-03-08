/**
 * 
 */
package com.example.util;
import io.jsonwebtoken.SignatureAlgorithm;


/**
 * @author mindfire
 *
 */
public final class Constant {

	/**
	 * 
	 */
	public Constant() {
		// TODO Auto-generated constructor stub
	}
	public static final String API_ROOT_URL = "http://localhost:8080";
	public static final String ACTIVATION_EMAIL_SUBJECT = "Phew Account Activation";
	public static final int ACTIVATION_KEY_LENGTH = 6;
	/**
	 * JWT Constants
	 */
	public static final SignatureAlgorithm ALGORITHM = SignatureAlgorithm.HS512;
	public static final String AUTH_HEADER = "X-Auth-Token";
	
	/**
	 * Enumeration used while contructing a new JWT(Json Web Token). Audience denotes
	 * the type of user holding the JSON token. 
	 */
	public static enum AudienceType {
		AUDIENCE_TYPE_USER,
		AUDIENCE_TYPE_ADMIN
	}
	

	/**
	 * Regular Expression patterns for validation
	 */
	public static final String ISO_PATTERN = "(?<![A-Z])[A-Z]{3}(?![A-Z])";
	public static final String COUNTRY_CODE_PATTERN = "(?<![A-Z])[A-Z]{2}(?![A-Z])";
	public static final String TIME_PATTERN_24 = "^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$";
	public static final String TIME_PATTERN_12 = "(1[012]|[1-9]):[0-5][0-9](\\s)?(?i)(am|pm)";
	public static final String WORDS_PATTERN = "^[a-zA-Z ()]*$";
	public static final String WORDS_WITH_SPACES_PATTERN = "^[a-zA-Z ]*$";
	public static final String WORD_PATTERN = "^[a-zA-Z]*$";
	public static final String ALPHANUMERIC_PATTERN = "^[a-zA-Z0-9]*$";
	public static final String ALPHANUMERIC_WITH_SPACES_PATTERN = "^[a-zA-Z0-9 ]*$";
	public static final String USERNAME_PATTERN = "(?=^.{6,32}$)^[a-zA-Z][a-zA-Z0-9]*[._-]?[a-zA-Z0-9]+$";
	public static final String EMAIL_PATTERN = "[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}";
}
