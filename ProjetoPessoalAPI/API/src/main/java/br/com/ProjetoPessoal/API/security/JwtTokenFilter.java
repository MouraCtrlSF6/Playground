package br.com.ProjetoPessoal.API.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.ProjetoPessoal.API.models.User;
import br.com.ProjetoPessoal.API.repository.UserRepository;
import br.com.ProjetoPessoal.API.util.JwtTokenUtil;

@Component
//public class JwtTokenFilter extends BasicAuthenticationFilter {
public class JwtTokenFilter extends OncePerRequestFilter {
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private UserRepository userRepository;
	
//	public JwtTokenFilter(
//		AuthenticationManager authManager,
//		JwtTokenUtil jwtTokenUtil,
//		UserRepository userRepository
//	) {
//		super(authManager);
//		this.jwtTokenUtil = jwtTokenUtil;
//		this.userRepository = userRepository;
//	}

	@Override
	protected void doFilterInternal(
		HttpServletRequest request, 
		HttpServletResponse response, 
		FilterChain filterChain)
	throws ServletException, IOException {
		
		final boolean True = true;
		if(!True) {
			return;
		}
		
		final String AuthHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		displayMessage("Got authHeader: " + AuthHeader);
		
		if(AuthHeader == null || AuthHeader.length() == 0 || !AuthHeader.startsWith("Bearer ")) {
			displayMessage("JwtTokenFilter: No auth or bearer token provided");
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		final String jwtToken = AuthHeader.split(" ")[1].trim();
		if(!this.jwtTokenUtil.canTokenBeRefreshed(jwtToken)) {
			displayMessage("JwtTokenFilter: jwtToken provided is not valid");
			filterChain.doFilter(request, response);
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
		User userFromToken = userRepository
			.findByName(this.jwtTokenUtil.getUsernameFromToken(jwtToken))
			.get(0);
		if(userFromToken == null) {
			displayMessage("JwtTokenFilter: No user provided in the token");
			filterChain.doFilter(request, response);
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
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
	}		
	
	private static void displayMessage(String message) {
		System.out.println("\n\n------------------------");
		System.out.println(message);
		System.out.println("------------------------\n\n");
	}

}
