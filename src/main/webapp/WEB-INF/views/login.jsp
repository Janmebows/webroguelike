<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Login</title>
</head>
<body>
<h3>Login page</h3>
	<sf:form action="login" method="post" modelAttribute="user">
		<sf:input path="username" placeholder="Username"/>
		<sf:input path="password" type="password" placeholder="Password"/>
		<input type="submit" value="login"/>	
	</sf:form>
</body>
</html>