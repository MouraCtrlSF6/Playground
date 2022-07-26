package br.com.alura.gerenciador.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class NewEnterpriseServlet
 */
@WebServlet("/NewEnterprise")
public class NewEnterpriseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("NewEnterprisesServlet.doPost");
		
		PrintWriter out = response.getWriter();
		
		String name = request.getParameter("enterpriseName");
		
		Enterprise newEnterprise = new Enterprise(name);
		
		Database.register(newEnterprise);
		
		String innerHTML = 
		"<html>" +
			"<body>" + 
				"New enterprise " + newEnterprise.getName() + " successfully registered!" + 
			"</body>" +
		"</html>";
		
		out.println(innerHTML);
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		
		String innerHTML = 
		"<html>" +
			"<body>" + 
				"Method GET not allowed for this functionality." + 
			"<body>" +
		"<html>";
		
		out.println(innerHTML);
	}
	
}
