package br.com.ProjetoPessoal.API.controller.form;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.com.ProjetoPessoal.API.enums.RolesEnum;
import br.com.ProjetoPessoal.API.models.Role;
import br.com.ProjetoPessoal.API.models.User;
import br.com.ProjetoPessoal.API.repository.RoleRepository;

public class UserForm {
	
	@NotNull
	@NotEmpty
	private String name;
	
	@NotNull
	@NotEmpty
	@Length(min = 11, max = 11)
	private String cpf;
	
	@NotNull
	@NotEmpty
	@Length(min = 8, max = 12)
	private String password;
	
	@NotNull
	private List<String> roles;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public List<Role> getRoles(RoleRepository roleRepository) {		
		List<Role> roles = new ArrayList<>();
		
		this.roles.forEach((role) -> 
			roles.add(roleRepository.findByRole(RolesEnum.valueOf(role)))
		);
		
		return roles;
	}
	
	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	
	public static User toModel(UserForm form, RoleRepository roleRepository) {
		return new User(
			form.getName(), 
			form.getPassword(),
			form.getCpf(),
			form.getRoles(roleRepository)
		);
	}
}
