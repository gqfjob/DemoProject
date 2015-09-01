package com.andaily.web.controller;

import com.andaily.domain.dto.user.ResetUserPasswordDto;
import com.andaily.domain.dto.user.UserFormDto;
import com.andaily.domain.dto.user.UserListDto;
import com.andaily.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

/**
 * @author Shengzhao Li
 */
@Controller
@RequestMapping("/user/")
public class UserController {


    @Autowired
    private UserService userService;


    @RequestMapping("list.hb")
    public String list(UserListDto listDto, Model model) {
        userService.loadUserListDto(listDto);
        model.addAttribute("listDto", listDto);
        return "user/user_list";
    }


    @RequestMapping(value = "form/{guid}.hb", method = RequestMethod.GET)
    public String loadForm(@PathVariable("guid") String guid, Model model) {
        UserFormDto formDto = userService.loadUserFormDto(guid);
        model.addAttribute("formDto", formDto);
        return "user/user_form";
    }

    @RequestMapping(value = "form/{guid}.hb", method = RequestMethod.POST)
    public String submitForm(@ModelAttribute("formDto") @Valid UserFormDto formDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "user/user_form";
        }
        userService.persistUserFormDto(formDto);
        model.addAttribute("alert", "saveUserOK");
        return "redirect:../list.hb";
    }

    /*
   * Delete user (logic delete)
   * */
    @RequestMapping(value = "delete/{guid}.hb", method = RequestMethod.POST)
    public void delete(@PathVariable("guid") String guid, HttpServletResponse response) throws Exception {
        userService.deleteUser(guid);
        responseOK(response);
    }

    private void responseOK(HttpServletResponse response) throws IOException {
        response.getWriter().print("ok");
    }


    /*
   * Reset password
   * */
    @RequestMapping(value = "reset_password/{guid}.hb", method = RequestMethod.POST)
    public String resetPassword(@PathVariable("guid") String guid, Model model) throws Exception {
        ResetUserPasswordDto resetUserPasswordDto = userService.resetUserPassword(guid);
        model.addAttribute("resetUserPasswordDto", resetUserPasswordDto);
        return "user/reset_password_result";
    }

}