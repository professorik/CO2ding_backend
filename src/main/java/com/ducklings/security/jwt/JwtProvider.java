package com.ducklings.security.jwt;

import com.ducklings.domain.user.model.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Component
public class JwtProvider {
	private final JwtProperties jwtProperties;
	private Key secretKey;
	private JwtParser jwtParser;

	@Autowired
	public JwtProvider(JwtProperties jwtProperties) {
		this.jwtProperties = jwtProperties;
	}

	private Key key() {
		if (secretKey == null) {
			byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.getSecret());
			secretKey = Keys.hmacShaKeyFor(keyBytes);
		}
		return secretKey;
	}

	private JwtParser jwtParser() {
		if (jwtParser == null) {
			jwtParser = Jwts.parserBuilder().setSigningKey(key()).build();
		}
		return jwtParser;
	}

	public String generateRefreshToken(User user) {
		Date date = Date.from(LocalDateTime.now().plusSeconds(jwtProperties.getSecs_to_expire_refresh()).toInstant(ZoneOffset.UTC));
		return Jwts.builder()
				.setSubject(user.getUsername())
				.setExpiration(date)
				.signWith(key())
				.compact();
	}

	public String generateToken(User user) {
		Date date = Date.from(LocalDateTime.now().plusSeconds(jwtProperties.getSecs_to_expire_access()).toInstant(ZoneOffset.UTC));
		return Jwts.builder()
				.setSubject(user.getUsername())
				.setExpiration(date)
				.signWith(key())
				.compact();
	}

	public String getLoginFromToken(String token) {
		Claims claims = parseToken(token);
		return claims.getSubject();
	}

	private Claims parseToken(String token) {
		try {
			return jwtParser().parseClaimsJws(token).getBody();
		} catch (ExpiredJwtException expEx) {
			throw new com.ducklings.security.jwt.JwtException("Token expired", "jwt-expired");
		} catch (UnsupportedJwtException unsEx) {
			throw new com.ducklings.security.jwt.JwtException("Unsupported jwt", "jwt-unsupported");
		} catch (MalformedJwtException mjEx) {
			throw new com.ducklings.security.jwt.JwtException("Malformed jwt", "jwt-malformed");
		} catch (SignatureException sEx) {
			throw new com.ducklings.security.jwt.JwtException("Invalid signature", "jwt-signature");
		} catch (Exception e) {
			throw new JwtException("Invalid token", "jwt-invalid");
		}
	}
}
