package br.com.ProjetoPessoal.API.validators.Auth;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class AuthValidator {

	@NotNull
	private String name;
	
	@NotNull
	@Length(min = 8, max = 12)
	private String password;
	
	public String getName() {
		return this.name;
	}
	public String getPassword() {
		return this.password;
	}
	public void setPassword(String password) {	
		this.password = password;
	}
	public void setName(String name) { 
		this.name = name;
	}
}
