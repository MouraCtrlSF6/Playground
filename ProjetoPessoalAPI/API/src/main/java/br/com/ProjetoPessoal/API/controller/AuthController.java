package br.com.ProjetoPessoal.API.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private UserRepository userRepository;
	
	@PostMapping
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<UserDetailsDto> auth(@RequestBody @Valid AuthForm form) {
		List<User> users = userRepository.findByName(form.getName());
		
		if(users.size() == 0) {
			return ResponseEntity
				.notFound()
				.build();
		}
		
		User user = users.get(0);
		if(!user.getPassword().equals(form.getPassword())) {
			return ResponseEntity
				.badRequest()
				.build();
		}
		
		return ResponseEntity
			.ok()
			.body(UserDetailsDto.convert(user));
		
	}
}
