<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@
	page import="java.util.List, br.com.alura.gerenciador.servlet.Enterprise"
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Display Enterprises</title>
</head>
<body>
	<div class="header">Registered enterprises list:</div>
	<ul class="enterpriseList">
	
		<%
			List<Enterprise> enterpriseList = (List<Enterprise>) request.getAttribute("enterpriseList");
			
			String names = "";
			
			for(Enterprise enterprise : enterpriseList) {
				names += "<li>" + enterprise.getName() + "</li>";
			}
			
			out.println(names);
		%>
	
	</ul>
</body>
</html>