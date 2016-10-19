<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2016/10/18
  Time: 16:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link rel="icon" type="image/x-icon" href="/resources/guo_c.ico">
    <title>MavenDemo</title>
</head>
<body>
    <h1><c:out value = "${name}" /></h1>
    <h2><c:out value = "${mailAddress}" /></h2>
</body>
</html>
