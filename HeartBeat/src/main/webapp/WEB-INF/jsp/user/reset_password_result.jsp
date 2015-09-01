<%--
 * 
 * @author Shengzhao Li
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fun" uri="http://java.sun.com/jsp/jstl/functions" %>

<div>
    <em class="fui-check-circle"></em>
    Reset password successful!<br/>
    The user[${resetUserPasswordDto.username}] new password is <strong
        class="label label-info">${resetUserPasswordDto.newPassword}</strong>.
</div>