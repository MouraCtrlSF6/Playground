package br.com.alura.forum.controller.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.com.alura.forum.modelo.Curso;
import br.com.alura.forum.modelo.Topico;
import br.com.alura.forum.repository.CursoRepository;

public class TopicoForm {

	@NotNull 
	@NotEmpty
	@Length(min = 5)
	private String titulo;
	
	@NotNull
	@NotEmpty
	@Length(min = 10)
	private String mensagem;
	
	@NotNull
	@NotEmpty
	private String cursoNome;
	
	public String getTitulo() {
		return this.titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getMensagem() {
		return this.mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	public String getCursoNome() {
		return this.cursoNome;
	}
	public void setCursoNome(String cursoNome) {
		this.cursoNome = cursoNome;
	}
	
	public Topico toModel(CursoRepository cursoRepository) {		
		Curso curso = cursoRepository
			.findByNome(this.getCursoNome())
			.get(0);
		
		return new Topico(
			this.getTitulo(),
			this.getMensagem(),
			curso
		);
				
	}
}
