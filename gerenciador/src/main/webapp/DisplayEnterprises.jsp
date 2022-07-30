<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List, br.com.alura.gerenciador.servlet.Enterprise" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Display Enterprises</title>
</head>
<body>
	<div class="header">Registered enterprises list:</div>
	<ul class="enterpriseList">
		<c:forEach items="${enterpriseList}" var="enterprise">
			
			<li>${enterprise.name} - Created at: <fmt:formatDate value="${enterprise.creationDate}" pattern="dd/MM/yyy" /></li>
		</c:forEach>
	</ul>
</body>
</html>