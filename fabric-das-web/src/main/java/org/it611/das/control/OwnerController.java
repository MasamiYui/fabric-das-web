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

import java.util.HashMap;
import java.util.List;

@Controller
public class OwnerController {

    private static Logger logger=Logger.getLogger(OwnerController.class);

    @Autowired
    private OwnerService ownerService;

    /**
     * 查询所有用户
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @RequestMapping("/owner/user/list")
    @ResponseBody
    public JSONObject selectUsers(int pageNumber, int pageSize){

        List<HashMap> data = ownerService.selectUsers(pageNumber, pageSize);
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

    /**
     * 查询用户通过用户姓名
     * @param pageNumber
     * @param pageSize
     * @param name
     * @return
     */
    @RequestMapping("/owner/user/select/name")
    @ResponseBody
    public JSONObject selectUsersByName(int pageNumber, int pageSize, String name){

        List<HashMap> data = ownerService.selectUsersByName(pageNumber, pageSize, name);
        return ResponseUtil.constructResponse(200,"ok", data);
    }

    /**
     * 查询用户通过用户的身份证ID
     * @param pageNumber
     * @param pageSize
     * @param id
     * @return
     */
    @RequestMapping("/owner/user/select/id")
    @ResponseBody
    public JSONObject selectUsersById(int pageNumber, int pageSize, String id){

        List<HashMap> data = ownerService.selectUsersById(pageNumber, pageSize, id);
        return ResponseUtil.constructResponse(200,"ok", data);
    }


}
