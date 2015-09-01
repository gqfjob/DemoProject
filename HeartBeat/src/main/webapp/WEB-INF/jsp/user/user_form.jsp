<%--
 * 
 * @author Shengzhao Li
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fun" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>${formDto.newly?'New':'Edit'} User</title>
</head>
<body>
<div>
    <div class="row">
        <div class="col-md-12">
            <h4>${formDto.newly?'New':'Edit'} User</h4>
            <form:form commandName="formDto" cssClass="form-horizontal">
                <form:hidden path="existUsername"/>
                <div class="form-group">
                    <label for="username" class="col-sm-2 control-label">Username</label>

                    <div class="col-sm-8">
                        <form:input path="username" id="username" cssClass="form-control" autocomplete="off"
                                    placeholder="Input username" required="true" maxlength="255"/>
                        <p class="help-block">Unique username</p>
                        <form:errors path="username" cssClass="text-danger"/>
                    </div>
                </div>
                <c:if test="${formDto.newly}">
                    <div class="form-group">
                        <label for="password" class="col-sm-2 control-label">Password</label>

                        <div class="col-sm-8">
                            <form:password path="password" id="password" cssClass="form-control"
                                           placeholder="Input pasword" required="true" maxlength="255"/>
                            <p class="help-block">Password length >= 6</p>
                            <form:errors path="password" cssClass="text-danger"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="rePassword" class="col-sm-2 control-label">Re-Password</label>

                        <div class="col-sm-8">
                            <form:password path="rePassword" id="rePassword" cssClass="form-control"
                                           placeholder="Input pasword again" required="true" maxlength="255"/>
                            <p class="help-block">Input the password again</p>
                            <form:errors path="rePassword" cssClass="text-danger"/>
                        </div>
                    </div>
                </c:if>
                <div class="form-group">
                    <label for="email" class="col-sm-2 control-label">Email</label>

                    <div class="col-sm-8">
                        <form:input path="email" id="email" cssClass="form-control"
                                    placeholder="Input email" maxlength="255"/>
                        <p class="help-block">The user email address, option.</p>
                        <form:errors path="email" cssClass="text-danger"/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="phone" class="col-sm-2 control-label">Phone</label>

                    <div class="col-sm-8">
                        <form:input path="phone" id="phone" cssClass="form-control"
                                    placeholder="Input phone number" maxlength="255"/>
                        <p class="help-block">The user phone number, option.</p>
                        <form:errors path="phone" cssClass="text-danger"/>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-sm-2 control-label">Privilege</label>

                    <div class="col-sm-8">
                        <c:forEach items="${formDto.allPrivileges}" var="p">
                            <label class="toggle-checkbox">
                                <input type="checkbox" name="privileges"
                                       value="${p.value}" ${fun:contains(formDto.privileges, p)?'checked':''}/> ${p.label}
                            </label>
                        </c:forEach>
                        <p class="help-block">The user privileges, at least checked one privilege.</p>
                        <form:errors path="privileges" cssClass="text-danger"/>
                    </div>
                </div>

                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button type="submit" class="btn btn-primary"><em class="fui-check-circle"></em> Save
                        </button>
                        &nbsp;<a href="../list.hb">Cancel</a>
                    </div>
                </div>
            </form:form>
        </div>
    </div>
</div>

</body>
</html>