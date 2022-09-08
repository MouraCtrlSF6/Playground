package br.com.GerenciamentoDeUsuarios.API.validators.Auth;

import javax.validation.constraints.NotNull;

public class AuthValidator {

	@NotNull
	private String name;
	
	@NotNull
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
