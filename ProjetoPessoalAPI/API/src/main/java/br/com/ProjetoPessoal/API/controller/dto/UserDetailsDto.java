package br.com.ProjetoPessoal.API.controller.dto;

import java.time.LocalDateTime;

import br.com.ProjetoPessoal.API.models.User;

public class UserDetailsDto {
	private Long id;
	private String name;
	private String cpf;
	private LocalDateTime registerDate;
		
	public UserDetailsDto(User user) {
		this.id = user.getId();
		this.name = user.getName();
		this.cpf = user.getCpf();
		this.registerDate = user.getRegisterDate();
	}
	
	public Long getId() {
		return this.id;
	}
	public String getName() {
		return this.name;
	}
	public String getCpf() {
		return this.cpf;
	}
	public LocalDateTime getRegisterDate() {
		return this.registerDate;
	}
	
	public static UserDetailsDto convert(User user) {
		return new UserDetailsDto(user);
	}
}
