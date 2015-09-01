<%--
 * 
 * @author Shengzhao Li
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>Monitoring</title>
</head>
<body>
<c:if test="${indexDto.employInstances}" var="emptyInstances">
    <div class="row">
        <div class="col-md-12">
            <div class="alert alert-info" role="alert">
                <strong class="fui-info-circle"></strong>
                Not enabled any instance to monitoring yet. Now create the first <a
                    href="${contextPath}/instance/instance_form.hb">new instance</a> or go to <a
                    href="${contextPath}/instance/list.hb">instances</a> enable it.
            </div>
        </div>
    </div>
</c:if>

<c:if test="${not emptyInstances}">
    <div class="row">
        <div class="col-md-10">
            <form class="form-inline" role="form" action="" id="filterForm">
                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">Per show</div>
                        <select name="maxResult" class="form-control">
                            <c:forEach begin="10" end="50" step="10" var="i">
                                <option value="${i}" ${i == indexDto.maxResult?'selected':''}>${i}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <div class="checkbox">
                        <label>
                            <input type="checkbox" id="enabled" ${indexDto.enabled?'checked':''}/>Monitoring
                            <input type="hidden" name="enabled" value="${indexDto.enabled}"/>
                        </label>
                    </div>
                </div>
            </form>
        </div>
        <div class="col-md-2">
            <div class="pull-right">
                <strong>${indexDto.instanceDtos.size()}</strong> instance(s)
            </div>
        </div>
    </div>

    <c:forEach items="${indexDto.instanceDtos}" var="ins" varStatus="s">
        <div class="row">
            <hr/>
            <div class="col-md-12">
                <h4><c:if test="${ins.enabled}"><em class="fui-time text-success" title="Monitoring"></em></c:if>
                    <a href="${contextPath}/monitoring/${ins.guid}.hb">${ins.instanceName}</a>
                    <small><a href="${ins.monitorUrl}" target="_blank" class="text-muted">${ins.monitorUrl}</a>
                        [${ins.requestMethod}]
                    </small>
                </h4>

                <div id="chart${s.index}" style="height:300px"></div>
            </div>
        </div>

    </c:forEach>

    <script src="${contextPath}/resources/js/echarts/echarts.js"></script>
    <script>
        require.config({
            paths:{
                echarts:'${contextPath}/resources/js/echarts'
            }
        });
        require(
                [
                    'echarts',
                    'echarts/chart/bar',
                    'echarts/chart/line'
                ],
                function (ec) {
                    <c:forEach items="${indexDto.instanceDtos}" var="ins" varStatus="s">
                    var option${s.index} = {
                        title:{
                            text:'${ins.instanceName}',
                            subtext:'Frequency: ${ins.frequency.seconds}s  Max-Conn: ${ins.maxConnectionSeconds}s Request-Method: ${ins.requestMethod}'
                        },
                        tooltip:{
                            trigger:'axis'
                        },
                        legend:{
                            data:['Conn Time(ms)']
                        },
                        toolbox:{
                            show:true,
                            feature:{
                                mark:{show:false},
                                dataView:{show:false, readOnly:false},
                                magicType:{show:true, type:['line', 'bar']},
                                restore:{show:true},
                                saveAsImage:{show:true}
                            }
                        },
                        xAxis:[
                            {
                                type:'category',
                                data:${ins.categoryData}
                            }
                        ],
                        yAxis:[
                            {
                                type:'value'
                            }
                        ],
                        series:[
                            {
                                name:'Conn Time(ms)',
                                type:'line',
                                data:${ins.seriesData},
                                markLine:{
                                    data:[
                                        {type:'average', name:'Average Conn Time'}
                                    ]
                                }
                            }
                        ]
                    };

                    var myChart${s.index} = ec.init(document.getElementById('chart${s.index}'));
                    myChart${s.index}.setOption(option${s.index});
                    myChart${s.index}.setTheme('macarons');

                    var lastLogDate${s.index} = '${ins.lastLogDate}';
                    setInterval(function () {
                        $.get("load_addition_monitor_logs.hb", {lastLogDate:lastLogDate${s.index}, guid:'${ins.guid}'}, function (data) {
                            myChart${s.index}.addData(eval(data.additionData));
                            lastLogDate${s.index} = data.lastLogDate;
                        });

                    }, ${ins.intervalTime});
                    </c:forEach>
                }
        );

    </script>
</c:if>

<script>
    $(function () {
        new Index('${param.alert}');
    });
</script>
</body>
</html>