package br.com.GerenciamentoDeUsuarios.API.controller.dto;

import org.springframework.data.domain.Page;

import br.com.GerenciamentoDeUsuarios.API.enums.RolesEnum;
import br.com.GerenciamentoDeUsuarios.API.models.Role;

public class RoleDto {
	
	private RolesEnum role;
	
	public RoleDto(Role role) {
		this.role = role.getRole();
	}
	
	public RolesEnum getRole() {
		return this.role;
	}
	
	public static Page<RoleDto> convert(Page<Role> roles) {
		return roles.map(RoleDto::new);
	}
	
	public static RoleDto convert(Role role) {
		return new RoleDto(role);
	}
}
