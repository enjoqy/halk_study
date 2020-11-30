<%--
  Created by IntelliJ IDEA.
  User: hacker
  Date: 2020/8/5 0005
  Time: 11:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath(); //  path = "/travel"
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; // basePath="http://localhost:8080/travel/"
%>
<html>
<head>
    <title>welcome</title>
</head>
<body>
<h2>这是欢迎页面</h2>
<p>${name}</p>
<p><%=basePath%></p>
<p><%=path%></p>


</body>
</html>
