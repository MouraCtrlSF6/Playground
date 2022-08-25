package br.com.ProjetoPessoal.API.security;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import br.com.ProjetoPessoal.API.models.User;
import br.com.ProjetoPessoal.API.repository.UserRepository;
import br.com.ProjetoPessoal.API.util.HttpUtils;
import br.com.ProjetoPessoal.API.util.JwtTokenUtils;
import br.com.ProjetoPessoal.API.util.MethodUtils;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;

public class JwtTokenFilter extends BasicAuthenticationFilter {
	
	private JwtTokenUtils jwtTokenUtil;
	
	private UserRepository userRepository;
	
	public JwtTokenFilter(
		AuthenticationManager authManager,
		JwtTokenUtils jwtTokenUtil,
		UserRepository userRepository
	) {
		super(authManager);
		this.jwtTokenUtil = jwtTokenUtil;
		this.userRepository = userRepository;
	}

	@Override
	protected void doFilterInternal(
		HttpServletRequest request, 
		HttpServletResponse response, 
		FilterChain filterChain)
	throws ServletException, IOException {
		try {	
			final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
			
			if(authHeader == null || authHeader.length() == 0 || !authHeader.startsWith("Bearer ")) {
				HttpUtils.UnexpectedResponse(
					response, 
					HttpServletResponse.SC_BAD_REQUEST,
					HttpUtils.ERROR_TITLE,
					HttpUtils.BAD_REQUEST_FEEDBACK
				);
				return;
			}
			
			final String jwtToken = authHeader.split(" ")[1].trim();
			
			jwtTokenUtil.validateToken(jwtToken);
			
			User userFromToken = userRepository
				.findByName(this.jwtTokenUtil.getUsernameFromToken(jwtToken));
				
			if(userFromToken == null) {
				filterChain.doFilter(request, response);
				MethodUtils.displayMessage("Couldn't get user from provided token");
				HttpUtils.UnexpectedResponse(
					response, 
					HttpServletResponse.SC_BAD_REQUEST, 
					HttpUtils.ERROR_TITLE,
					HttpUtils.BAD_REQUEST_FEEDBACK
				);
				return;
			}
			
			AbstractAuthenticationToken authentication = 
				new UsernamePasswordAuthenticationToken(
					userFromToken, 
		            null,
		            userFromToken.getAuthorities()
				);
			
			authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
	        SecurityContextHolder.getContext().setAuthentication(authentication);
	        
	        filterChain.doFilter(request, response);
		} catch (ExpiredJwtException e) {
			try {
				HttpUtils.UnexpectedResponse(
					response, 
					HttpServletResponse.SC_BAD_REQUEST,
					HttpUtils.ERROR_TITLE,
					HttpUtils.EXPIRED_TOKEN_FEEDBACK
				);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			return;

		} catch (MalformedJwtException e) {
			try {
				HttpUtils.UnexpectedResponse(
					response, 
					HttpServletResponse.SC_BAD_REQUEST,
					HttpUtils.ERROR_TITLE,
					HttpUtils.INVALID_TOKEN_FEEDBACK
				);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			return;

		} catch(Exception e) {
			MethodUtils.displayMessage(e.getMessage());
			try {
				HttpUtils.UnexpectedResponse(
					response, 
					HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
					HttpUtils.ERROR_TITLE,
					HttpUtils.INTERNAL_SERVER_ERROR_FEEDBACK
				);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			return;

		}
	}		
}
