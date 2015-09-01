<%--
 * 
 * @author Shengzhao Li
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>${formDto.newly?'New':'Edit'} Instance</title>
</head>
<body>
<div>
    <div class="row">
        <div class="col-md-12">
            <h4>${formDto.newly?'New':'Edit'} Instance</h4>
            <form:form commandName="formDto" cssClass="form-horizontal">
                <div class="form-group">
                    <label for="instanceName" class="col-sm-2 control-label">Instance name</label>

                    <div class="col-sm-8">
                        <form:input path="instanceName" id="instanceName" cssClass="form-control"
                                    placeholder="Input instance name" required="true"/>
                        <p class="help-block">An unique name of the application instance</p>
                        <form:errors path="instanceName" cssClass="text-danger"/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="monitorUrl" class="col-sm-2 control-label">Monitor URL</label>

                    <div class="col-sm-8">
                        <form:input path="monitorUrl" id="monitorUrl" cssClass="form-control"
                                    placeholder="The application monitor url" required="true"/>
                        <p class="help-block">Start with 'http' or 'https', e.g. 'http://andaily.com/test.html'</p>
                        <form:errors path="monitorUrl" cssClass="text-danger"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">Request Method</label>

                    <div class="col-sm-8">
                        <label class="toggle-radio">
                            <input type="radio" name="requestMethod"
                                   value="GET" ${formDto.requestMethod.get?'checked':''}/> GET
                        </label>
                        &nbsp;
                        <label class="toggle-radio">
                            <input type="radio" name="requestMethod"
                                   value="POST" ${formDto.requestMethod.post?'checked':''}/> POST
                        </label>

                        <p class="help-block">Specify the monitor url request method. default: GET</p>
                        <form:errors path="requestMethod" cssClass="text-danger"/>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-sm-2 control-label">Request Params</label>

                    <div class="col-sm-8">
                        <table class="table table-responsive table-condensed">
                            <thead>
                            <tr>
                                <td>Param-Key</td>
                                <td>Param-Value</td>
                                <th>&nbsp;</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${formDto.urlParameters}" var="p" varStatus="s">
                                <tr order="${s.index}">
                                    <td class="col-xs-3">
                                        <form:input path="urlParameters[${s.index}].key"
                                                    cssClass="form-control input-sm" maxlength="20"
                                                    placeholder="Parameter Key"/></td>
                                    <td class="col-xs-6">
                                        <div class="input-group">
                                            <input type="text" name="urlParameters[${s.index}].value"
                                                   value="${p.value}"
                                                   class="form-control input-sm value" ${p.randomValue?'readonly':''}
                                                   maxlength="255" placeholder="Parameter Value"/>
                                        <span class="input-group-addon input-sm">
                                            <input type="checkbox" value="true" class="random"
                                                   name="urlParameters[${s.index}].randomValue" ${p.randomValue?'checked':''}/> Random
                                        </span>
                                        </div>
                                    </td>
                                    <td>
                                        <c:set var="hiddenStyle"
                                               value="${(s.last and formDto.urlParametersSize > 0)?'':'hidden'}"/>
                                        <a href="javascript:void(0);" class="addParam ${hiddenStyle}"
                                           title="Add row"><em
                                                class="fui-plus-circle"></em></a>
                                        <a href="javascript:void(0);" class="deleteParam ${hiddenStyle}"
                                           title="Delete row"><em
                                                class="fui-cross-circle"></em></a>
                                    </td>
                                </tr>
                            </c:forEach>
                            <c:if test="${empty formDto.urlParameters}">
                                <tr order="0">
                                    <td class="col-xs-3"><form:input path="urlParameters[0].key"
                                                                     cssClass="form-control input-sm" maxlength="20"
                                                                     placeholder="Parameter Key"/></td>
                                    <td class="col-xs-6">
                                        <div class="input-group">
                                            <form:input path="urlParameters[0].value" maxlength="255"
                                                        cssClass="form-control input-sm value"
                                                        placeholder="Parameter Value"/>
                                        <span class="input-group-addon input-sm">
                                            <input type="checkbox" value="true" name="urlParameters[0].randomValue"
                                                   class="random"/> Random
                                        </span>
                                        </div>
                                    </td>
                                    <td>
                                        <a href="javascript:void(0);" class="addParam" title="Add row"><em
                                                class="fui-plus-circle"></em></a>
                                        <a href="javascript:void(0);" class="deleteParam hidden" title="Delete row"><em
                                                class="fui-cross-circle"></em></a>
                                    </td>
                                </tr>
                            </c:if>
                            </tbody>
                        </table>

                        <p class="help-block">Specify the monitor url request parameter(s), allow set value is random.
                            (Optional)</p>
                        <form:errors path="urlParameters" cssClass="text-danger"/>
                    </div>
                </div>

                <div class="form-group">
                    <label for="contentType" class="col-sm-2 control-label">Request ContentType</label>

                    <div class="col-sm-8">
                        <form:select path="contentType" id="contentType" cssClass="form-control">
                            <form:option value="">default</form:option>
                            <form:options items="${formDto.contentTypes}" itemLabel="mimeType" itemValue="mimeType"/>
                        </form:select>
                        <p class="help-block">Specify request 'contentType' if the URL need, Optional</p>
                        <form:errors path="contentType" cssClass="text-danger"/>
                    </div>
                </div>

                <div class="form-group">
                    <label for="monitorUrl" class="col-sm-2 control-label">Frequency(s)</label>

                    <div class="col-sm-8">
                        <form:select path="frequency" id="frequency" cssClass="form-control">
                            <form:options items="${formDto.frequencies}" itemLabel="seconds" itemValue="value"/>
                        </form:select>
                        <p class="help-block">The seconds interval between the two requests</p>
                        <form:errors path="frequency" cssClass="text-danger"/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="maxConnectionSeconds" class="col-sm-2 control-label"><abbr
                            title='Max Connection Time(ms)'>Max Conn(s)</abbr></label>

                    <div class="col-sm-8">
                        <form:input path="maxConnectionSeconds" id="maxConnectionSeconds" cssClass="form-control"
                                    placeholder="Input max connection time(ms)" required="true"/>
                        <p class="help-block">Max connection time when send request every time, default max is Frequency
                            seconds</p>
                        <form:errors path="maxConnectionSeconds" cssClass="text-danger"/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="email" class="col-sm-2 control-label">Email</label>

                    <div class="col-sm-8">
                        <form:input path="email" id="email" cssClass="form-control"
                                    placeholder="Input email" required="true"/>
                        <p class="help-block">The email that use when monitoring the application response exception,
                            will send email to the address; More split by (;)</p>
                        <form:errors path="email" cssClass="text-danger"/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="remark" class="col-sm-2 control-label">Remark</label>

                    <div class="col-sm-8">
                        <form:textarea path="remark" id="remark" rows="3"
                                       cssClass="form-control"
                                       placeholder="Remark information of the instance"/>
                        <p class="help-block">More information of the instance; Optional</p>
                        <form:errors path="remark" cssClass="text-danger"/>
                    </div>
                </div>

                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button type="submit" class="btn btn-primary"><em class="fui-check-circle"></em> Save</button>
                        &nbsp;<a href="list.hb">Cancel</a>
                    </div>
                </div>
            </form:form>
        </div>
    </div>
</div>

<script>
    $(function () {
        new InstanceForm();
    });
</script>
</body>
</html>