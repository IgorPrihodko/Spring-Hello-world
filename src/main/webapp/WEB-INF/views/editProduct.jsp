<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit product</title>
</head>
<body>
<center>
    <form action="/admin/editProduct" method="post">
        ID <input name="id" type="number" value="${id}" readonly/>
        Title <input name="title" type="text" value="${title}"/>
        Description <input name="description" type="text" value="${description}"/>
        Price <input name="price" type="number" step="0.1" value="${price}"/>
        <button type="submit">Edit</button>
    </form>
    <h2>${wrong}</h2>
    <form action="/admin/products" method="get">
        <button type="submit">Back</button>
    </form>
</center>
</body>
</html>
