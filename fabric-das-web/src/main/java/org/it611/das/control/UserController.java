package org.it611.das.control;

import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;
import org.it611.das.service.OwnerService;
import org.it611.das.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

@Controller
public class UserController {

    private static Logger logger = Logger.getLogger(UserController.class);

    @Autowired
    private OwnerService ownerService;

    //查询所有用户信息（普通用户和企业用户），用于图表中的用户选择查询条件
    @RequestMapping("/lineChart/userSelectIndex")
    public String userSelectIndex(){

        return "chart_UserList";
    }


    @RequestMapping("/lineChart/userSelect")
    @ResponseBody
    public JSONObject selectAllUsers(int currentPage, int numberOfPages, String searchString) {
        HashMap data = ownerService.queryAllUsers(currentPage, numberOfPages, searchString);
        return ResponseUtil.constructResponse(200, "ok", data);
    }


}