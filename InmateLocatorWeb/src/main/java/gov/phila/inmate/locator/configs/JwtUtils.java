package gov.phila.inmate.locator.configs;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

@Component
public class JwtUtils {

	public DecodedJWT getDecodedJwt(String token) {
		Algorithm algorithm = Algorithm.HMAC256("secret");
	    JWTVerifier verifier = JWT.require(algorithm)
	        .build();
	    return verifier.verify(token);
	}
	
	public Collection<GrantedAuthority> getAuthorities(DecodedJWT jwt){
		return Arrays.asList(jwt.getClaims().get("role").asString().split(","))
				.stream().map(authority -> new SimpleGrantedAuthority(authority)).collect(Collectors.toList());
	}
	
	public String getName(DecodedJWT jwt) {
		return jwt.getClaims().get("name").asString();
	}
	
	public String getLocation(DecodedJWT jwt) {
		return jwt.getClaims().get("location").asString();
	}
}