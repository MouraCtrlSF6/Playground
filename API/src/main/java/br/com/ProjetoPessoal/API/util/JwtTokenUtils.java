package br.com.ProjetoPessoal.API.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import br.com.ProjetoPessoal.API.models.Role;
import br.com.ProjetoPessoal.API.models.User;
import br.com.ProjetoPessoal.API.repository.UserRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class JwtTokenUtils {

    static final String CLAIM_KEY_CREATED = "created";

    private static String secret = "@Senha123";

    private static Long expiration = 3600L;
    
    @Autowired
    private UserRepository userRepository;
    
    public List<Role> getUserRolesFromToken(String token) {    	
		final String userNameFromToken = this.getUsernameFromToken(token);
		
		final User client = userRepository.findByName(userNameFromToken).get(0);
		
		return client.getRoles();
    }

    public String getUsernameFromToken(String token) {
        String username;
        
        try {
            username =  getClaimsFromToken(token).getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    public Date getCreatedDateFromToken(String token) {
        Date created;
        
        try {
            created = new Date((Long) getClaimsFromToken(token).get(CLAIM_KEY_CREATED));
        } catch (Exception e) {
            created = null;
        }
        return created;
    }

    public Date getExpirationDateFromToken(String token) {
        Date expiration;
        
        try {
            expiration = getClaimsFromToken(token).getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }

    private Claims getClaimsFromToken(String token) {
        Claims claims;
        
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
        	System.out.println("Gonna throw exception: " + e.getMessage() + "\n");
            claims = null;
        }
        return claims;
    }

    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(UserDetails userDetails) {        
        Claims claims = Jwts.claims()
        	.setSubject(userDetails.getUsername());
        
        claims.put("scopes", userDetails.getAuthorities());
        
        return generateToken(claims);
    }

    String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public Boolean canTokenBeRefreshed(String token) {
        return !isTokenExpired(token);
    }

    public String refreshToken(String token) {
        String refreshedToken;
        try {
        	if(!canTokenBeRefreshed(token)) {
        		throw new Exception("Error: token can't be refreshed.");
        	}
        	
            final Claims claims = getClaimsFromToken(token);
            claims.put(CLAIM_KEY_CREATED, new Date());
            refreshedToken = generateToken(claims);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }
    

}