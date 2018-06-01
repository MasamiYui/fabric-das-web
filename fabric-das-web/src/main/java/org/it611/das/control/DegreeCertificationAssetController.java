package org.it611.das.control;

import com.alibaba.fastjson.JSONObject;
import org.hyperledger.fabric.sdk.exception.CryptoException;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.TransactionException;
import org.it611.das.service.DegreeCertificationAssetService;
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
public class DegreeCertificationAssetController {

    @Autowired
    private DegreeCertificationAssetService degreeCertificationAssetService;

    //获取学历证书资产列表页面
    @RequestMapping("/asset/degreeCertification/index")
    public String degreeCertificationIndex(){
        return "degreeCertificationIndex";
    }

    //获取学历证书资产列表
    @RequestMapping("/asset/degreeCertification/list")
    @ResponseBody
    public JSONObject degreeCertificationList(int currentPage, int numberOfPages, String certId,String state){
        return degreeCertificationAssetService.degreeCertificationList(currentPage, numberOfPages, certId,state);
    }


    //学位证书对比结果（页面）
    @RequestMapping("/degreeCertificationCompareDetail/index")
    public ModelAndView degreeCertificationCompareDetail(String id) throws Exception {
        JSONObject result = degreeCertificationAssetService.selectDegreeCertificationDetailById(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("result", result);
        modelAndView.setViewName("certificateCompareDetail");
        return modelAndView;
    }

    //审核和更改状态
    @RequestMapping("/degreeCertificationCompareDetail/checkAndChangeState")
    @ResponseBody
    public JSONObject checkAndChangeState(String id, String state) throws Exception {
        JSONObject resultData = degreeCertificationAssetService.CheckDegreeCertificationAndChangeState(id, state);
        return resultData;
    }


}
