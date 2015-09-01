<%--
 * 
 * @author Shengzhao Li
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>Monitoring [${instanceDto.instanceName}]</title>
</head>
<body>

<div class="row">
    <h4><c:if test="${instanceDto.enabled}"><em class="fui-time text-success"
                                                title="Monitoring"></em></c:if>
        ${instanceDto.instanceName}</h4>

    <div class="col-md-12">
        <div id="chart" style="height:400px"></div>
    </div>
</div>

<div class="row">
    <div class="col-md-6">
        <ul class="list-group">
            <li class="list-group-item">
                Monitor URL: <a href="${instanceDto.monitorUrl}"
                                target="_blank">${instanceDto.monitorUrl}</a> <span
                    class="text-info">[${instanceDto.requestMethod}]</span>
            </li>
            <c:if test="${not empty instanceDto.contentType}">
                <li class="list-group-item">
                    ContentType: <span class="text-info">${instanceDto.contentType}</span>
                </li>
            </c:if>
            <li class="list-group-item">
                Frequency: <span class="text-info">${instanceDto.frequency.seconds}s</span>
            </li>
            <li class="list-group-item">
                <abbr title='Max Connection Time(s)'>Max Conn(s)</abbr>: <span
                    class="text-info">${instanceDto.maxConnectionSeconds}s</span>
            </li>
            <li class="list-group-item">
                Email: <span class="text-info">${instanceDto.email}</span>
            </li>
            <li class="list-group-item">
                Remark: <span class="text-info">${instanceDto.remark}</span>
            </li>
        </ul>
    </div>
    <div class="col-md-6">
        <img src="${contextPath}/resources/images/loading_64.gif" class="img-responsive"/>
        <%--Ajax load data--%>
        <div id="staticsDiv"></div>
    </div>
</div>


<div class="row">
    <div class="col-md-12">
        <div class="text-center">
            <a href="javascript:history.back();" class="btn btn-inverse">Back</a>
        </div>
    </div>
</div>


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

                var option0 = {
                    title:{
                        text:'${instanceDto.instanceName}',
                        subtext:'Frequency: ${instanceDto.frequency.seconds}s  Max-Conn: ${instanceDto.maxConnectionSeconds}s Request-Method: ${instanceDto.requestMethod}'
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
                            data:${instanceDto.categoryData}
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
                            data:${instanceDto.seriesData},
                            markLine:{
                                data:[
                                    {type:'average', name:'Average Conn Time'}
                                ]
                            }
                        }
                    ]
                };

                var myChart0 = ec.init(document.getElementById('chart'));
                myChart0.setOption(option0);
                myChart0.setTheme('macarons');

                var lastLogDate0 = '${instanceDto.lastLogDate}';
                setInterval(function () {
                    $.get("../load_addition_monitor_logs.hb", {lastLogDate:lastLogDate0, guid:'${instanceDto.guid}'}, function (data) {
                        myChart0.addData(eval(data.additionData));
                        lastLogDate0 = data.lastLogDate;
                    });

                }, ${instanceDto.intervalTime});
            }
    );

    new MonitoringInstance('${instanceDto.guid}');
</script>

</body>
</html>