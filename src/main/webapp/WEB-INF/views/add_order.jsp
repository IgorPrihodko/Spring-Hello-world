<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Order</title>
</head>
<body>
<div align="center">
    <form action="/user/order" method="post">
        User ID <input name="userID" type="number" value="${userID}" readonly/>
        Email <input name="userEmail" type="email" value="${userEmail}"/>
        Name <input name="name" type="text"/>
        Surname <input name="surname" type="text"/>
        Address <input name="address" type="text"/>
        Confirmation code <input name="confirmationCode" type="text"/>
        <button type="submit">Buy</button>
    </form>
    <h3>Total price = ${totalPrice}</h3>
    <form action="/user/sendCode" method="post">
        <button type="submit">Send confirmation code to email</button>
    </form>
    <h2>${wrong}</h2>
    <form action="/user/basket" method="get">
        <button type="submit">Back to basket</button>
    </form>
</div>
</body>
</html>
