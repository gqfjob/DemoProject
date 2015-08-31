package org.stackbox.dancsic.web.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.stackbox.dancsic.spi.ResultModel;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by stackbox on 15/6/27.
 */

@Controller
@RequestMapping("/api")
public class AuthController {


    /**
     *
     * @param requeset
     * @param params
     *          phone 电话
     *          code 验证码
     *
     * @return
     */

    @RequestMapping("/register")
    @ResponseBody
    public ResultModel register(HttpServletRequest requeset, @RequestBody Map<String, Object> params) {


        return ResultModel.SUCCESS(null);
    }


    @RequestMapping("/signin")
    @ResponseBody
    public ResultModel signin(HttpServletRequest request, @RequestBody Map<String, Object> params) {

        return ResultModel.SUCCESS(null);
    }
}
