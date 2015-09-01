
<%--
 * 
 * @author Shengzhao Li
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8"/>
    <title>Loading... - HeartBeat</title>
</head>
<body>
Loading...
<%
    request.getRequestDispatcher("/index.hb").forward(request, response);
%>
</body>
</html>