<%--
 * 
 * @author Shengzhao Li
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8"/>
    <c:set var="contextPath" value="${pageContext.request.contextPath}" scope="application"/>

    <meta name="viewport" content="width=device-width,user-scalable=no"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta name="keywords" content="HeartBeat "/>
    <meta name="description" content="HeartBeat "/>
    <meta name="author" content="andaily.com"/>

    <title><decorator:title default=""/> - HeartBeat</title>

    <link href="${contextPath}/resources/flat_ui/css/vendor/bootstrap.min.css" rel="stylesheet"/>
    <link href="${contextPath}/resources/flat_ui/css/flat-ui.min.css" rel="stylesheet"/>
    <link rel="shortcut icon" href="${contextPath}/favicon.ico"/>

    <decorator:head/>

</head>
<body>
<c:set var="notSign" value="${empty SPRING_SECURITY_CONTEXT.authentication.principal.username}"/>

<div class="container" style="margin-top: 10px;">
    <%--navbar--%>
    <div class="row">
        <div class="col-xs-12">
            <nav class="navbar navbar-inverse navbar-embossed" role="navigation">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse"
                            data-target="#navbar-collapse-01">
                        <span class="sr-only">Toggle navigation</span>
                    </button>
                    <a class="navbar-brand" href="${contextPath}">HB</a>
                </div>
                <div class="collapse navbar-collapse" id="navbar-collapse-01">
                    <ul class="nav navbar-nav navbar-left" id="mainMenu">
                        <%--class="active"--%>
                        <li><a href="${contextPath}">Monitoring</a></li>
                        <li class="dropdown" id="instanceMenu">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">Instance <b
                                    class="caret"></b></a>
                            <span class="dropdown-arrow"></span>
                            <ul class="dropdown-menu">
                                <%--<sec:authorize ifAnyGranted="ROLE_CREATE_EDIT_INSTANCE">--%>
                                <li><a href="${contextPath}/instance/instance_form.hb">New Instance</a></li>
                                <%--</sec:authorize>--%>
                                <li><a href="${contextPath}/instance/list.hb">Instances</a></li>
                            </ul>
                        </li>
                        <li class="dropdown" id="logMenu">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">Log <b
                                    class="caret"></b></a>
                            <span class="dropdown-arrow"></span>
                            <ul class="dropdown-menu">
                                <li><a href="${contextPath}/log/list.hb">Monitor Log</a></li>
                                <li><a href="${contextPath}/log/reminder_list.hb">Reminder Log</a></li>
                            </ul>
                        </li>
                        <sec:authorize ifAnyGranted="ROLE_USER_MANAGEMENT,ROLE_SYSTEM_SETTING">
                            <li class="dropdown" id="systemMenu">
                                <a href="#" class="dropdown-toggle" data-toggle="dropdown">System <b
                                        class="caret"></b></a>
                                <span class="dropdown-arrow"></span>
                                <ul class="dropdown-menu">
                                    <sec:authorize ifAnyGranted="ROLE_USER_MANAGEMENT">
                                        <li><a href="${contextPath}/user/list.hb">Users</a></li>
                                    </sec:authorize>
                                    <sec:authorize ifAnyGranted="ROLE_SYSTEM_SETTING">
                                        <li><a href="${contextPath}/system/setting.hb">Setting</a></li>
                                    </sec:authorize>
                                </ul>
                            </li>
                        </sec:authorize>
                        <c:if test="${not notSign}">
                            <%--User actions--%>
                            <li id="userProfileMenu">
                                <a href="${contextPath}/user_profile.hb">${SPRING_SECURITY_CONTEXT.authentication.principal.username}</a>
                            </li>
                        </c:if>
                        <li>
                            <%--sign in/ out --%>
                            <c:if test="${notSign}">
                                <a href="${contextPath}/login.hb" title="Sign in"><em
                                        class="fui-export text-success"></em></a>
                            </c:if>
                            <c:if test="${not notSign}">
                                <a href="${contextPath}/logout"
                                   title="Sign out"><em class="fui-exit text-danger"></em></a>
                            </c:if>
                        </li>
                    </ul>
                    <form class="navbar-form navbar-right" action="${contextPath}/search.hb" role="search">
                        <div class="form-group">
                            <div class="input-group">
                                <input class="form-control" id="navbarInput-01" name="key" type="search"
                                       placeholder="Search"/>
                                <span class="input-group-btn">
                                    <button type="submit" class="btn"><span class="fui-search"></span></button>
                                </span>
                            </div>
                        </div>
                    </form>
                </div>
                <!-- /.navbar-collapse -->
            </nav>
            <!-- /navbar -->
        </div>
    </div>
    <!-- /row -->

    <%--Import js--%>
    <script src="${contextPath}/resources/flat_ui/js/vendor/jquery.min.js"></script>
    <script src="${contextPath}/resources/flat_ui/js/flat-ui.min.js"></script>
    <script src="${contextPath}/resources/js/heart_beat.js"></script>

    <decorator:body/>

    <%--footer--%>
    <div class="row">
        <div class="col-md-12">
            <hr/>
            <div class="pull-right" style="margin-top: -10px;">
                <a target="_blank" href="${projectHome}"
                   style="font-size: 14px;text-decoration: underline;color: #555555;">HeartBeat&nbsp;
                    version: ${currentVersion}</a>
            </div>
        </div>
    </div>
</div>

<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span
                        class="sr-only">Close</span></button>
                <h4 class="modal-title" id="myModalLabel"></h4>
            </div>
            <div class="modal-body">
                <div id="modalContainer"></div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" id="modalConfirmBtn">Confirm</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>

</body>
</html>