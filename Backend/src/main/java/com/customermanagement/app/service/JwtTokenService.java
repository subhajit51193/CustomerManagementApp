package com.customermanagement.app.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

/*
 * This class contains all the methods related to JwtToken
 */
@Service
public class JwtTokenService {

	@Autowired
	private JwtEncoder encoder;
	
	@Autowired
	private JwtDecoder decoder;
	
	
	/*
	 * Generates AccessToken
	 * 
	 * @param email -> email of user
	 * 
	 * @return String -> accessToken
	 */
	public String generateToken(String email) {
		
		Instant now = Instant.now();
		
		JwtClaimsSet claims = JwtClaimsSet.builder()
				.issuer("self")
				.issuedAt(now)
				.expiresAt(now.plus(1,ChronoUnit.HOURS))
				.subject(email)
				.build();
		return this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
	}
	
	/*
	 * Generates RefreshToken
	 * 
	 * @param email -> email of user
	 * 
	 * @return String -> refreshTOken
	 */
	public String generateRefreshToken(String email) {
		
		Instant now = Instant.now();
		
		JwtClaimsSet claims = JwtClaimsSet.builder()
				.issuer("self")
				.issuedAt(now)
				.expiresAt(now.plus(7,ChronoUnit.DAYS))
				.subject(email)
				.build();
		return this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
	}
	
	/*
	 * Extract email from token
	 * 
	 * @param String -> JWT token
	 * 
	 * @return String -> email of user
	 */
	public String extractEmailFromToken(String jwtToken) {
	    Jwt jwt = decoder.decode(jwtToken);
	    String email = jwt.getSubject();
	    return email;
	}
}
