package br.com.alura.gerenciador.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns="/HelloWorld")
public class HelloWorld extends HttpServlet {
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		String innerHTML = 
		"<html>" +
			"<body>" + 
				"Hello world! This is my page running on a TomCat servlet." + 
			"</body>" + 
		"</html>";
		out.println(innerHTML);
		
		System.out.println("O servlet HelloWorld foi chamado.");
	}
}
