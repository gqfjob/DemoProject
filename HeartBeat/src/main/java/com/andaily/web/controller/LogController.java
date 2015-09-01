package com.andaily.web.controller;

import com.andaily.domain.dto.log.FrequencyMonitorLogListDto;
import com.andaily.domain.dto.log.ReminderLogListDto;
import com.andaily.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 15-2-13
 *
 * @author Shengzhao Li
 */
@Controller
@RequestMapping("log/")
public class LogController {


    @Autowired
    private LogService logService;


    @RequestMapping("list.hb")
    public String listLogs(FrequencyMonitorLogListDto listDto, Model model) {
        logService.loadFrequencyMonitorLogListDto(listDto);
        model.addAttribute("listDto", listDto);
        return "log/log_list";
    }


    @RequestMapping("reminder_list.hb")
    public String reminderLogs(ReminderLogListDto listDto, Model model) {
        logService.loadReminderLogListDto(listDto);
        model.addAttribute("listDto", listDto);
        return "log/reminder_logs";
    }

}
