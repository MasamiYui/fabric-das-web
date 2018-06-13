package org.it611.das.control;

import com.alibaba.fastjson.JSONObject;
import org.it611.das.service.SyxxzlAssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SyxxzlAssetController {

    @Autowired
    private SyxxzlAssetService syxxzlAssetService;

    //获取学历证书资产列表页面
    @RequestMapping(value = "/asset/syxxzl/index", method = RequestMethod.GET)
    public String degreeSyxxzlIndex(){

        return "index_syxxzlAssertList";
    }

    //获取学历证书资产列表
    @RequestMapping(value = "/asset/syxxzls", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject syxxzlList(int currentPage, int numberOfPages, String certId,String state){

        return syxxzlAssetService.syxxzlList(currentPage, numberOfPages, certId,state);
    }

    //学位证书对比结果（页面）
    @RequestMapping(value = "/asset/syxxzl/{id}", method = RequestMethod.GET)
    public ModelAndView syxxzlDetail(@PathVariable String id) throws Exception {

        JSONObject result = syxxzlAssetService.selectSyxxzlDetailById(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("result", result);
        modelAndView.setViewName("compare_syxxzlAssertDetail");
        return modelAndView;
    }

    //审核和更改状态
    @RequestMapping(value = "/asset/syxxzl", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject checkAndChangeState(String id, String state) throws Exception {

        JSONObject resultData = syxxzlAssetService.CheckSyxxzlAndChangeState(id, state);
        return resultData;
    }


}
