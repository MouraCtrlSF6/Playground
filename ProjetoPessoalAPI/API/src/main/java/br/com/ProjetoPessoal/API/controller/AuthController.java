package br.com.ProjetoPessoal.API.controller;

import java.util.List;

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

import br.com.ProjetoPessoal.API.controller.dto.UserDetailsDto;
import br.com.ProjetoPessoal.API.controller.form.AuthForm;
import br.com.ProjetoPessoal.API.models.User;
import br.com.ProjetoPessoal.API.repository.UserRepository;
import br.com.ProjetoPessoal.API.util.JwtTokenUtil;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@PostMapping
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<Object> auth(@RequestBody @Valid AuthForm form) {
		try {
			List<User> users = userRepository.findByName(form.getName());
			
			if(users.size() == 0) {
				return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body("Error: User not found");
			}
		
			User user = users.get(0);
			
			Boolean samePassword = new BCryptPasswordEncoder()
				.matches(form.getPassword(), user.getPassword());
			
			if(!samePassword) {
				return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body("Error: Provided password doesnt match");
			}
			
			// Method 1
			Authentication auth = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(
					form.getName(), form.getPassword()
			));
			
			user = (User) auth.getPrincipal();
			
			System.out.println("User: " + user.getName());
			System.out.println("Password: " + user.getPassword());
			user.getRoles().forEach(role -> System.out.println(role.getRole()));
			
			return ResponseEntity
				.status(HttpStatus.OK)
				.header(HttpHeaders.AUTHORIZATION, jwtTokenUtil.generateToken(user))
				.body(UserDetailsDto.convert(user));
		} catch(BadCredentialsException e) {
			return ResponseEntity
				.status(HttpStatus.UNAUTHORIZED)
				.body("Error: " + e.getMessage());
		}
	}
}
