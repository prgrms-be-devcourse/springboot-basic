<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
    <title>Home</title>
</head>
<body class="container-fluid">
<h1>KDT Spring App</h1>
<img src="<c:url value="/resources/bg.png" />" class="img-fluid">
<p>The time on the server is ${serverTime}</p>
<h2>Customer Table</h2>
<table class="table table-striped table-hover">
    <thead>
    <tr>
        <th scope="col">ID</th>
        <th scope="col">Name</th>
        <th scope="col">Email</th>
        <th scope="col">CreatedAt</th>
        <th scope="col">LastLoginAt</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="customer" items="${customers}">
        <tr>
            <th scope="row">${customer.customerId}</th>
            <td>${customer.name}</td>
            <td>${customer.email}</td>
            <td>${customer.createdAt}</td>
            <td>${customer.lastLoginAt}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>