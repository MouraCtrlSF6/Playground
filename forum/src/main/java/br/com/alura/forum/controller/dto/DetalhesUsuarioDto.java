package br.com.alura.forum.controller.dto;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;

import br.com.alura.forum.modelo.Usuario;

public class DetalhesUsuarioDto {
	private String nome;
	private String email;
	private LocalDateTime registeredAt;
	
	public DetalhesUsuarioDto(Usuario usuario) {
		this.nome = usuario.getNome();
		this.email = usuario.getEmail();
		this.registeredAt = usuario.getRegisteredAt();
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
}
