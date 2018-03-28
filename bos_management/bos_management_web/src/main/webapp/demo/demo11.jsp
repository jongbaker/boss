<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<shiro:authenticated>
 当前用户已经授权
</shiro:authenticated>
<hr>
<shiro:hasRole name="admin">
有角色哦
</shiro:hasRole>

<hr>

<shiro:hasPermission name="courier">
有courier权限,授权了
</shiro:hasPermission>
</body>
</html>