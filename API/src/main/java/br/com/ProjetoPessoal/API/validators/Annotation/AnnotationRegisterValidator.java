package br.com.ProjetoPessoal.API.validators.Annotation;

import br.com.ProjetoPessoal.API.models.Annotation;

public class AnnotationRegisterValidator {
	
	private String content;
	
	public String getContent() {
		return this.content;
	}
	public void setContent(String content) { 
		this.content = content;
	}
	
	public static Annotation toModel(AnnotationRegisterValidator form) {
		final Annotation annotation = new Annotation();

		annotation.setContent(form.getContent());

		return annotation;
	}
}	
