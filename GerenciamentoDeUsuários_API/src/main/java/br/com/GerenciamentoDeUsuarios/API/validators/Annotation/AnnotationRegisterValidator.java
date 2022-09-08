package br.com.GerenciamentoDeUsuarios.API.validators.Annotation;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.com.GerenciamentoDeUsuarios.API.models.Annotation;

public class AnnotationRegisterValidator {
	
	private String content;

	@NotNull
	@NotEmpty
	private String title;
	
	public String getContent() {
		return this.content;
	}
	public void setContent(String content) { 
		this.content = content;
	}
	public String getTitle() {
		return this.title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public static Annotation toModel(AnnotationRegisterValidator form) {
		final Annotation annotation = new Annotation();

		annotation.setContent(form.getContent());

		annotation.setTitle(form.getTitle());

		return annotation;
	}
}	
