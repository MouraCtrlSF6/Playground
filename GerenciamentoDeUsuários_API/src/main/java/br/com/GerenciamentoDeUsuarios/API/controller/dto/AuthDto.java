package br.com.GerenciamentoDeUsuarios.API.controller.dto;

public class AuthDto {
	
	private UserDetailsDto user;
	private String token;
	
	public AuthDto(UserDetailsDto user, String token) {
		this.user = user;
		this.token = token;
	}
	
	public UserDetailsDto getUser() {
		return this.user;
	}
	public String getToken() {
		return this.token;
	}
}
