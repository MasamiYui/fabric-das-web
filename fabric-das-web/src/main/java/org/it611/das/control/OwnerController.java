package org.it611.das.control;

import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;
import org.it611.das.service.OwnerService;
import org.it611.das.util.ResponseUtil;
import org.it611.das.util.State;
import org.it611.das.vo.CompanyVO;
import org.it611.das.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;

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


    /**
     * 删除一个用户
     * @return
     */
    @RequestMapping("/owner/user/delete")
    @ResponseBody
    public JSONObject deleteUser(String id){

        int result = ownerService.deleteUser(id);
        if(result == 1){
            return ResponseUtil.constructResponse(200,"ok", null);
        }
        return ResponseUtil.constructResponse(400,"delete user fail.", null);
    }


    /**
     * 返回用户用户列表页面
     * @return
     */
    @RequestMapping("/owner/user")
    public ModelAndView selectUsers(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("tables");
        return modelAndView;
    }

    //测试获取新增用户的表单
    @RequestMapping("/owner/getUserForm")
    public ModelAndView getUserForm(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("userForm");
        return modelAndView;
    }


    /**
     * 插入一条公司信息
     * @param companyVO
     * @return
     */
    @RequestMapping("/owner/company/add")
    @ResponseBody
    public JSONObject addCompany(CompanyVO companyVO) {

        if(ownerService.addComany(companyVO) == State.SUCCESS){
            return ResponseUtil.constructResponse(200,"ok", null);
        }
        return ResponseUtil.constructResponse(400,"insert user fail.", null);
    }

    /**
     * 查询公司列表
     * @param currentPage
     * @param numberOfPages
     * @param searchString
     * @param searchId
     * @return
     */
    @RequestMapping("/owner/company/list")
    @ResponseBody
    public JSONObject selectCompanies(int currentPage,int numberOfPages, String searchString,String searchId){

        HashMap<String, Object> data = ownerService.selectCompanies(currentPage,numberOfPages, searchString, searchId);
        return ResponseUtil.constructResponse(200,"ok", data);
    }


}