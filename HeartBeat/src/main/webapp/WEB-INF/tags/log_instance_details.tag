<%@tag pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="d" required="true" type="com.andaily.domain.dto.application.ApplicationInstanceDto" %>
<div class="row">
    <br/>

    <div class="col-md-12">
        <div class="alert alert-info" role="alert">
            <ul class="list-inline">
                <li>Monitor URL: <a href="${d.monitorUrl}"
                                    target="_blank">${d.monitorUrl}</a></li>
                <li>
                    Frequency: ${d.frequency.seconds}s
                </li>
                <li>
                    <abbr title='Max Connection Time'>Max
                        Conn</abbr>: ${d.maxConnectionSeconds}s
                </li>
                <li>
                    Request Method: ${d.requestMethod}
                </li>
                <c:if test="${not empty d.contentType}">
                    <li>
                        ContentType: ${d.contentType}
                    </li>
                </c:if>
            </ul>
        </div>
    </div>
</div>
