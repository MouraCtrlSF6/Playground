package br.com.ProjetoPessoal.API.controller;

import java.io.IOException;
import java.util.Arrays;

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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.ProjetoPessoal.API.controller.dto.DefaultJsonDto;
import br.com.ProjetoPessoal.API.controller.dto.UserDetailsDto;
import br.com.ProjetoPessoal.API.controller.dto.UserDto;
import br.com.ProjetoPessoal.API.validators.User.*;
import br.com.ProjetoPessoal.API.enums.RolesEnum;
import br.com.ProjetoPessoal.API.models.Role;
import br.com.ProjetoPessoal.API.models.User;
import br.com.ProjetoPessoal.API.repository.RoleRepository;
import br.com.ProjetoPessoal.API.repository.UserRepository;
import br.com.ProjetoPessoal.API.util.ControllerUtils;
import br.com.ProjetoPessoal.API.util.JwtTokenUtils;
import br.com.ProjetoPessoal.API.util.JsonUtils;

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

	@GetMapping("/{id}")
	public ResponseEntity<Object> find(@PathVariable Long id, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) 
	throws IOException {
		if(!ControllerUtils.exists(userRepository, id)) {
			String payload = DefaultJsonDto
				.generateJsonString("Error", "User with id " + id + " was not found.", HttpStatus.NOT_FOUND);
			
			return ResponseEntity
				.status(HttpStatus.NOT_FOUND)
				.body(JsonUtils.parse(payload));
		}
		
		final User user = userRepository.getReferenceById(id);
		
		token = token.split(" ")[1].trim();
		
		final String userNameFromToken = jwtTokenUtils.getUsernameFromToken(token);
		
		final Boolean isAdmin = this.checkUserRole("ROLE_ADMIN", token);
		
		if(!isAdmin && !(user.getName().equals(userNameFromToken))) {
			String payload = DefaultJsonDto
				.generateJsonString("Error", "You dont't have permission to access the recourse.", HttpStatus.FORBIDDEN);
			
			return ResponseEntity
				.status(HttpStatus.FORBIDDEN)
				.body(JsonUtils.parse(payload));
		}
	
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(UserDetailsDto.convert(user));
	}
	
	@PostMapping
	@Transactional
	public ResponseEntity<Object> register(@RequestBody @Valid UserRegisterValidator form) 
	throws IOException {
		try {
			
			User user = UserRegisterValidator.toModel(form, roleRepository);
			
			user.setRoles(Arrays.asList(roleRepository.findByRole(RolesEnum.valueOf("ROLE_COMMON"))));
			
			User newUser = userRepository.save(user);
			
			return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(UserDto.convert(newUser));
		} catch(Exception e) {			
			String payload = DefaultJsonDto
				.generateJsonString("Error", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			
			return ResponseEntity
				.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(JsonUtils.parse(payload));
		}
	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<Object> update(
		@RequestBody @Valid UserUpdateValidator form, 
		@PathVariable Long id,
		@RequestHeader(HttpHeaders.AUTHORIZATION) String token
	) throws IOException {
		try {
			if(!ControllerUtils.exists(userRepository, id)) {
				String payload = DefaultJsonDto
					.generateJsonString("Error", "User with id " + id + " was not found.", HttpStatus.NOT_FOUND);
					
				return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body(JsonUtils.parse(payload));
			}
			
			final User user = userRepository.getReferenceById(id);
			
			token = token.split(" ")[1].trim();
			
			final String userNameFromToken = jwtTokenUtils.getUsernameFromToken(token);
			
			final Boolean isAdmin = this.checkUserRole("ROLE_ADMIN", token);
			
			if(!isAdmin && !userNameFromToken.equals(user.getName())) {
				String payload = DefaultJsonDto
					.generateJsonString("Error", "You dont't have permission to access the recourse.", HttpStatus.FORBIDDEN);
					
				return ResponseEntity
					.status(HttpStatus.FORBIDDEN)
					.body(JsonUtils.parse(payload));
			}
			
			form.update(userRepository, id);
			
			String payload = DefaultJsonDto
				.generateJsonString("Success", "User successfully updated!", HttpStatus.OK);
			
			return ResponseEntity
				.status(HttpStatus.OK)
				.body(JsonUtils.parse(payload));
			
		} catch(Exception e) {
			System.out.println("\nError: " + e.getMessage());
			
			String payload = DefaultJsonDto
				.generateJsonString("Error", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			
			return ResponseEntity
				.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(JsonUtils.parse(payload));
		}
	}
	
	@PutMapping("/{id}/update-password")
	@Transactional
	public ResponseEntity<Object> updatePassword(
		@RequestBody @Valid UserUpdateValidator form,
		@PathVariable Long id,
		@RequestHeader(HttpHeaders.AUTHORIZATION) String token
	) throws IOException {
		try {
			if(!ControllerUtils.exists(userRepository, id)) {
				String payload = DefaultJsonDto
					.generateJsonString("Error", "User with id " + id + " was not found.", HttpStatus.NOT_FOUND);
				
				return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body(JsonUtils.parse(payload));
			}
			
			User user = userRepository.getReferenceById(id);
			
			token = token.split(" ")[1].trim();
			
			final String userNameFromToken = jwtTokenUtils.getUsernameFromToken(token);
			
			final Boolean isAdmin = this.checkUserRole("ROLE_ADMIN", token);
			
			if(!isAdmin && !userNameFromToken.equals(user.getName())) {
				String payload = DefaultJsonDto
					.generateJsonString("Error", "You dont't have permission to access the recourse.", HttpStatus.FORBIDDEN);
						
				return ResponseEntity
					.status(HttpStatus.FORBIDDEN)
					.body(JsonUtils.parse(payload));
			}
			
			form.updatePassword(userRepository, id);

			String payload = DefaultJsonDto
				.generateJsonString("Success", "Password successfully updated!", HttpStatus.OK);
			
			return ResponseEntity
				.status(HttpStatus.OK)
				.body(JsonUtils.parse(payload));
		} catch(Exception e) {		
			String payload = DefaultJsonDto
				.generateJsonString("Error", e.getMessage(), HttpStatus.BAD_REQUEST);
			
			return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.body(JsonUtils.parse(payload));
		}
	}
	
	private Boolean checkUserRole(String role, String token) {
		final Role roleValue = roleRepository.findByRole(RolesEnum.valueOf(role));
		
		return jwtTokenUtils.getUserRolesFromToken(token).contains(roleValue);
	}
}
