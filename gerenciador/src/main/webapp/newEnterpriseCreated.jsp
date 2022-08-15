<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html lang="en-US">
	<head>
		<title>Registered</title>
		<meta charset="UTF-8" />
	</head>
	<body>
		<div class="feedbackMessage">
			<c:if test="${ not empty enterpriseName }">
				New enterprise ${ enterpriseName } successfully registered.
			</c:if>
			<c:if test="${ empty enterpriseName }">
				No enterprise registered. 
			</c:if>
		</div>
	</body>
</html>                                                 