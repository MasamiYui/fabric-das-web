package org.it611.das.control;

import com.alibaba.fastjson.JSONObject;
import org.it611.das.service.PhotoAssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PhotoAssetController {

    @Autowired
    private PhotoAssetService photoAssetService;

    //图片资产首页Index页面
    @RequestMapping(value = "/asset/photo/index", method = RequestMethod.GET)
    public String photoAssetIndex(){

        return "index_photoAssetList";
    }

    //获取图片资产列表
    @RequestMapping(value = "/asset/photos")  /*, method = RequestMethod.GET*/
    @ResponseBody
    public JSONObject photoAssetList(int currentPage, int numberOfPages, String title,String state){

        return photoAssetService.photoAssetList(currentPage, numberOfPages, title,state);
    }

    //获取图片详情(页面)
    @RequestMapping(value = "/asset/photo/{id}", method = RequestMethod.GET)
    public ModelAndView photoAssetDetail(@PathVariable String id) throws Exception {

        JSONObject result = photoAssetService.photoAssetDetail(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("result", result);
        modelAndView.setViewName("compare_photoAssertDetail");
        return modelAndView;
    }

    //审核和更改状态
    @RequestMapping(value = "/asset/photo", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject checkAndChangeState(String id, String state) throws Exception {

        JSONObject resultData = photoAssetService.CheckPhotoAssetAndChangeState(id, state);
        return resultData;
    }

}
