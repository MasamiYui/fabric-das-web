package org.it611.das.control;

import com.alibaba.fastjson.JSONObject;
import org.it611.das.service.VideoAssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class VideoAssetController {

    @Autowired
    private VideoAssetService videoAssetService;


    //Video资产首页Index页面
    @RequestMapping(value = "/asset/video/index", method = RequestMethod.GET)
    public ModelAndView videoAssetIndex(){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index_videoAssetList");
        return modelAndView;
    }

    //获取Video资产列表
    @RequestMapping(value = "/asset/videos")  /* , method = RequestMethod.GET*/
    @ResponseBody
    public JSONObject videoAssetList(int currentPage, int numberOfPages, String title,String state){

        JSONObject result =videoAssetService.videoAssetList(currentPage, numberOfPages, title,state);
        return  result;
    }

    //获取Video详情(页面)
    @RequestMapping(value = "/asset/video/{id}", method = RequestMethod.GET)
    public ModelAndView videoAssetDetail(@PathVariable String id) throws Exception {

        System.out.println(id);
        JSONObject result = videoAssetService.videoAssetDetail(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("result", result);
        modelAndView.setViewName("compare_videoAssetDetail");
        return modelAndView;
    }

    //审核和更改状态
    @RequestMapping(value = "/asset/video", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject checkAndChangeState(String id, String state) throws Exception {

        JSONObject resultData = videoAssetService.CheckVideoAssetAndChangeState(id, state);
        return resultData;
    }

    //视频链接地址播放
    @RequestMapping(value = "/videoPaly", method = RequestMethod.GET)
    public ModelAndView videoPaly(String linkAddress){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("videoUrl", linkAddress);
        modelAndView.setViewName("play_videoAndAudio");
        return modelAndView;
    }

}
