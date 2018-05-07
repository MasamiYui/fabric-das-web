package org.it611.das.control;

import com.alibaba.fastjson.JSONObject;
import org.it611.das.service.OwnerService;
import org.it611.das.util.ResponseUtil;
import org.it611.das.util.State;
import org.it611.das.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;

@Controller
public class OnwerController {

    @Autowired
    private OwnerService ownerService;

    @RequestMapping("/owner/user/list")
    @ResponseBody
    public JSONObject selectUsers(int pageNumber, int pageSize){

        List<HashMap> data = ownerService.selectUsers(pageNumber, pageSize);
        return ResponseUtil.constructResponse(200,"ok", data);
    }


    @RequestMapping("/owner/user/add")
    @ResponseBody
    public JSONObject addUser(UserVo userVo){

        int result = ownerService.addUser(userVo);
        if(result == State.SUCCESS){
            return ResponseUtil.constructResponse(200,"ok", null);
        }
        return ResponseUtil.constructResponse(400,"insert user fail.", null);
    }

}
