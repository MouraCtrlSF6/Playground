package br.com.ProjetoPessoal.API.controller;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.ProjetoPessoal.API.controller.dto.UserDetailsDto;
import br.com.ProjetoPessoal.API.controller.dto.UserDto;
import br.com.ProjetoPessoal.API.controller.form.UserForm;
import br.com.ProjetoPessoal.API.enums.RolesEnum;
import br.com.ProjetoPessoal.API.models.User;
import br.com.ProjetoPessoal.API.repository.RoleRepository;
import br.com.ProjetoPessoal.API.repository.UserRepository;
import br.com.ProjetoPessoal.API.util.ArrayUtils;
import br.com.ProjetoPessoal.API.util.ControllerUtils;
import br.com.ProjetoPessoal.API.util.JwtTokenUtils;
import br.com.ProjetoPessoal.API.util.MethodUtils;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private JwtTokenUtils jwtTokenUtils;
	
	@GetMapping
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<Page<UserDto>> list(
		@RequestParam(required = false) String name,
		@PageableDefault(
			page = 0, 
			size = 10, 
			sort = "id", 
			direction = Direction.ASC
		) Pageable pagination
	) {
		Page<User> users = name == null
			? userRepository.findAll(pagination)
			: userRepository.findByName(name, pagination);
		
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(UserDto.convert(users));
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/{id}")
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<Object> find(@PathVariable Long id, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
		if(!ControllerUtils.exists(userRepository, id)) {
			return ResponseEntity
				.status(HttpStatus.NOT_FOUND)
				.body("User with id " + id + "not found.");
		}
		
		final User user = userRepository.getReferenceById(id);
		
		token = token.split(" ")[1].trim();
		
		final String userNameFromToken = jwtTokenUtils.getUsernameFromToken(token);
		
		final User client = userRepository.findByName(userNameFromToken).get(0);
		
		final Boolean isAdmin = ArrayUtils.find(client.getRoles(), (role, index, roles) -> {
			return role.getRole() == RolesEnum.valueOf("ROLE_ADMIN");
		}) != null;
		
		if(!isAdmin && !(user.getName().equals(userNameFromToken))) {
			return ResponseEntity
				.status(HttpStatus.FORBIDDEN)
				.build();
		}
	
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(UserDetailsDto.convert(user));
	}
	
	@PostMapping
	@Transactional
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<Object> register(@RequestBody @Valid UserForm form) {
		try {
			
			User user = UserForm.toModel(form, roleRepository);
			
			User newUser = userRepository.save(user);
			
			return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(UserDto.convert(newUser));
		} catch(Exception e) {
			System.out.println("\nError: " + e.getMessage());
			
			return ResponseEntity
				.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body("Error: " + e.getMessage());
		}
	}
	
}
