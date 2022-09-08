package br.com.GerenciamentoDeUsuarios.API.util;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;

import br.com.GerenciamentoDeUsuarios.API.controller.dto.DefaultJsonDto;

public interface HttpUtils {
	String INTERNAL_SERVER_ERROR_FEEDBACK = "An error has occurred while processing the request.";
	String BAD_REQUEST_FEEDBACK = "Invalid request provided.";
	String EXPIRED_TOKEN_FEEDBACK = "Provided access token expired.";
	String INVALID_TOKEN_FEEDBACK = "Invalid token provided.";
	
	String ERROR_TITLE = "Error";
	
	static <T> void UnexpectedResponse(
		HttpServletResponse response,
		Integer responseStatus,
		String title,
		T message
	) throws Exception {
		PrintWriter writer = response.getWriter();
		
		response.setStatus(responseStatus);
		
		String payload = DefaultJsonDto
				.generateJsonString(title, (String) message, HttpStatus.valueOf(responseStatus));
		
		writer.println(JsonUtils.parse(payload));
		
		return;
	}
}
