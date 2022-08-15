package br.com.alura.forum.controller.dto;

import java.util.ArrayList;
import java.util.List;

import br.com.alura.forum.modelo.Usuario;

public class UsuarioDto {
	private String nome;
	private String email;
	
	public UsuarioDto(Usuario usuario) {
		this.nome = usuario.getNome();
		this.email = usuario.getEmail();
	}
	
	public String getNome() {
		return this.nome;
	}
	public String getEmail() {
		return this.email;
	}
	
	public static List<UsuarioDto> converter(List<Usuario> usuarios) {
		List<UsuarioDto> dto = new ArrayList<>();
		
		usuarios.forEach(usuario -> {
			dto.add(new UsuarioDto(usuario));
		});

		return dto;
	}
	
	public static UsuarioDto converter(Usuario usuario) {
		return new UsuarioDto(usuario);
	}
}
