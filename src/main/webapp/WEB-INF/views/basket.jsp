<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>List of products you want to buy</title>
</head>
<body>
<div align="right">
    <form action="/user/products" method="get">
        <button type="submit">Back to products</button>
    </form>
</div>
<table border="1" align="center">
    <div align="center">
        <h2>Your basket</h2>
    </div>
    <tr>
        <td>ID</td>
        <td>Title</td>
        <td>Description</td>
        <td>Price</td>
        <td>Delete</td>
    </tr>
    <c:forEach var="product" items="${basket.productsInBasket}">
        <tr>
            <td>${product.id}</td>
            <td>${product.title}</td>
            <td>${product.description}</td>
            <td>${product.price}</td>
            <td><form action="/user/deleteFromBasket?id=${product.id}" method="post">
                <button type="submit">Delete</button>
            </form></td>
        </tr>
    </c:forEach>
</table>
<div align="center">
    <h3>${totalPrice}</h3>
    <form action="/user/order" method="get">
        <button type="submit">Make Order</button>
    </form>
    <h3>${wrong}</h3>
</div>
</body>
</html>
