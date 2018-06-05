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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;

@Controller
public class OwnerController {

    private static Logger logger = Logger.getLogger(OwnerController.class);

    @Autowired
    private OwnerService ownerService;

    /**
     * 查询所有用户
     *
     * @param numberOfPages
     * @param currentPage
     * @return
     */
    @RequestMapping("/owner/user/list")
    @ResponseBody
    public JSONObject selectUsers(int currentPage, int numberOfPages, String searchString, String searchId) {

        HashMap data = ownerService.selectUsers(currentPage, numberOfPages, searchString, searchId);
        return ResponseUtil.constructResponse(200, "ok", data);
    }


    /**
     * 新增一个用户
     *
     * @param userVo
     * @return
     */
    @RequestMapping("/owner/user/add")
    @ResponseBody
    public JSONObject addUser(UserVo userVo) {

        int result = ownerService.addUser(userVo);
        if (result == State.SUCCESS) {
            return ResponseUtil.constructResponse(200, "ok", null);
        }
        return ResponseUtil.constructResponse(400, "insert user fail.", null);
    }


    /**
     * 删除一个用户
     *
     * @return
     */
    @RequestMapping("/owner/user/delete")
    @ResponseBody
    public JSONObject deleteUser(String id) {

        int result = ownerService.deleteUser(id);
        if (result == 1) {
            return ResponseUtil.constructResponse(200, "ok", null);
        }
        return ResponseUtil.constructResponse(400, "delete user fail.", null);
    }


    /**
     * 返回用户用户列表页面
     *
     * @return
     */
    @RequestMapping("/owner/user")
    public ModelAndView selectUsers() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index_userList");
        return modelAndView;
    }

    //获取新增用户的表单
    @RequestMapping("/owner/getUserForm")
    public ModelAndView getUserForm() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("form_newUser");
        return modelAndView;
    }


    /**
     * 插入一条公司信息
     *
     * @param companyVO
     * @return
     */
    @RequestMapping("/owner/company/add")
    @ResponseBody
    public JSONObject addCompany(CompanyVO companyVO) {

        if (ownerService.addComany(companyVO) == State.SUCCESS) {
            return ResponseUtil.constructResponse(200, "ok", null);
        }
        return ResponseUtil.constructResponse(400, "err", null);
    }

    /**
     * 查询公司列表
     *
     * @param currentPage
     * @param numberOfPages
     * @return
     */
    @RequestMapping("/owner/company/list")
    @ResponseBody
    public JSONObject selectCompanies(int currentPage, int numberOfPages, String username, String companyName, String creditId, String state) {

        HashMap<String, Object> data = ownerService.selectCompanies(currentPage, numberOfPages, username, companyName, creditId, state);
        return ResponseUtil.constructResponse(200, "ok", data);
    }

    //获取用户记录的详情
    @RequestMapping("/owner/recordDetail")
    public ModelAndView recordDetail(String recordId) {
        List<HashMap> records = ownerService.getRecordDetail(recordId);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("records", records);
        modelAndView.setViewName("detail_user");
        return modelAndView;
    }

    //企业列表
    @RequestMapping("/owner/company")
    public String getCompanyList() {
        return "index_companyList";
    }


    //企业申请表单
    @RequestMapping("/owner/companyForm")
    public String companyForm() {
        return "form_newCompany";
    }

    //获取公司记录的详情
    @RequestMapping("/owner/companyDetail")
    public ModelAndView companyDetail(String recordId) {
        List<HashMap> records = ownerService.getCompanyDetail(recordId);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("records", records);
        modelAndView.setViewName("detail_company");
        return modelAndView;
    }


    //测试=====》帐号状态设置
    @RequestMapping("/accountState")
    public String accountState(Model model, String recordId, String ownerType) {
        model.addAttribute("id", recordId);
        model.addAttribute("ownerType", ownerType);
        return "state_setAccount";
    }

    //普通用户帐号修改
    @RequestMapping("/user/state/change")
    @ResponseBody
    public JSONObject changeUserState(String id,String state) {
       int result= ownerService.changeUserState(id,state);
       if(result == 1){
           JSONObject dataMap=ResponseUtil.constructResponse(200, "ok", null);
           return dataMap;
       }
        return ResponseUtil.constructResponse(400, "ok", null);
    }

    //企业账户帐号修改
    @RequestMapping("/company/state/change")
    @ResponseBody
    public JSONObject changeCompanyState(String id,String state) {
        int result= ownerService.changeCompanyState(id,state);
        if(result == 1){
            JSONObject dataMap=ResponseUtil.constructResponse(200, "ok", null);
            return dataMap;
        }
        return ResponseUtil.constructResponse(400, "ok", null);
    }
}