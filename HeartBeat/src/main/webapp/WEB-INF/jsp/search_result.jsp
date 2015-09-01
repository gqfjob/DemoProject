<%--
 * 
 * @author Shengzhao Li
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="dis" uri="http://displaytag.sf.net" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="custom" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>Search by [${searchDto.key}]</title>

    <style>
        .list-group li:hover {
            background-color: #f9f9f9;
        }
    </style>
</head>
<body>

<div class="row">
    <div class="col-md-12">
        <form class="form-inline" role="form" action="" id="filterForm">
            <div class="input-group">
                <input type="text" name="key" value="${searchDto.key}" class="form-control" placeholder="Search"/>
                 <span class="input-group-btn">
                       <button type="submit" class="btn"><span class="fui-search"></span></button>
                  </span>
            </div>
            <input type="hidden" id="searchType" name="searchType" value="${searchDto.searchType}"/>
            Total: <strong>${searchDto.totalSize}</strong>
        </form>
    </div>
</div>

<div class="row" style="margin-top: 10px;">
    <div class="col-md-12">
        <ul class="nav nav-tabs">
            <c:forEach items="${searchDto.searchTypes}" var="t">
                <li role="presentation" class="${t eq searchDto.searchType?'active':''}"><a
                        href="javascript:void(0);" sType=${t}>${t.label}</a>
                </li>
            </c:forEach>
        </ul>
        <div style="margin-top: 5px;">
            <%--instance--%>
            <c:if test="${searchDto.searchType.instance}">
                <c:if test="${empty searchDto.list}">
                    <div class="well well-sm text-muted">Search result is empty.</div>
                </c:if>
                <c:if test="${not empty searchDto.list}">
                    <ul class="list-group">
                        <c:forEach items="${searchDto.list}" var="d">
                            <custom:instance_group_item d="${d.instanceDto}"/>
                        </c:forEach>
                    </ul>
                    <%--pagination--%>
                    <dis:table list="${searchDto}" id="d" form="filterForm"
                               class="table table-striped table-hover hidden">
                        <dis:column property="guid"/>
                    </dis:table>
                </c:if>
            </c:if>

            <%--monitorLog--%>
            <c:if test="${searchDto.searchType.monitorLog}">
                <dis:table list="${searchDto}" id="d" form="filterForm" class="table table-striped table-hover">
                    <dis:column title="Instance" class="${d.monitorLogDto.normal?'':'text-danger'}">
                        <a href="${contextPath}/monitoring/${d.monitorLogDto.instanceGuid}.hb">${d.monitorLogDto.instanceName}</a>
                    </dis:column>
                    <dis:column title="Log Time" property="monitorLogDto.createTime"
                                class="${d.monitorLogDto.normal?'':'text-danger'}"/>
                    <dis:column title="<abbr title='Connection Time(ms)'>Conn Time(ms)</abbr>"
                                property="monitorLogDto.costTime"
                                class="${d.monitorLogDto.normal?'':'text-danger'}"/>
                    <dis:column title="<abbr title='Response Data Size(KB)'>Resp Size(KB)</abbr>"
                                property="monitorLogDto.responseSizeAsKB"
                                class="${d.monitorLogDto.normal?'':'text-danger'}"/>
                    <dis:column title="Remark" class="${d.monitorLogDto.normal?'':'text-danger'}">
                        <custom:substring_remark d="${d.monitorLogDto}"/>
                    </dis:column>
                </dis:table>
            </c:if>
        </div>
    </div>
</div>

<script>
    $(function () {
        new SearchResult();
    });
</script>
</body>
</html>