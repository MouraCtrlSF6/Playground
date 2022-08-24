package br.com.ProjetoPessoal.API.validators.User;

import java.util.Arrays;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.com.ProjetoPessoal.API.models.Role;
import br.com.ProjetoPessoal.API.models.User;
import br.com.ProjetoPessoal.API.repository.RoleRepository;

public class UserRegisterValidator {
	
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
	
	
	public static User toModel(UserRegisterValidator form, RoleRepository roleRepository) {
		return new User(
			form.getName(), 
			form.getPassword(),
			form.getCpf(),
			Arrays.asList(new Role())
		);
	}
}
