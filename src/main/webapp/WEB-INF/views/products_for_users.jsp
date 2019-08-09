<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Products</title>
</head>
<body>
<div align="right">
    <form action="/user/basket" method="get">
        <button type="submit">Basket</button>
    </form>
</div>
<table border="1" align="center">
    <div align="center">
        <h2>All products</h2>
    </div>
    <tr>
        <td>ID</td>
        <td>Title</td>
        <td>Description</td>
        <td>Price</td>
        <td>To basket</td>
    </tr>
    <c:forEach var="product" items="${products}">
        <tr>
            <td>${product.id}</td>
            <td>${product.title}</td>
            <td>${product.description}</td>
            <td>${product.price}</td>
            <td><form action="/user/addToBasket?id=${product.id}" method="post">
                <button type="submit">To basket</button>
            </form></td>
        </tr>
    </c:forEach>
</table>
<div align="center">
    <form action="/" method="get">
        <button type="submit">Exit</button>
    </form>
</div>
</body>
</html>
