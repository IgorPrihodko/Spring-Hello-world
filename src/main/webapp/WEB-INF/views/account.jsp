
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Account page</title>
</head>
<body>
<div align="center">
    <h2>Welcome to your account!</h2>
</div>
<div align="center">
    <form action="/user/products" method="get">
        <button type="submit">All products</button>
    </form>
</div>
<h2 align="center">${success}</h2>
<div align="center">
    <form action="/" method="get">
        <button type="submit">Exit</button>
    </form>
</div>
</body>
</html>
