<%--
 * 
 * @author Shengzhao Li
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<ul class="list-group">
    <li class="list-group-item">
        Normal connection: <span class="text-success">${statisticsDto.normalConnection}</span>
    </li>
    <li class="list-group-item">
        Last can not connection: <a href="../log/list.hb?instanceGuid=${statisticsDto.guid}&normal=2"><span
            class="text-danger">${statisticsDto.lastNotNormalTime}</span></a>
    </li>
    <li class="list-group-item">
        Connections normal/not-normal times: <a href="../log/list.hb?instanceGuid=${statisticsDto.guid}&normal=1"><span
            class="text-info">${statisticsDto.normalAmount}</span></a> / <a
            href="../log/list.hb?instanceGuid=${statisticsDto.guid}&normal=2"><span
            class="text-danger">${statisticsDto.notNormalAmount}</span></a>
    </li>
    <li class="list-group-item">
        Interrupt connection times: <a href="../log/reminder_list.hb?instanceGuid=${statisticsDto.guid}&normal=0"><span
            class="text-info">${statisticsDto.interruptTime}</span></a>
    </li>
    <li class="list-group-item">
        Send reminder times: <a href="../log/reminder_list.hb?instanceGuid=${statisticsDto.guid}&normal=1"><span
            class="text-info">${statisticsDto.sendReminderTime}</span></a>
    </li>
</ul>