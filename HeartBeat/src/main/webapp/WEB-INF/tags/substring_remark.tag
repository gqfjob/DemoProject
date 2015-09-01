<%@tag pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fun" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ attribute name="d" required="true" type="com.andaily.domain.dto.log.FrequencyMonitorLogDto" %>

<c:if test="${fun:length(d.remark) > 25}" var="subLength">
    <span title="${d.remark}">${fun:substring(d.remark,0, 25)}...</span>
</c:if>
<c:if test="${not subLength}">
    ${d.remark}
</c:if>
