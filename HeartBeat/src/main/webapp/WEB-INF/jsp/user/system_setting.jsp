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
    <title>System Setting</title>
</head>
<body>
<%--Alert--%>
<div class="row">
    <div class="col-md-12">
        <div class="alert alert-success" style="display: none;">
            <button type="button" class="close" data-dismiss="alert">&times;</button>
            <div><em class="fui-check-circle"></em>
                <span id="updateSettingOK" style="display: none;">Save system setting successful</span>
            </div>
        </div>
    </div>
</div>

<div>
    <div class="row">
        <div class="col-md-12">
            <h4>System Setting</h4>
            <form:form commandName="formDto" cssClass="form-horizontal">
                <div class="form-group">
                    <label for="allowUserRegister" class="col-sm-2 control-label">Allow User Register</label>

                    <div class="col-sm-8">
                        <form:checkbox path="allowUserRegister" id="allowUserRegister"/>
                        <p class="help-block">
                            Whether allow user register; If allow then the user have privileges as below: <br/>[<em>Create/Edit
                            instance, Delete instance, Start/Stop monitoring instance</em>]
                        </p>
                        <form:errors path="allowUserRegister" cssClass="text-danger"/>
                    </div>
                </div>


                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button type="submit" class="btn btn-primary"><em class="fui-check-circle"></em> Save
                        </button>
                    </div>
                </div>
            </form:form>
        </div>
    </div>
</div>

<script>
    $(function () {
        new UserProfile('${param.alert}');
    });
</script>
</body>
</html>