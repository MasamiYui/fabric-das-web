package org.it611.das.control;

import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;
import org.it611.das.service.OwnerService;
import org.it611.das.util.ResponseUtil;
import org.it611.das.util.State;
import org.it611.das.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;

@Controller
public class OwnerController {

    private static Logger logger=Logger.getLogger(OwnerController.class);

    @Autowired
    private OwnerService ownerService;

    /**
     * 查询所有用户
     * @param numberOfPages
     * @param currentPage
     * @return
     */
    @RequestMapping("/owner/user/list")
    @ResponseBody
    public JSONObject selectUsers(int currentPage,int numberOfPages, String searchString,String searchId){

        HashMap data = ownerService.selectUsers(currentPage, numberOfPages,searchString,searchId);
        return ResponseUtil.constructResponse(200,"ok", data);
    }


    /**
     * 新增一个用户
     * @param userVo
     * @return
     */
    @RequestMapping("/owner/user/add")
    @ResponseBody
    public JSONObject addUser(UserVo userVo){

        int result = ownerService.addUser(userVo);
        if(result == State.SUCCESS){
            return ResponseUtil.constructResponse(200,"ok", null);
        }
        return ResponseUtil.constructResponse(400,"insert user fail.", null);
    }

    //进入table.html
    @RequestMapping("/owner/user")
    public ModelAndView selectUsers(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("tables");
        return modelAndView;
    }

}