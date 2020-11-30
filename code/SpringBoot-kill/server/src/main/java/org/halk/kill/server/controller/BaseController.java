package org.halk.kill.server.controller;

import org.apache.commons.lang3.StringUtils;
import org.halk.kill.enums.StatusCode;
import org.halk.kill.response.BaseResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author halk
 * @Date 2020/8/5 0005 11:04
 */
@RequestMapping("/base")
@Controller
public class BaseController {

    private static final Logger log = LoggerFactory.getLogger(BaseController.class);

    @GetMapping("/welcome")
    public String welcome(String name, ModelMap modelMap) {
        if (StringUtils.isBlank(name)) {
            name = "这不是welcome";
        }
        modelMap.put("name", name);
        return "welcome";
    }

    @GetMapping("/response")
    @ResponseBody
    public BaseResponse response(String name){
        if (StringUtils.isBlank(name)) {
            name = "这不是welcome";
        }
        BaseResponse response = new BaseResponse(StatusCode.Success);
        response.setData(name);
        return response;
    }
}
