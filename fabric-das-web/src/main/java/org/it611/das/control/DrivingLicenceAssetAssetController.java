package org.it611.das.control;

import com.alibaba.fastjson.JSONObject;
import org.it611.das.service.DrivingLicenceAssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DrivingLicenceAssetAssetController {

    @Autowired
    private DrivingLicenceAssetService drivingLicenceAssetService;

    //获取学历证书资产列表页面
    @RequestMapping(value = "/asset/drivingLicence/index", method = RequestMethod.GET)
    public String drivingLicenceIndex(){

        return "index_drivingLicenceAssertList";
    }

    //获取学历证书资产列表
    @RequestMapping(value = "/asset/drivingLicences") /*, method = RequestMethod.GET*/
    @ResponseBody
    public JSONObject drivingLicenceList(int currentPage, int numberOfPages, String certId,String state){

        return drivingLicenceAssetService.drivingLicenceList(currentPage, numberOfPages, certId,state);
    }

    //学位证书对比结果（页面）
    @RequestMapping(value = "/asset/drivingLicence/{id}", method = RequestMethod.GET)
    public ModelAndView drivingLicenceDetail(@PathVariable String id) throws Exception {

        JSONObject result = drivingLicenceAssetService.selectDrivingLicenceDetailById(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("result", result);
        modelAndView.setViewName("compare_drivingLicenceAssertDetail");
        return modelAndView;
    }

    //审核和更改状态
    @RequestMapping(value = "/asset/drivingLicence", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject checkAndChangeState(String id, String state) throws Exception {

        JSONObject resultData = drivingLicenceAssetService.CheckDrivingLicenceAndChangeState(id, state);
        return resultData;
    }


}
