package br.com.ProjetoPessoal.API.controller.dto;

import org.springframework.http.HttpStatus;

public class DefaultJsonDto {
	
	private String title;
	
	private String message;
	
	private HttpStatus status;
	
	private DefaultJsonDto() {}
	
	private DefaultJsonDto(String title, String message, HttpStatus status) {
		this.setTitle(title);
		this.setMessage(message);
		this.setStatus(status);
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public String getMessage() {
		return this.message;
	}
	
	public HttpStatus getStatus() {
		return this.status;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public void setStatus(HttpStatus status) {
		this.status = status;
	}
	
	public static String generateJsonString(String title, String message, HttpStatus status) {
		final DefaultJsonDto jsonPayloadValues = new DefaultJsonDto(title, message, status);
		
		return "{ "
				+ "\"title\": \"" + jsonPayloadValues.getTitle() + "\","
				+ "\"message\": \"" + jsonPayloadValues.getMessage() + "\","
				+ "\"status\": " + jsonPayloadValues.getStatus().value()
				+ "}";	
	}
}
