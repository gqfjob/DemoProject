<%@tag pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ attribute name="d" required="true" type="com.andaily.domain.dto.application.ApplicationInstanceDto" %>
<li class="list-group-item">
    <div class="pull-right">
        <c:if test="${not d.enabled}">
            <sec:authorize ifAnyGranted="ROLE_START_STOP_INSTANCE">
                <a href="${contextPath}/instance/enable.hb?guid=${d.guid}" title="Start Monitoring"
                   onclick="return confirm('Enable heart-beat by instance[${d.instanceName}] now ?')"><em
                        class="fui-play"></em></a>
                &nbsp;
            </sec:authorize>
            <sec:authorize ifAnyGranted="ROLE_CREATE_EDIT_INSTANCE">
                <a href="${contextPath}/instance/instance_form.hb?guid=${d.guid}" title="Edit"><em class="fui-new"></em></a>
                &nbsp;
            </sec:authorize>
            <sec:authorize ifAnyGranted="ROLE_DELETE_INSTANCE">
                <a href="${contextPath}/instance/delete.hb?guid=${d.guid}" title="Delete"
                   onclick="return confirm('Are you sure delete the instance[${d.instanceName}] (include heart-beat logs) ?')"><em
                        class="fui-cross"></em></a>
            </sec:authorize>
        </c:if>
        <c:if test="${d.enabled}">
            <a href="${contextPath}/monitoring/${d.guid}.hb" title="Monitoring"><em
                    class="fui-time"></em></a>
            &nbsp;
            <a href="${contextPath}/log/list.hb?instanceGuid=${d.guid}" title="Log"><em
                    class="fui-list"></em></a>
            &nbsp;
            <sec:authorize ifAnyGranted="ROLE_START_STOP_INSTANCE">
                <a href="${contextPath}/instance/stop.hb?guid=${d.guid}" title="Stop"
                   onclick="return confirm('Stop heart-beat by instance[${d.instanceName}] now ?')"><em
                        class="fui-pause"></em></a>
            </sec:authorize>
        </c:if>
    </div>

    <h4 class="list-group-item-heading">
        <c:if test="${d.enabled}">
            <em class="fui-time text-success" title="Monitoring"></em>
        </c:if>
        ${d.instanceName}
        <small><a href="${d.monitorUrl}" target="_blank">${d.monitorUrl}</a></small>
    </h4>

    <div class="list-group-item-text text-muted">
        Frequency: <span class="text-info">${d.frequency.seconds}s</span>&nbsp;
        Max Connection Time: <span class="text-info">${d.maxConnectionSeconds}s</span>&nbsp;
        Request Method: <span class="text-info">${d.requestMethod}</span>&nbsp;
        <c:if test="${not empty d.contentType}">ContentType: <span class="text-info">${d.contentType}</span></c:if>
        <br/>
        Creator: <span class="text-info">${d.creatorName}</span>&nbsp;
        Email: <a href="mailto:${d.email}" class="text-info">${d.email}</a>
        <br/>
        Remark: <a class="text-info">${d.remark}</a>
    </div>
</li>
