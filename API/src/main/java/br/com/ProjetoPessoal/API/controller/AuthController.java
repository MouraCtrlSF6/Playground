package br.com.ProjetoPessoal.API.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ProjetoPessoal.API.controller.dto.AuthDto;
import br.com.ProjetoPessoal.API.controller.dto.DefaultJsonDto;
import br.com.ProjetoPessoal.API.controller.dto.UserDetailsDto;
import br.com.ProjetoPessoal.API.validators.Auth.*;
import br.com.ProjetoPessoal.API.models.User;
import br.com.ProjetoPessoal.API.repository.UserRepository;
import br.com.ProjetoPessoal.API.util.JsonUtils;
import br.com.ProjetoPessoal.API.util.JwtTokenUtils;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JwtTokenUtils jwtTokenUtil;
	
	@PostMapping
	@Transactional
	public ResponseEntity<Object> auth(@RequestBody @Valid AuthValidator form) throws IOException {
		try {
			User user = userRepository.findByName(form.getName());
			
			if(user == null) {
				String payload = DefaultJsonDto
					.generateJsonString("Error", "User not found.", HttpStatus.NOT_FOUND);
				
				return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body(JsonUtils.parse(payload));
			}
			
			Boolean samePassword = new BCryptPasswordEncoder()
				.matches(form.getPassword(), user.getPassword());
			
			if(!samePassword) {
				String payload = DefaultJsonDto
					.generateJsonString("Error", "Incorrect password provided.", HttpStatus.BAD_REQUEST);
				
				return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body(JsonUtils.parse(payload));
			}
			
			Authentication auth = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(
					form.getName(), form.getPassword()
			));
			
			user = (User) auth.getPrincipal();
			
			String userToken = jwtTokenUtil.generateToken(user);
			
			user.setLastAccessAt(LocalDateTime.now());
			
			return ResponseEntity
				.status(HttpStatus.OK)
				.header(HttpHeaders.AUTHORIZATION, userToken)
				.body(new AuthDto(UserDetailsDto.convert(user), userToken));
		} catch(BadCredentialsException e) {
			String payload = DefaultJsonDto
				.generateJsonString("Error", e.getMessage(), HttpStatus.UNAUTHORIZED);
			
			return ResponseEntity
				.status(HttpStatus.UNAUTHORIZED)
				.body(JsonUtils.parse(payload));
		}
	}
}
