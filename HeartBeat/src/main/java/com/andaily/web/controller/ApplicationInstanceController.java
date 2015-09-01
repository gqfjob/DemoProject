package com.andaily.web.controller;

import com.andaily.domain.dto.application.ApplicationInstanceFormDto;
import com.andaily.domain.dto.application.ApplicationInstanceListDto;
import com.andaily.service.ApplicationInstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

/**
 * @author Shengzhao Li
 */
@Controller
@RequestMapping("instance/")
public class ApplicationInstanceController {


    @Autowired
    private ApplicationInstanceService instanceService;

    @RequestMapping("list.hb")
    public String list(ApplicationInstanceListDto listDto, Model model) {
        instanceService.loadApplicationInstanceListDto(listDto);
        model.addAttribute("listDto", listDto);
        return "instance/instance_list";
    }

    /*
    * Enable newly instance
    * */
    @RequestMapping("enable.hb")
    public String enableInstance(@RequestParam String guid, Model model) {
        final boolean result = instanceService.enableApplicationInstance(guid);
        model.addAttribute("alert", result ? "enableSuccess" : "enableFailed");
        return "redirect:list.hb";
    }

    /*
    * Stop monitoring instance
    * */
    @RequestMapping("stop.hb")
    public String stopInstance(@RequestParam String guid, Model model) {
        final boolean result = instanceService.stopMonitoringApplicationInstance(guid);
        model.addAttribute("alert", result ? "stopSuccess" : "stopFailed");
        return "redirect:list.hb";
    }

    /*
    * Delete instance
    * */
    @RequestMapping("delete.hb")
    public String deleteInstance(@RequestParam String guid, Model model) {
        final boolean result = instanceService.deleteApplicationInstance(guid);
        model.addAttribute("alert", result ? "deleteSuccess" : "deleteFailed");
        return "redirect:list.hb";
    }

    /*
   * New/Edit instance
   * */
    @RequestMapping(value = "instance_form.hb", method = RequestMethod.GET)
    public String loadForm(String guid, Model model) {
        ApplicationInstanceFormDto formDto = instanceService.loadApplicationInstanceFormDto(guid);
        model.addAttribute("formDto", formDto);
        return "instance/instance_form";
    }

    @RequestMapping(value = "instance_form.hb", method = RequestMethod.POST)
    public String submitForm(@ModelAttribute("formDto") @Valid ApplicationInstanceFormDto formDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "instance/instance_form";
        }
        instanceService.persistApplicationInstance(formDto);
        model.addAttribute("alert", "saveInstanceOK");
        return "redirect:list.hb";
    }
}