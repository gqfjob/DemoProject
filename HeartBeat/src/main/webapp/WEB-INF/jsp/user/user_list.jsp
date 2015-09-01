<%--
 * 
 * @author Shengzhao Li
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fun" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="custom" %>
<%@ taglib prefix="dis" uri="http://displaytag.sf.net" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>Users</title>
</head>
<body>
<%--Alert--%>
<div class="row">
    <div class="col-md-12">
        <div class="alert alert-success" style="display: none;">
            <button type="button" class="close" data-dismiss="alert">&times;</button>
            <div><em class="fui-check-circle"></em>
                <span id="saveUserOK" style="display: none;">Save user successful</span>
                <span id="deleteSuccess" style="display: none;">Delete user successful</span>
            </div>
        </div>
    </div>
</div>

<div class="row">
    <div class="col-md-8">
        <form class="form-inline" role="form" action="" id="filterForm">
            <div class="input-group">
                <input type="text" name="username" value="${listDto.username}" placeholder="Type username"
                       class="form-control"/>
                <span class="input-group-btn">
                    <button type="submit" class="btn"><i class="fui-search"></i></button>
                </span>
            </div>
            <strong>${listDto.totalSize}</strong> user(s)
        </form>
    </div>
    <div class="col-md-4">
        <div class="pull-right">
            <a href="form/create.hb" class="btn btn-primary"><i class="fui-plus"></i> New User</a>
        </div>
    </div>
</div>


<div class="row">
    <div class="col-md-12">
        <dis:table list="${listDto}" id="d" form="filterForm" class="table table-striped table-hover">
            <dis:column title="Username" property="username" class="${d.registerUser?'text-success':''}"/>
            <dis:column title="Phone" property="phone"/>
            <dis:column title="Email" property="email"/>
            <dis:column title="Privileges">
                <ul>
                    <c:forEach items="${d.privileges}" var="p">
                        <li>${p.label}</li>
                    </c:forEach>
                    <c:if test="${d.defaultUser}">
                        <li class="text-warning">All privileges</li>
                    </c:if>
                </ul>
            </dis:column>
            <dis:column title="">
                <a href="javascript:void(0);" guid="${d.guid}" title="Reset password" class="resetPass"
                   confirmText="Are you sure reset the user[${d.username}] password ?"><em
                        class="fui-clip"></em></a>&nbsp;
                <c:if test="${d.defaultUser}" var="du">
                    <em class="fui-lock" title="Default user"></em>
                </c:if>
                <c:if test="${not du}">
                    <a href="form/${d.guid}.hb" title="Edit"><em class="fui-new"></em></a>&nbsp;
                    <a href="javascript:void(0);" guid="${d.guid}" class="deleteUser" title="Delete"
                       confirmText="Are you sure delete user[${d.username}] ?"><em
                            class="fui-cross"></em></a>
                </c:if>
            </dis:column>
        </dis:table>

        <p class="help-block">
            * Blue-green color user is registered.
        </p>
    </div>
</div>

<%--Reset password modal--%>
<div class="modal fade" tabindex="-1" role="dialog" id="resetPassModal" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">Reset Password</h4>
            </div>
            <div class="modal-body">
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>


<script>
    $(function () {
        new UserList('${param.alert}');
    })
</script>

</body>
</html>