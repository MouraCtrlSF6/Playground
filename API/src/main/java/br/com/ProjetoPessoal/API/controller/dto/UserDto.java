package br.com.ProjetoPessoal.API.controller.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

import br.com.ProjetoPessoal.API.models.User;

public class UserDto {
	private Long id;
	private String name;
	private LocalDateTime registerDate;
		
	public UserDto(User user) {
		this.id = user.getId();
		this.name = user.getName();
		this.registerDate = user.getRegisterDate();
	}
	
	public Long getId() {
		return this.id;
	}
	public String getName() {
		return this.name;
	}
	public LocalDateTime getRegisterDate() {
		return this.registerDate;
	}
	
	public static Page<UserDto> convert(Page<User> users) {
		return users.map(UserDto::new);
	}
	
	public static UserDto convert(User user) {
		return new UserDto(user);
	}
}	
