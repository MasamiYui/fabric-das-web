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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
     */
    @RequestMapping(value = "/owner/users")  //, method = RequestMethod.POST
    @ResponseBody
    public JSONObject selectUsers(int currentPage, int numberOfPages, String searchString, String searchId) {

        HashMap data = ownerService.selectUsers(currentPage, numberOfPages, searchString, searchId);
        return ResponseUtil.constructResponse(200, "ok", data);
    }


    /**
     * 新增一个用户
     */
    @RequestMapping(value = "/owner/user", method = RequestMethod.PUT)
    @ResponseBody
    public JSONObject addUser(UserVo userVo) {

        int result = ownerService.addUser(userVo);
        if (result == State.SUCCESS) {
            return ResponseUtil.constructResponse(200, "ok", null);
        }
        return ResponseUtil.constructResponse(400, "insert user fail.", null);
    }



    /**
     * 返回用户用户列表页面
     */
    @RequestMapping(value = "/owner/user/index", method = RequestMethod.GET)
    public ModelAndView selectUsers() {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index_userList");
        return modelAndView;
    }


    /**
     * 获取新增用户的表单
     */
    @RequestMapping(value = "/owner/addUser", method = RequestMethod.GET)
    public ModelAndView getUserForm() {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("form_newUser");
        return modelAndView;
    }


    /**
     * 插入一条公司信息
     */
    @RequestMapping(value = "/owner/company", method = RequestMethod.PUT)
    @ResponseBody
    public JSONObject addCompany(CompanyVO companyVO) {

        if (ownerService.addComany(companyVO) == State.SUCCESS) {
            return ResponseUtil.constructResponse(200, "ok", null);
        }
        return ResponseUtil.constructResponse(400, "err", null);
    }

    /**
     * 查询公司列表
     */
    @RequestMapping(value = "/owner/companys", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject selectCompanies(int currentPage, int numberOfPages, String username, String companyName, String creditId, String state) {

        HashMap<String, Object> data = ownerService.selectCompanies(currentPage, numberOfPages, username, companyName, creditId, state);
        return ResponseUtil.constructResponse(200, "ok", data);
    }

    //获取用户记录的详情
    @RequestMapping(value = "/owner/user/{id}", method = RequestMethod.GET)
    public ModelAndView userDetail(@PathVariable String id) {

        List<HashMap> records = ownerService.getRecordDetail(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("records", records);
        modelAndView.setViewName("detail_user");
        return modelAndView;
    }

    //企业列表
    @RequestMapping(value = "/owner/company/index", method = RequestMethod.GET)
    public String getCompanyList() {

        return "index_companyList";
    }


    //企业申请表单页面
    @RequestMapping(value = "/owner/addCompany", method = RequestMethod.GET)
    public String companyForm() {

        return "form_newCompany";
    }

    //获取公司记录的详情
    @RequestMapping(value = "/owner/company/{id}", method = RequestMethod.GET)
    public ModelAndView companyDetail(@PathVariable String id) {

        List<HashMap> records = ownerService.getCompanyDetail(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("records", records);
        modelAndView.setViewName("detail_company");
        return modelAndView;
    }


    //帐号状态设置
    @RequestMapping(value = "/owner/state", method = RequestMethod.GET)
    public String accountState(Model model, String recordId, String ownerType) {

        model.addAttribute("id", recordId);
        model.addAttribute("ownerType", ownerType);
        return "state_setAccount";
    }

    //普通用户帐号修改
    @RequestMapping(value = "/owner/user/state", method = RequestMethod.POST)
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
    @RequestMapping(value = "/owner/company/state", method = RequestMethod.POST)
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