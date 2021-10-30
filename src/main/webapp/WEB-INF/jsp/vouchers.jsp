<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
<%--    <meta charset="UTF-8">--%>
<%--    <meta name="viewport"--%>
<%--    content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">--%>
<%--    <meta http-equiv="X-UA-Compatible" content="ie=edge">--%>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
    <title>Home</title>
</head>
<body class="container-fluid">
    <h1>KDT Spring App</h1>
    <img src="<c:url value="/resources/bg.jpg" />" class="img-fluid"/>
    <p>The time on the server is ${serverTime}</p>

<h2>Voucher Table</h2>
    <%--    <c:forEach var="i" begin="1" end="10" step="1">${i}</c:forEach>--%>
    <%--<%--%>
    <%--    for(int i = 0 ; i < 10; i++) {--%>
    <%--        %>--%>
    <%--    <%=i %><br>--%>
    <%--    <%--%>
    <%--    }--%>
    <%--%>--%>
<%--    <c:forEach var="customer" items="${customers}">${customer.customerId}${i} <br> </c:forEach>--%>

    <table class="table table-bordered table-striped table-hover">
        <thead>
        <tr>
            <td>ID</td>
            <td>Value</td>
            <td>Type</td>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="voucher" items="${vouchers}">
            <tr>
                <td>
                    <a href='<c:url value="vouchers/${voucher.voucherId}" />'>${voucher.voucherId}</a>
                </td>
                <td>${voucher.voucherId}</td>
                <td>${voucher.value}</td>
                <td>${voucher.voucherStatus}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

</body>

</html>
