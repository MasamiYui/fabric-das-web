package org.it611.das.control;

import com.alibaba.fastjson.JSONObject;
import org.hyperledger.fabric.sdk.exception.CryptoException;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.hyperledger.fabric.sdk.exception.TransactionException;
import org.it611.das.service.VideoAssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;

@Controller
public class VideoAssetController {

    @Autowired
    private VideoAssetService videoAssetService;


    //Video资产首页Index页面
    @RequestMapping("/asset/video/index")
    public ModelAndView degreeCertificationAssetIndex(){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("videoAssetIndex");
        return modelAndView;
    }

    //获取Video资产列表
    @RequestMapping("/asset/video/list")
    @ResponseBody
    public JSONObject videoAssetList(int currentPage, int numberOfPages, String title){
        return videoAssetService.videoAssetList(currentPage, numberOfPages, title);
    }

    //获取Video详情(页面)
    @RequestMapping("/asset/video/detail/index")
    @ResponseBody
    public ModelAndView degreeCertificationCompareDetail(String id) throws Exception {
        JSONObject result = videoAssetService.videoAssetDetail(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("result", result);
        modelAndView.setViewName("videoAssetCompareDetail");
        return modelAndView;
    }

    //审核和更改状态
    @RequestMapping("/asset/video/checkAndChangeState")
    @ResponseBody
    public JSONObject checkAndChangeState(String id, String state) throws Exception {
        JSONObject resultData = videoAssetService.CheckVideoAssetAndChangeState(id, state);
        return resultData;
    }

}
