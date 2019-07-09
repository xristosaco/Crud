<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>University Enrollments</title>

	<style>
		tr:first-child{
			font-weight: bold;
			background-color: #C6C9C4;
		}
	</style>

</head>


<body>
	<h2>List of Trainers</h2>	
	<table>
		<tr>
			<td>NAME</td><td>Joining Date</td><td>Salary</td><td>SSN</td><td></td>
		</tr>
		<c:forEach items="${trainers}" var="trainer">
			<tr>
			<td>${trainer.name}</td>
			<td>${trainer.joiningDate}</td>
			<td>${trainer.salary}</td>
			<td><a href="<c:url value='/edit-${trainer.ssn}-trainer' />">${trainer.ssn}</a></td>
			<td><a href="<c:url value='/delete-${trainer.ssn}-trainer' />">delete</a></td>
			</tr>
		</c:forEach>
	</table>
	<br/>
	<a href="<c:url value='/new' />">Add New Trainer</a>
</body>
</html>