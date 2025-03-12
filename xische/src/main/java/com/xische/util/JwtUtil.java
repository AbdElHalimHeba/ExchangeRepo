package com.xische.util;

import java.util.Date;

import com.xische.constant.SecurityConstant;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

public class JwtUtil {

	public static String generateJWT(String subject) {
		
		 return Jwts.builder()
	                .claims(Jwts.claims().subject(subject).build())
	                .issuedAt(new Date())
	                .expiration(new Date(System.currentTimeMillis() + SecurityConstant.JWT_EXPIRATION))
	                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(SecurityConstant.JWT_SECRET)))
	                .compact();
	}
	
	public static String parseJwtSubject(String jwt) {
		return Jwts.parser()
		.verifyWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(SecurityConstant.JWT_SECRET)))
		.build()
        .parseSignedClaims(jwt) 
        .getPayload()
        .getSubject();
	}

}
