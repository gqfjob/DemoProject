package com.andaily.web.controller;

import com.andaily.domain.dto.HBSearchDto;
import com.andaily.domain.dto.IndexAdditionInstanceDto;
import com.andaily.domain.dto.IndexDto;
import com.andaily.domain.dto.MonitoringInstanceDto;
import com.andaily.domain.dto.application.InstanceStatisticsDto;
import com.andaily.domain.dto.user.UserProfileDto;
import com.andaily.domain.dto.user.UserRegisterDto;
import com.andaily.infrastructure.CaptchaImageGenerator;
import com.andaily.service.ApplicationInstanceService;
import com.andaily.service.IndexService;
import com.andaily.service.UserService;
import com.andaily.web.WebUtils;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author Shengzhao Li
 */
@Controller
public class IndexController {


    @Autowired
    private IndexService indexService;
    @Autowired
    private ApplicationInstanceService instanceService;
    @Autowired
    private UserService userService;


    @RequestMapping("index.hb")
    public String index(IndexDto indexDto, Model model) {
        indexService.loadIndexDto(indexDto);
        model.addAttribute("indexDto", indexDto);
        return "index";
    }


    @RequestMapping("login.hb")
    public String login(Model model) {
        model.addAttribute("allowRegister", allowUserRegister());
        return "user/login";
    }


    /*
    * Online load addition  monitoring data
    * */
    @RequestMapping("load_addition_monitor_logs.hb")
    public void loadAdditionData(IndexAdditionInstanceDto additionInstanceDto, HttpServletResponse response) {
        indexService.loadIndexAdditionInstanceDto(additionInstanceDto);
        WebUtils.writeJson(response, JSONObject.fromObject(additionInstanceDto));
    }


    /*
   * Monitoring instance
   * */
    @RequestMapping("monitoring/{guid}.hb")
    public String monitoringInstance(@PathVariable("guid") String guid, Model model) {
        MonitoringInstanceDto instanceDto = indexService.loadMonitoringInstanceDto(guid);
        model.addAttribute("instanceDto", instanceDto);
        return "monitoring_instance";
    }

    /*
   * Statics instance  details
   * */
    @RequestMapping("monitoring/statistics/{guid}.hb")
    public String statisticsInstance(@PathVariable("guid") String guid, Model model) {
        InstanceStatisticsDto statisticsDto = instanceService.loadInstanceStatisticsDto(guid);
        model.addAttribute("statisticsDto", statisticsDto);
        return "instance/statistics_instance";
    }

    /*
   * Search
   * */
    @RequestMapping("search.hb")
    public String search(HBSearchDto searchDto, Model model) {
        indexService.loadHBSearchDto(searchDto);
        model.addAttribute("searchDto", searchDto);
        return "search_result";
    }


    /*
   * Load user profile
   * */
    @RequestMapping(value = "user_profile.hb", method = RequestMethod.GET)
    public String showUserProfile(Model model) {
        model.addAttribute("formDto", new UserProfileDto());
        return "user_profile";
    }


    @RequestMapping(value = "user_profile.hb", method = RequestMethod.POST)
    public String submitUserProfile(@ModelAttribute("formDto") @Valid UserProfileDto formDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "user_profile";
        }
        userService.updateUserProfile(formDto);
        model.addAttribute("alert", "updateProfileOK");
        return "redirect:user_profile.hb";
    }


    /*
   * User register
   * */
    @RequestMapping(value = "register.hb", method = RequestMethod.GET)
    public String showRegister(Model model) {
        if (!allowUserRegister()) {
            return "user_register_forbidden";
        }
        model.addAttribute("formDto", new UserRegisterDto());
        return "user_register";
    }


    @RequestMapping(value = "register.hb", method = RequestMethod.POST)
    public String submitRegister(@ModelAttribute("formDto") @Valid UserRegisterDto formDto, BindingResult result) {
        if (!allowUserRegister()) {
            return "user_register_forbidden";
        }
        if (result.hasErrors()) {
            return "user_register";
        }
        userService.registerUser(formDto);
        return "redirect:register_success.hb";
    }

    //register successful
    @RequestMapping(value = "register_success.hb")
    public String successRegister() {
        return "user_register_success";
    }


    private boolean allowUserRegister() {
        return indexService.loadAllowUserRegister();
    }


    /*
    * Generate captcha
    * */
    @RequestMapping("captcha.hb")
    public void writeCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final HttpSession session = request.getSession(true);

        CaptchaImageGenerator captchaImageGenerator = new CaptchaImageGenerator();
        final BufferedImage image = captchaImageGenerator.generate();
        //save to session
        WebUtils.setCaptchaKey(session, captchaImageGenerator.code());

        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/png");

        final ServletOutputStream output = response.getOutputStream();
        ImageIO.write(image, "png", output);
        output.flush();
    }

}