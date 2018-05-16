package org.it611.das.control;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.it611.das.service.UserService;
import org.it611.das.util.CookieUtil;
import org.it611.das.vo.CompanyVO;
import org.it611.das.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

@Controller
public class UserController {

    @Autowired
    public UserService userService;


    @RequestMapping(value = "/user/login")
    @ResponseBody
    public JSONObject userLogin(@RequestParam("loginStr") String loginStr, @RequestParam("password")String password, HttpServletResponse response) throws JsonProcessingException {

        JSONObject result = userService.userLogin(loginStr, password);
        if((int)result.get("code") == 400) {
            return result;
        }
        String token = result.get("data").toString();
        CookieUtil.addCookie(response,CookieUtil.COOKIE_TOKEN_KEY, token, CookieUtil.COOKIE_MAX_AGE);
        return result;
    }

    @RequestMapping(value = "/company/login")
    @ResponseBody
    public JSONObject companyLogin(@RequestParam("loginStr") String loginStr, @RequestParam("password")String password, HttpServletResponse response) throws JsonProcessingException {

        JSONObject result = userService.companyLogin(loginStr, password);
        if((int)result.get("code") == 400) {
            return result;
        }
        String token = result.get("data").toString();
        CookieUtil.addCookie(response,CookieUtil.COOKIE_TOKEN_KEY, token, CookieUtil.COOKIE_MAX_AGE);
        return result;
    }



    @RequestMapping(value = "/user/register")
    @ResponseBody
    public JSONObject userRegister(UserVO userVO){

        JSONObject result = userService.userRegister(userVO);
        return result;
    }

    @RequestMapping(value = "/company/register")
    @ResponseBody
    public JSONObject companyRegister(CompanyVO companyVO){

        JSONObject result = userService.companyRegister(companyVO);
        return result;
    }


    @RequestMapping(value = "/login/index")
    public ModelAndView loginIndex() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }


    @RequestMapping(value = "/index")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        return modelAndView;
    }


}
