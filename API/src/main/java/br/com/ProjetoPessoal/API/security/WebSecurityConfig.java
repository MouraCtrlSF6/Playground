package br.com.ProjetoPessoal.API.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import br.com.ProjetoPessoal.API.repository.UserRepository;
import br.com.ProjetoPessoal.API.util.JwtTokenUtils;

@Configuration
public class WebSecurityConfig {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JwtTokenUtils jwtTokenUtil;
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.httpBasic()
			.and()
			.cors()
			.and()
			.csrf().disable();
		http
			.addFilter(new JwtTokenFilter(
				authenticationManager(http.getSharedObject(AuthenticationConfiguration.class)),
				jwtTokenUtil,
				userRepository
			));
		http
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http
			.authorizeHttpRequests()
			.antMatchers(HttpMethod.GET, "/users").hasRole("ADMIN")
			.anyRequest().authenticated()
			.and();

		return http.build();
	}
	
	@Bean
	public PasswordEncoder passwordEnconder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
	    return authenticationConfiguration.getAuthenticationManager();
	}
	
	@Bean
	public WebSecurityCustomizer ignoringCustomizer() {
		return web -> web
			.ignoring()
			.antMatchers(HttpMethod.POST, "/auth")
			.antMatchers(HttpMethod.POST, "/users")
			.antMatchers(HttpMethod.GET, "/");
	}
}
