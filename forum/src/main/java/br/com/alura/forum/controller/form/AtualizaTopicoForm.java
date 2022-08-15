package br.com.alura.forum.controller.form;

import br.com.alura.forum.modelo.Topico;
import br.com.alura.forum.repository.TopicoRepository;

public class AtualizaTopicoForm {

	private String titulo;
	private String mensagem;
	
	public String getTitulo() {
		return this.titulo;
	}
	public String getMensagem() {
		return this.mensagem;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	
	public Topico update(Long id, TopicoRepository topicoRepository) {
		Topico topico = topicoRepository.getReferenceById(id);
		topico.setTitulo(this.getTitulo());
		topico.setMensagem(this.getMensagem());
		
		return topico;
	}
}
