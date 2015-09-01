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
    <title>Reminder Log</title>
</head>
<body>

<div class="row">
    <div class="col-md-10">
        <form class="form-inline" role="form" action="" id="filterForm">
            <div class="form-group">
                <select name="instanceGuid" class="form-control">
                    <option value="">All instances</option>
                    <c:forEach items="${listDto.instanceDtos}" var="st">
                        <option value="${st.guid}" ${st.guid eq listDto.instanceGuid?'selected':''}>${st.instanceName}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-group">
                <select name="normal" class="form-control">
                    <option value="-1">All statuses</option>
                    <option value="0" ${listDto.normal eq 0?'selected':''}>Normal -> Un-Normal</option>
                    <option value="1" ${listDto.normal eq 1?'selected':''}>Un-Normal -> Normal</option>
                </select>
            </div>
            <button type="submit" class="btn"><i class="fui-search"></i></button>
            <strong>${listDto.totalSize}</strong> log(s)
        </form>
    </div>
    <div class="col-md-2">
        &nbsp;
    </div>
</div>

<c:if test="${not empty listDto.instanceDto}">
    <custom:log_instance_details d="${listDto.instanceDto}"/>
</c:if>

<div class="row">
    <div class="col-md-12">
        <dis:table list="${listDto}" id="d" form="filterForm" class="table table-striped table-hover">
            <dis:column title="Instance">
                <a href="${contextPath}/monitoring/${d.instanceGuid}.hb">${d.instanceName}</a>
            </dis:column>
            <dis:column title="Reminder Time" property="createTime"/>
            <dis:column title="Status">
                <c:if test="${d.changeNormal}">
                    <em class="fui-checkbox-checked text-success" title="Restore to normal"></em>
                </c:if>
                <c:if test="${not d.changeNormal}">
                    <em class="fui-stumbleupon text-warning" title="Change to un-normal"></em>
                </c:if>
            </dis:column>
            <dis:column title="Email Address">
                <a href="mailto:${d.receiveEmail}" class="text-info">${d.receiveEmail}</a>
                &nbsp;
                <a title="Show Email Content" class="showMailContent" href="javascript:void(0)"><em
                        class="fui-mail"></em></a>

                <div class="hidden">
                        ${d.emailContent}
                </div>
            </dis:column>
        </dis:table>

    </div>
</div>

<script>
    $(function () {
        new ReminderLogs();
    })
</script>
</body>
</html>