package br.com.alura.gerenciador.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DisplayEnterprises
 */

@WebServlet("/DisplayEnterprises")
public class DisplayEnterprises extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DisplayEnterprises() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	System.out.println("ListEnterprises.doGet");
    	
    	PrintWriter out = response.getWriter();
    	
    	String names = null;
    	
    	for(Enterprise enterprise : Database.getEnterprises()) {
    		names += "<li>" + enterprise.getName() + "</li>";
    	}
    	
    	String innerHTML = 
    	"<html>" +
    		"<body>" +
    			"Registered enterprises: " +
    			"<ul>" + names + "</ul>" + 
    		"<body>" +
    	"<html>";
    	
    	
    	out.println(innerHTML);
    }

}
