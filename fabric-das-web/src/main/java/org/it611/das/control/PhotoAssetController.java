package org.it611.das.control;

import com.alibaba.fastjson.JSONObject;
import org.it611.das.service.PhotoAssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PhotoAssetController {

    @Autowired
    private PhotoAssetService photoAssetService;

    //图片资产首页Index页面
    @RequestMapping("/asset/photo/index")
    public String PhotoAssetIndex(){
        return "index_photoAssetList";
    }
    //获取图片资产列表
    @RequestMapping("/asset/photo/list")
    @ResponseBody
    public JSONObject videoAssetList(int currentPage, int numberOfPages, String title,String state){
        return photoAssetService.photoAssetList(currentPage, numberOfPages, title,state);
    }

    //获取图片详情(页面)
    @RequestMapping("/asset/photo/detail/index")
    @ResponseBody
    public ModelAndView degreeCertificationCompareDetail(String id) throws Exception {
        JSONObject result = photoAssetService.photoAssetDetail(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("result", result);
        modelAndView.setViewName("compare_photoAssertDetail");
        return modelAndView;
    }

    //审核和更改状态
    @RequestMapping("/asset/photo/checkAndChangeState")
    @ResponseBody
    public JSONObject checkAndChangeState(String id, String state) throws Exception {
        JSONObject resultData = photoAssetService.CheckPhotoAssetAndChangeState(id, state);
        return resultData;
    }

}
