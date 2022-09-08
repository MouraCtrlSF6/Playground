package br.com.GerenciamentoDeUsuarios.API.controller.dto;

import java.time.LocalDateTime;
import java.util.List;

import br.com.GerenciamentoDeUsuarios.API.models.Role;
import br.com.GerenciamentoDeUsuarios.API.models.User;

public class UserDetailsDto {
	private Long id;
	private String name;
	private String cpf;
	private List<Role> roles;
	private LocalDateTime registerDate;
	private LocalDateTime updateDate;
	private LocalDateTime lastAccessAt;
		
	public UserDetailsDto(User user) {
		this.id = user.getId();
		this.name = user.getName();
		this.cpf = user.getCpf();
		this.registerDate = user.getRegisterDate();
		this.roles = user.getRoles();
		this.updateDate = user.getUpdatedAt();
		this.lastAccessAt = user.getLastAccessAt();
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
	public List<Role> getRoles() {
		return this.roles;
	}
	public LocalDateTime getUpdateDate() {
		return this.updateDate;
	}
	public LocalDateTime getLastAccessAt() {
		return this.lastAccessAt;
	}
	
	public static UserDetailsDto convert(User user) {
		return new UserDetailsDto(user);
	}
}
