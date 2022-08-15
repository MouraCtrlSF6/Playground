package br.com.alura.forum.controller.dto;

import java.time.LocalDateTime;
import java.util.List;

import br.com.alura.forum.modelo.Usuario;

public class DetalhesUsuarioDto {
	private String nome;
	private String email;
	private LocalDateTime registeredAt;
	private List<TopicoDto> topicos;
	
	public DetalhesUsuarioDto(Usuario usuario) {
		this.nome = usuario.getNome();
		this.email = usuario.getEmail();
		this.registeredAt = usuario.getRegisteredAt();
		topicos = TopicoDto.converter(usuario.getTopicos());
	}

	public String getNome() {
		return this.nome;
	}

	public String getEmail() {
		return this.email;
	}

	public LocalDateTime getRegisteredAt() {
		return this.registeredAt;
	}
	
	public List<TopicoDto> getTopicos() {
		return this.topicos;
	}
}
