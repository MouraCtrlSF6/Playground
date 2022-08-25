package br.com.ProjetoPessoal.API.controller.dto;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;

import br.com.ProjetoPessoal.API.models.Role;
import br.com.ProjetoPessoal.API.models.User;

public class UserDto {
	private Long id;
	private String name;
	private LocalDateTime registeredAt;
	private LocalDateTime updatedAt;
		
	public UserDto(User user) {
		this.id = user.getId();
		this.name = user.getName();
		this.registeredAt = user.getRegisterDate();
		this.updatedAt = user.getUpdatedAt();
	}
	
	public Long getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}
	
	public LocalDateTime getRegisterDate() {
		return this.registeredAt;
	}

	public LocalDateTime getUpdatedAt() {
		return this.updatedAt;
	}
	
	public static Page<UserDto> convert(Page<User> users) {
		return users.map(UserDto::new);
	}
	
	public static UserDto convert(User user) {
		return new UserDto(user);
	}
}	
