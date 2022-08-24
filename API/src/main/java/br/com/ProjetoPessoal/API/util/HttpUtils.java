package br.com.ProjetoPessoal.API.util;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;

import br.com.ProjetoPessoal.API.controller.dto.DefaultJsonDto;

public interface HttpUtils {
	String INTERNAL_SERVER_ERROR_FEEDBACK = "Error: An error has occurred while processing the request";
	String BAD_REQUEST_FEEDBACK = "Error: Invalid request provided";
	
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
