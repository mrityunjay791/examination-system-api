/**
 * 
 */
package com.example.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.util.Constant;
import com.example.rest.security.SpringSecurityUser;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

/**
 * @author mindfire
 *
 */
public class JwtToken {


	/**
	 * Inject the value of <code>jwt.secret</code> from <code>application.properties</code> file
	 */
	@Value("${jwt.secret}")
    private String jwtSecret;
	
	/**
	 * Inject the value of <code>jwt.expiration</code> from <code>application.properties</code> file
	 */
	@Value("${jwt.expiration}")
	private Long tokenExpiration;


    /**
     * Overloaded method. Generates a new token from Spring's {@link UserDetails} object.
     * 
     * @param userDetails Takes a {@code UserDetails} Object
     * @return The JSON Web Token
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", userDetails.getUsername());
        claims.put("audience", Constant.AudienceType.AUDIENCE_TYPE_USER.name());
        claims.put("created", this.generateCurrentDate());
        claims.put("access", userDetails.getAuthorities());
        return this.generateToken(claims);
    }
    
    /**
     * Generate a new token with all the specified claims in it.
     * 
     * @param claims Takes a {@code Map<String, Object>}
     * @return The JSON Web Token
     */
    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(this.generateExpirationDate())
                .signWith(Constant.ALGORITHM, this.jwtSecret)
                .compact();
    }
    
    /**
     * Parses the token using a <em><b>Secret Key</b></em>
     * 
     * @param token The token as String
     * @return {@link Claims} JWT Claims
     */
    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(this.jwtSecret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }
    
    /**
     * Retrieve the Subject (i.e. username) from the token after parsing it
     * 
     * @param token
     * @return String
     */
    public String getUsernameFromToken(String token) {
        String username;
        try {
            final Claims claims = this.getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }
    
    /**
     * Retrieve the type of token user from the token
     * 
     * @param token
     * @return
     */
    public String getAudienceFromToken(String token) {
        String audience;
        try {
            final Claims claims = this.getClaimsFromToken(token);
            audience = (String) claims.get("audience");
        } catch (Exception e) {
            audience = null;
        }
        return audience;
    }

    
    /**
     * Retrieve the creation date of the token 
     * 
     * @param token
     * @return Date
     */
    public Date getCreatedDateFromToken(String token) {
        Date created;
        try {
            final Claims claims = this.getClaimsFromToken(token);
            created = new Date((Long) claims.get("created"));
        } catch (Exception e) {
            created = null;
        }
        return created;
    }

    /**
     * Retrieve the expiration date of the token 
     * 
     * @param token The JWT Token
     * @return Date The expiration date as Java {@link Date} 
     */
    public Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            final Claims claims = this.getClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }

    /**
     * Returns the current timestamp
     * 
     * @return {@link Date} The current timestamp
     */
    private Date generateCurrentDate() {
        return new Date(System.currentTimeMillis());
    }

    /**
     * Generate the expiration date of the token, calculated from the current timestamp
     * 
     * @return <b>Date</b> Expiration date of the token
     */
    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + this.tokenExpiration * 1000);
    }

    /**
     * Check if the token is expired. The expiration is set during the creation of the token 
     * 
     * @param token The token as String
     * @return <b>true</b> if the token is expired, else it returns <b>false</b>
     */
    private Boolean isTokenExpired(String token) {
        final Date expiration = this.getExpirationDateFromToken(token);
        return expiration.before(this.generateCurrentDate());
    }

    /**
     * Check to see if the token was created before the last password reset by the user.
     * 
     * @param created The token creation date
     * @param lastPasswordReset
     * @return <b>true</b> if the token isn't created before last password reset, else <b>false</b>
     */
    private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return lastPasswordReset != null && created.before(lastPasswordReset);
    }    

    /**
     * Check to see if the token can be refreshed
     * 
     * @param token
     * @param lastPasswordReset The last password reset date
     * @return <b>true</b> if the token hasn't expired and isn't created before last password reset
     */
    public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
        final Date created = this.getCreatedDateFromToken(token);
        return !(this.isCreatedBeforeLastPasswordReset(created, lastPasswordReset)) && (!(this.isTokenExpired(token)));
    }

    /**
     * Refesh a token on expire
     * 
     * @param token The token to refresh
     * @return The newly created token with the current timestamp
     */
    public String refreshToken(String token) {
        String refreshedToken;
        try {
            final Claims claims = this.getClaimsFromToken(token);
            claims.put("created", this.generateCurrentDate());
            refreshedToken = this.generateToken(claims);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    /**
     * Validate a JWT token to check its authenticity
     * 
     * @param token The JWT Token
     * @param userDetails The UserDetails object that bears this token
     * @return true if valid
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        SpringSecurityUser user = (SpringSecurityUser) userDetails;
        final String username = this.getUsernameFromToken(token);
        final Date created = this.getCreatedDateFromToken(token);
        return username.equals(user.getUsername())
                && !(this.isTokenExpired(token))
                && !(this.isCreatedBeforeLastPasswordReset(created, user.getLastPasswordReset()));
    }
    
}
