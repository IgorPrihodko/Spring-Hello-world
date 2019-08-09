<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Delete product</title>
</head>
<body>
<center>
    <form action="/admin/deleteProduct" method="post">
        ID <input name="id" type="number" value="${id}" readonly>
        Title <input name="title" type="text" value="${title}" readonly/>
        Description <input name="description" type="text" value="${description}" readonly/>
        Price <input name="price" type="number" value="${price}" readonly/>
        <button type="submit">Delete</button>
    </form>
    <form action="/admin/products" method="get">
        <button type="submit">Back</button>
    </form>
</center>
</body>
</html>
