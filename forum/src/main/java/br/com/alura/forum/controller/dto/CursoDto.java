package br.com.alura.forum.controller.dto;

import java.util.ArrayList;
import java.util.List;

import br.com.alura.forum.modelo.Curso;

public class CursoDto {

	private String nome;
	private String categoria;
	
	public CursoDto(Curso curso) {
		this.setNome(curso.getNome());
		this.setCategoria(curso.getCategoria());
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	
	public static List<CursoDto> converte(List<Curso> cursos) {
		List<CursoDto> cursosDto = new ArrayList<>();
		
		for (Curso curso : cursos) {
			cursosDto.add(new CursoDto(curso));
		}
		
		return cursosDto;
	}
	
}
