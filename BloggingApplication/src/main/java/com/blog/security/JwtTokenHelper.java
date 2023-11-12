	package com.blog.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenHelper {

	private static final long JWT_TOKEN_VALIDITY = 50*60*60;
	
	private String secret = "jwtTokenKey";
	
	//retrive username from token
	public String getUserNameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}
	
	//retrive expiry date from token of a token
	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);		
	}
	
	//For retriving any information from token we need the secret key
	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}
	
	//Check if token has expired
	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}
	
	//Generate Token for users
	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		return doGenerateToken(claims, userDetails.getUsername());
	}
	
	//While creating the token
	//Define claims of the token , like isuser, expiration,subject and the ID
	//Sign the JWT using the HS512 algorithm and secret key
	//According to JWS compact Serialization(https://tools.ietf.org/html/draft-ietf-joss-jwt)
	//compaction of the JWT to a URL-safe string
	private String doGenerateToken(Map<String, Object> claims, String subject) {
		
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(subject)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+JWT_TOKEN_VALIDITY*100))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}
	
	//Validate Token
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = this.getUserNameFromToken(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
	
	
}
