package br.com.alura.gerenciador.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    	System.out.println("ListEnterprises.doGet");

    	RequestDispatcher rd = request.getRequestDispatcher("/DisplayEnterprises.jsp");
    	
    	request.setAttribute("enterpriseList", Database.getEnterprises());
    	
    	rd.forward(request, response);
    	
    }

}
