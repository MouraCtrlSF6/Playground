<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:url value="/NewEnterprise" var="redirectUrl" />
 
<html lang="en-US">
	<head>
		<meta charset="ISO-8859-1">
		<title>Register</title>
	</head>
	<body>
		<form action="${redirectUrl}" method="POST">
			<label>New Enterprise name: </label>
			<input type="text" name="enterpriseName" />
			
			<label>Creation date:</label>
			<input type="text" name="creationDate" />
			
			<input type="submit" />
		</form>	
	</body>
</html>