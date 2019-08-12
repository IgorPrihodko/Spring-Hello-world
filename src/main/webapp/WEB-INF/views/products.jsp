<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All products</title>
</head>
<body>
<div align="center">
    <a href="/admin/addProduct">Add new product</a>
</div>
<table border="1" align="center">
    <div align="center">
        <h2>All products list</h2>
    </div>
    <tr>
        <td>ID</td>
        <td>Title</td>
        <td>Description</td>
        <td>Price</td>
        <td>Edit product</td>
        <td>Delete product</td>
    </tr>
    <c:forEach var="product" items="${products}">
        <tr>
            <td>${product.id}</td>
            <td>${product.title}</td>
            <td>${product.description}</td>
            <td>${product.price}</td>
            <td><a href="/admin/editProduct?id=${product.id}">Edit</a></td>
            <td><a href="/admin/deleteProduct?id=${product.id}">Delete</a></td>
        </tr>
    </c:forEach>
</table>
<div align="center">
    <a href="/admin/users">List of all users</a>
</div>
<div align="center">
    <form action="/" method="get">
        <button type="submit">Exit</button>
    </form>
</div>
</body>
</html>
