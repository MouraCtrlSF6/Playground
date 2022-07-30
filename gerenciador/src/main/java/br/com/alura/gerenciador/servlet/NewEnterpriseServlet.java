package br.com.alura.gerenciador.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Date;
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
		System.out.println("NewEnterprisesServlet.doPost");
		
		String name = request.getParameter("enterpriseName");
		
		String dateParameter = request.getParameter("creationDate");
		
		Date creationDate;
		
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			creationDate = sdf.parse(dateParameter);
		} catch (ParseException e) {
			throw new ServletException(e);
		}
		
		Enterprise newEnterprise = new Enterprise(name, creationDate);
		
		Database.register(newEnterprise);
		
		RequestDispatcher rd = request.getRequestDispatcher("/newEnterpriseCreated.jsp");
		
		request.setAttribute("enterpriseName", newEnterprise.getName());
		
		request.setAttribute("enterpriseCreationDate", newEnterprise.getCreationDate());
		
		rd.forward(request, response);
		
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
