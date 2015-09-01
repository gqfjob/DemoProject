package com.andaily.service.operation;

import com.andaily.domain.log.FrequencyMonitorLog;
import com.andaily.infrastructure.DateUtils;

import java.util.List;

/**
 * @author Shengzhao Li
 */
public class MonitoringChartDataGenerator {

    private static final String TIME_FORMAT = "HH:mm:ss";
    private static final String DATA_PREFIX = "[";
    private static final String DATA_SUFFIX = "]";

    private static final String SINGLE_QUOTATION = "'";
    private static final String COMMA = ",";
    private static final String TRUE_FALSE = "true, false,";

    private static final String NOT_NORMAL_CATEGORY_TEMPLATE = "{value:'%s', textStyle:{color:'red'}}";
    private static final String NOT_NORMAL_SERIES_TEMPLATE = " {value:%s, symbolSize:3, itemStyle:{normal:{color:'red'}}}";

    private static final String ADDITION_COST_TIME_TEMPLATE = " {value:%s, symbolSize:3, itemStyle:{normal:{color:'red'}}}";
    private static final String ADDITION_LOG_TIME_TEMPLATE = " {value:'%s', textStyle:{color:'red'}}";


    private List<FrequencyMonitorLog> monitorLogs;

    public MonitoringChartDataGenerator(List<FrequencyMonitorLog> monitorLogs) {
        this.monitorLogs = monitorLogs;
    }

    /**
     * ['11:19:02', '11:20:02', '11:21:02', '11:22:02', '11:23:02', '11:24:02', {value:'11:25:02', textStyle:{color:'red'}}, '11:26:08', '11:27:00', '11:28:00', '11:29:00', '11:30:00', '11:31:00', '11:32:00',
     * '11:33:00', '11:34:00', '11:35:00', '11:36:00', '11:37:00', '11:38:00']
     *
     * @return Category Data in chart
     */
    public String generateCategoryData() {
        StringBuilder sb = new StringBuilder(DATA_PREFIX);

        for (int i = 0; i < monitorLogs.size(); i++) {
            generateSingleCategoryData(monitorLogs.get(i), sb);
            if (!isLastMonitorLog(i)) {
                sb.append(COMMA);
            }
        }

        return sb.append(DATA_SUFFIX).toString();
    }

    /*
    * Normal: 11:20:02'
    * Not-Normal: {value:'11:25:02', textStyle:{color:'red'}}
    * */
    private void generateSingleCategoryData(FrequencyMonitorLog monitorLog, StringBuilder sb) {
        String time = formatLogTime(monitorLog);
        if (monitorLog.normal()) {
            sb.append(SINGLE_QUOTATION).append(time).append(SINGLE_QUOTATION);
        } else {
            sb.append(String.format(NOT_NORMAL_CATEGORY_TEMPLATE, time));
        }
    }

    private boolean isLastMonitorLog(int index) {
        return index == monitorLogs.size() - 1;
    }

    /**
     * [2259, 2258, 2259, 2260, 2259, 2259, {value:2259, symbolSize:3, itemStyle:{normal:{color:'red'}}}, 8178, 372, 123, 122, 125, 121, 125, 123, 120, 124, 124, 408, 123]
     *
     * @return Series Data in chart
     */
    public String generateSeriesData() {
        StringBuilder sb = new StringBuilder(DATA_PREFIX);

        for (int i = 0; i < monitorLogs.size(); i++) {
            generateSingleSeriesData(monitorLogs.get(i), sb);
            if (!isLastMonitorLog(i)) {
                sb.append(COMMA);
            }
        }

        return sb.append(DATA_SUFFIX).toString();
    }

    /*
    * Normal:  2259
    * Not-Normal:  {value:2259, symbolSize:3, itemStyle:{normal:{color:'red'}}}
    * */
    private void generateSingleSeriesData(FrequencyMonitorLog monitorLog, StringBuilder sb) {
        final long costTime = monitorLog.costTime();
        if (monitorLog.normal()) {
            sb.append(costTime);
        } else {
            sb.append(String.format(NOT_NORMAL_SERIES_TEMPLATE, costTime));
        }
    }

    private String formatLogTime(FrequencyMonitorLog monitorLog) {
        return DateUtils.toDateText(monitorLog.createTime(), TIME_FORMAT);
    }

    /**
     * [
     * [0, {value:254, symbolSize:3, itemStyle:{normal:{color:'red'}}}, true, false, {value:'12:23:23', textStyle:{color:'red'}}],
     * [1, 253, true, false, '23:23:21']
     * ]
     *
     * @return Addition Data
     */
    public String generateAdditionData() {
        StringBuilder sb = new StringBuilder(DATA_PREFIX);

        for (int i = 0; i < monitorLogs.size(); i++) {
            generateSingleAdditionData(monitorLogs.get(i), sb, i);
            if (!isLastMonitorLog(i)) {
                sb.append(COMMA);
            }
        }

        return sb.append(DATA_SUFFIX).toString();
    }


    /*
    * Normal:  [1, 253, true, false, '23:23:21']
    * Not-Normal:  [0, {value:254, symbolSize:3, itemStyle:{normal:{color:'red'}}}, true, false, {value:'12:23:23', textStyle:{color:'red'}}]
    * */
    private void generateSingleAdditionData(FrequencyMonitorLog monitorLog, StringBuilder sb, int i) {
        sb.append(DATA_PREFIX).append(i).append(COMMA);

        final String logTime = formatLogTime(monitorLog);
        final long costTime = monitorLog.costTime();

        if (monitorLog.normal()) {
            sb.append(costTime).append(COMMA).append(TRUE_FALSE).append(SINGLE_QUOTATION).append(logTime).append(SINGLE_QUOTATION);
        } else {
            sb.append(String.format(ADDITION_COST_TIME_TEMPLATE, costTime)).append(COMMA).append(TRUE_FALSE)
                    .append(String.format(ADDITION_LOG_TIME_TEMPLATE, logTime));
        }
        sb.append(DATA_SUFFIX);
    }
}