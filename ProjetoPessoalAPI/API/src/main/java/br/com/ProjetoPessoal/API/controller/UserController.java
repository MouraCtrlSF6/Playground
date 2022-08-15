package br.com.ProjetoPessoal.API.controller;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ProjetoPessoal.API.controller.dto.UserDetailsDto;
import br.com.ProjetoPessoal.API.controller.dto.UserDto;
import br.com.ProjetoPessoal.API.controller.form.UserForm;
import br.com.ProjetoPessoal.API.models.User;
import br.com.ProjetoPessoal.API.repository.UserRepository;
import br.com.ProjetoPessoal.API.util.ControllerUtil;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<List<UserDto>> list(String name) {
		List<User> users = name == null
			? userRepository.findAll()
			: userRepository.findByName(name);
		
		return users.size() == 0
			? ResponseEntity
				.notFound()
				.build()
			: ResponseEntity
				.ok()
				.body(UserDto.convert(users));
	}
	
	@GetMapping("/{id}")
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<UserDetailsDto> find(@PathVariable Long id) {
		if(!ControllerUtil.exists(userRepository, id)) {
			return ResponseEntity
				.notFound()
				.build();
		}
		
		final User user = userRepository.getReferenceById(id);
		return ResponseEntity
			.ok()
			.body(UserDetailsDto.convert(user));
	}
	
	@PostMapping
	@Transactional
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<UserDto> register(@RequestBody @Valid UserForm form) {
		try {
			User user = UserForm.toModel(form);
			
			User newUser = userRepository.save(user);
			
			return ResponseEntity
				.ok()
				.body(UserDto.convert(newUser));
		} catch(Exception e) {
			return ResponseEntity
				.internalServerError()
				.build();
		}
	}
	
}
