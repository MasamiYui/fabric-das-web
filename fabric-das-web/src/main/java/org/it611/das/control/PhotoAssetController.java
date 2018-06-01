package org.it611.das.control;

import com.alibaba.fastjson.JSONObject;
import org.hyperledger.fabric.sdk.exception.CryptoException;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.hyperledger.fabric.sdk.exception.TransactionException;
import org.it611.das.service.PhotoAssetService;
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
public class PhotoAssetController {

    @Autowired
    private PhotoAssetService photoAssetService;

    //图片资产首页Index页面
    @RequestMapping("/asset/photo/index")
    public String PhotoAssetIndex(){
        return "photoAssetIndex";
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
        modelAndView.setViewName("photoAssertCompareDetail");
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
