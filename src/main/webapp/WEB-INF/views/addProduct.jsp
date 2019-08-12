<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add product</title>
</head>
<body>
<center>
    <form action="/admin/addProduct" method="post">
        Title <input name="title" type="text"/>
        Description <input name="description" type="text"/>
        Price <input name="price" type="number" step="0.1"/>
        <button type="submit">Add</button>
    </form>
    <form action="/" method="get">
        <button type="submit">Exit</button>
    </form>
    <h2>${wrong}</h2>
</center>
</body>
</html>
