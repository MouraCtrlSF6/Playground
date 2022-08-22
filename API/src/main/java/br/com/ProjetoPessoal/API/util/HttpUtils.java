package br.com.ProjetoPessoal.API.util;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

public interface HttpUtils {
	String INTERNAL_SERVER_ERROR_FEEDBACK = "Error: An error has occurred while processing the request";
	String BAD_REQUEST_FEEDBACK = "Error: Invalid request provided";
	
	static <T> void UnexpectedResponse(
		HttpServletResponse response,
		Integer reponseStatus,
		T payload
	) throws Exception {
		PrintWriter writer = response.getWriter();
		
		response.setStatus(reponseStatus);
		
		writer.println(payload);
		
		return;
	}
}
