package org.it611.das.control;

import com.alibaba.fastjson.JSONObject;
import org.hyperledger.fabric.sdk.exception.CryptoException;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.hyperledger.fabric.sdk.exception.TransactionException;
import org.it611.das.service.MusicAssetService;
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
public class MusicAssetController {

    @Autowired
    private MusicAssetService musicAssetService;


    //Music资产首页Index页面
    @RequestMapping("/asset/music/index")
    public ModelAndView degreeCertificationAssetIndex(){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("musicAssetIndex");
        return modelAndView;
    }

    //获取Music资产列表
    @RequestMapping("/asset/music/list")
    @ResponseBody
    public JSONObject musicAssetList(int currentPage, int numberOfPages, String title){
        return musicAssetService.musicAssetList(currentPage, numberOfPages, title);
    }

    //获取Music详情(页面)
    @RequestMapping("/asset/music/detail/index")
    @ResponseBody
    public ModelAndView degreeCertificationCompareDetail(String id) throws Exception {
        JSONObject result = musicAssetService.musicAssetDetail(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("result", result);
        modelAndView.setViewName("musicAssetCompareDetail");
        return modelAndView;
    }

    //审核和更改状态
    @RequestMapping("/asset/music/checkAndChangeState")
    @ResponseBody
    public JSONObject checkAndChangeState(String id, String state) throws Exception {
        JSONObject resultData = musicAssetService.CheckMusicAssetAndChangeState(id, state);
        return resultData;
    }

}
