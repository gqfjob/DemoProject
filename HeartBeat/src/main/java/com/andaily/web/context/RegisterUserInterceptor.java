package com.andaily.web.context;

import com.andaily.domain.dto.user.UserRegisterDto;
import com.andaily.web.WebUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.springframework.web.bind.ServletRequestUtils.getStringParameter;

/**
 * 15-4-27
 *
 * @author Shengzhao Li
 */
public class RegisterUserInterceptor extends HandlerInterceptorAdapter {


    public RegisterUserInterceptor() {
    }

    /*
    * For validate captcha
    * */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        if (RequestMethod.GET.name().equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        final String correctCaptcha = WebUtils.getCaptchaKey(request.getSession());
        final String captcha = getStringParameter(request, UserRegisterDto.CAPTCHA_KEY);

        if (StringUtils.isEmpty(captcha) || !captcha.equalsIgnoreCase(correctCaptcha)) {
            response.getWriter().print("<script>alert('Invalid Captcha.');history.back();</script>");
            return false;
        }

        return true;
    }

}
