package br.com.ProjetoPessoal.API.controller.form;

import javax.validation.constraints.NotNull;

import br.com.ProjetoPessoal.API.models.Annotation;

public class AnnotationForm {
	
	@NotNull
	private Long user_id;
	
	private String content;
	
	public Long getUser_id() {
		return this.user_id;
	}
	public String getContent() {
		return this.content;
	}
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}
	public void setContent(String content) { 
		this.content = content;
	}
	
	public static Annotation toModel(AnnotationForm form) {
		return new Annotation(form.getUser_id(), form.getContent());
	}
}	
