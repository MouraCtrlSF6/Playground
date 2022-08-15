package br.com.ProjetoPessoal.API.controller.form;

public class AuthForm {
	private String name;
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
