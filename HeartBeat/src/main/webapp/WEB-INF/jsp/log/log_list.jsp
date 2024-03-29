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
    <title>Monitor Log</title>
</head>
<body>
<%--Alert--%>
<%--<div class="row">--%>
<%--<div class="col-md-12">--%>
<%--<div class="alert alert-success" style="display: none;">--%>
<%--<button type="button" class="close" data-dismiss="alert">&times;</button>--%>
<%--<div><em class="fui-check-circle"></em>--%>
<%--<span id="saveInstanceOK" style="display: none;">Save instance successful</span>--%>
<%--</div>--%>
<%--</div>--%>
<%----%>
<%--</div>--%>
<%--</div>--%>
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
                    <option value="0">All statuses</option>
                    <option value="1" ${listDto.normal eq 1?'selected':''}>Normal</option>
                    <option value="2" ${listDto.normal eq 2?'selected':''}>Not normal</option>
                </select>
            </div>
            <div class="form-group">
                <div class="input-group">
                    <div class="input-group-addon">order by</div>
                    <select name="orderItem" class="form-control">
                        <c:forEach items="${listDto.orderItems}" var="item">
                            <option value="${item.value}" ${item eq listDto.orderItem?'selected':''}>${item.label}</option>
                        </c:forEach>
                    </select>
                </div>
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
        <table class="table table-striped table-hover">
            <thead>
            <tr>
                <th>Instance</th>
                <th>Log Time</th>
                <th><abbr title='Connection Time(ms)'>Conn Time(ms)</abbr></th>
                <th><abbr title='Response Data Size(KB)'>Resp Size(KB)</abbr></th>
                <th>Remark</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${listDto.listGroupResults}" var="gro">
                <tr>
                    <td colspan="5">
                        <strong class="text-info">${gro.key}</strong>
                    </td>
                </tr>
                <c:forEach items="${gro.results}" var="d">
                    <tr class="${d.normal?'':'text-danger'}">
                        <td><a href="${contextPath}/monitoring/${d.instanceGuid}.hb">${d.instanceName}</a></td>
                        <td>${d.createTime}</td>
                        <td>${d.costTime}</td>
                        <td>${d.responseSizeAsKB}</td>
                        <td>
                            <custom:substring_remark d="${d}"/>
                        </td>
                    </tr>
                </c:forEach>
            </c:forEach>
            </tbody>
        </table>


        <dis:table list="${listDto}" id="d" form="filterForm" class="table hidden">
            <dis:column property="guid"/>
        </dis:table>
    </div>
</div>

</body>
</html>