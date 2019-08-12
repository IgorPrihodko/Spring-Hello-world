<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>
<center>
    <form action="/admin/addUser" method="post">
        Email <input name="email" type="email"/>
        Password <input name="password" type="password"/>
        Repeat password <input name="repeatPassword" type="password"/>
        Admin <input name="role" type="radio" value="admin"/>
        User <input name="role" type="radio" value="user"/>
        <button type="submit">Register</button>
    </form>
    <form action="/" method="get">
        <button type="submit">Exit</button>
    </form>
    <h2>${wrong}</h2>
</center>
</body>
</html>
