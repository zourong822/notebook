<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/3/18
  Time: 15:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"  isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>list</title>
</head>
<body>
    jump success<br/>
    <c:forEach items="${list}" var="account">
        ${account.name}+${account.money}<br>
    </c:forEach>

</body>
</html>
