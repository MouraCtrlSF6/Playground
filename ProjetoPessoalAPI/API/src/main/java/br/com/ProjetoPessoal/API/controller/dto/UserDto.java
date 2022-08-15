package br.com.ProjetoPessoal.API.controller.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
	
	public static List<UserDto> convert(List<User> users) {
		final List<UserDto> dto = new ArrayList<>();
		
		users.forEach(user -> dto.add(UserDto.convert(user)));
		
		return dto;
	}
	
	public static UserDto convert(User user) {
		return new UserDto(user);
	}
}	
