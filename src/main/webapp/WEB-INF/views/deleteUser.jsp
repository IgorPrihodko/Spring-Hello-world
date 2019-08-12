<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Delete user</title>
</head>
<body>
<center>
    <form action="/admin/deleteUser" method="post">
        ID <input name="id" type="number" value="${id}" readonly>
        Email <input name="email" type="email" value="${email}" readonly/>
        Password <input name="password" type="password" value="${password}" readonly/>
        Role <input name="role" type="text" value="${role}" readonly/>
        <button type="submit">Delete</button>
    </form>
    <form action="/admin/users" method="get">
        <button type="submit">Back</button>
    </form>
</center>
</body>
</html>
