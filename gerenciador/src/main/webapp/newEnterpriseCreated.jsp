<%
	String enterpriseName = (String) request.getAttribute("enterpriseName");	
%>

<html lang="en-US">
	<head>
		<title>Registered</title>
		<meta charset="UTF-8" />
	</head>
	<body>
		<div class="feedbackMessage">New enterprise <% out.println(enterpriseName); %> successfully registered.</div>
	</body>
</html>                                                 