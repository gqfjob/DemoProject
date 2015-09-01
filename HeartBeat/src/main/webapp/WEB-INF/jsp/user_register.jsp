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
    <title>User Register</title>
</head>
<body>

<div>
    <div class="row">
        <div class="col-md-12">
            <h4>User Register</h4>
            <form:form commandName="formDto" cssClass="form-horizontal">

                <div class="form-group">
                    <label for="username" class="col-sm-2 control-label">Username</label>

                    <div class="col-sm-8">
                        <form:input path="username" id="username" cssClass="form-control"
                                    placeholder="Input username" required="true" maxlength="40"/>
                        <p class="help-block">Unique username</p>
                        <form:errors path="username" cssClass="text-danger"/>
                    </div>
                </div>

                <div class="form-group">
                    <label for="password" class="col-sm-2 control-label">Password</label>

                    <div class="col-sm-8">
                        <form:password path="password" id="password" cssClass="form-control"
                                       placeholder="Input pasword, lenth >= 6" required="true" maxlength="255"/>
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

                <div class="form-group">
                    <label for="email" class="col-sm-2 control-label">Email</label>

                    <div class="col-sm-8">
                        <form:input path="email" id="email" cssClass="form-control"
                                    placeholder="Input email" maxlength="255" required="true"/>
                        <p class="help-block">Your email address, required.</p>
                        <form:errors path="email" cssClass="text-danger"/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="phone" class="col-sm-2 control-label">Phone</label>

                    <div class="col-sm-8">
                        <form:input path="phone" id="phone" cssClass="form-control"
                                    placeholder="Input phone number" maxlength="255"/>
                        <p class="help-block">Your phone number, optional.</p>
                        <form:errors path="phone" cssClass="text-danger"/>
                    </div>
                </div>

                <div class="form-group">
                    <label for="captcha" class="col-sm-2 control-label">Captcha</label>

                    <div class="col-sm-8">
                        <div class="input-group">
                            <input type="text" name="${formDto.captchaKey}" class="form-control"
                                   placeholder="Input captcha" id="captcha" maxlength="10" required="required"/>
                            <span class="input-group-addon input-sm">
                                <img src="${contextPath}/captcha.hb" onclick="this.src = this.src+'?'" alt="Captcha"/>
                            </span>
                        </div>
                        <p class="help-block">Please input captcha, required.</p>
                    </div>
                </div>


                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button type="submit" class="btn btn-primary"><em class="fui-check-circle"></em> Register
                        </button>
                        &nbsp;<a href="${contextPath}/login.hb">Sign in</a>
                    </div>
                </div>
            </form:form>
        </div>
    </div>
</div>

</body>
</html>