<%--
 * 
 * @author Shengzhao Li
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="dis" uri="http://displaytag.sf.net" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="custom" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>Instances</title>

    <style>
        .list-group li:hover {
            background-color: #f9f9f9;
        }
    </style>
</head>
<body>
<%--Alert--%>
<div class="row">
    <div class="col-md-12">
        <div class="alert alert-success" style="display: none;">
            <button type="button" class="close" data-dismiss="alert">&times;</button>
            <div><em class="fui-check-circle"></em>
                <span id="saveInstanceOK" style="display: none;">Save instance successful</span>
                <span id="enableSuccess" style="display: none;">Enable instance heart-beat successful</span>
                <span id="stopSuccess" style="display: none;">Stop instance heart-beat successful</span>
                <span id="deleteSuccess" style="display: none;">Delete instance successful</span>
            </div>
        </div>
        <div class="alert alert-danger" style="display: none;">
            <button type="button" class="close" data-dismiss="alert">&times;</button>
            <div><em class="fui-check-circle"></em>
                <span id="enableFailed" style="display: none;">Fail to enable instance heart-beat monitoring; Please checking server-console what happening...</span>
                <span id="stopFailed" style="display: none;">Fail to stop instance heart-beat monitoring; Please checking server-console what happening...</span>
                <span id="deleteFailed"
                      style="display: none;">Delete instance failed; Make sure it is not-enabled</span>
            </div>
        </div>

    </div>
</div>
<div class="row">
    <div class="col-md-4">
        <form class="form-inline" role="form" action="" id="filterForm">
            <div class="input-group">
                <input type="text" name="instanceName" class="form-control" id="instanceName"
                       placeholder="Instance name" value="${listDto.instanceName}"/>
                <span class="input-group-btn">
                    <button type="submit" class="btn"><i class="fui-search"></i></button>
                </span>
            </div>
            <strong>${listDto.totalSize}</strong> instance(s)
        </form>
    </div>
    <div class="col-md-2">
        &nbsp;
    </div>
    <div class="col-md-4 col-md-offset-2">
        <div class="pull-right">
            <%--<sec:authorize ifAnyGranted="ROLE_CREATE_EDIT_INSTANCE">--%>
            <a href="instance_form.hb" class="btn btn-primary"><i class="fui-plus"></i> New Instance</a>
            <%--</sec:authorize>--%>
        </div>
    </div>
</div>
<div class="row">
    <div class="col-md-12">
        <br/>
        <c:if test="${empty listDto.list}">
            <div class="alert alert-info" role="alert">
                <strong class="fui-info-circle"></strong>
                Not yet add any instances, please click 'New Instance' to add the first instance.
            </div>
        </c:if>
        <c:if test="${not empty listDto.list}">
            <ul class="list-group">
                <c:forEach items="${listDto.list}" var="d" varStatus="sta">
                    <custom:instance_group_item d="${d}"/>
                </c:forEach>
            </ul>
            <%--pagination--%>
            <dis:table list="${listDto}" id="d" form="filterForm" class="table table-striped table-hover hidden">
                <dis:column property="frequency.seconds"/>
            </dis:table>
        </c:if>
    </div>
</div>
<script>
    $(function () {
        new InstanceList('${param.alert}');
    })
</script>
</body>
</html>