package org.it611.das.control;

import com.alibaba.fastjson.JSONObject;
import org.hyperledger.fabric.sdk.Channel;
import org.hyperledger.fabric.sdk.exception.CryptoException;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.hyperledger.fabric.sdk.exception.TransactionException;
import org.it611.das.fabric.ChaincodeManager;
import org.it611.das.fabric.util.FabricManager;
import org.it611.das.service.RecoveryService;
import org.it611.das.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;

@Controller
public class RecoveryController {

    @Autowired
    private RecoveryService recoveryService;

    @RequestMapping(value = "/recovery/index", method = RequestMethod.GET)
    public ModelAndView dataRecovery() throws IOException, NoSuchAlgorithmException, InvocationTargetException, NoSuchMethodException, InstantiationException, InvalidKeySpecException, CryptoException, InvalidArgumentException, IllegalAccessException, NoSuchProviderException, TransactionException, ClassNotFoundException, ProposalException {

        Channel channel = FabricManager.obtain().getManager().getChannelInstance();
        long high = channel.queryBlockchainInfo().getHeight();//区块链高度
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("data_recoveryAndBak");
        modelAndView.addObject("blockchainHigh", high);
        return modelAndView;
    }


    @RequestMapping(value = "/recovery/do", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject doRecovery(String startBlock, String endBlock, String password) throws IOException, NoSuchAlgorithmException, InvocationTargetException, NoSuchMethodException, InstantiationException, InvalidKeySpecException, CryptoException, InvalidArgumentException, IllegalAccessException, NoSuchProviderException, TransactionException, ClassNotFoundException, ProposalException {

        System.out.println(startBlock);
        System.out.println(endBlock);
        System.out.println(password);
        if(!"123456".equals(password)){//如果输入的密码不正确，直接返回
            return ResponseUtil.constructResponse(400, "密码错误，无法提交任务", null);
        }

        String result = "";
        if(startBlock.equals("") && endBlock.equals("")){//如果同时为null,即用户不设置，则默认全部恢复

            ChaincodeManager manager = FabricManager.obtain().getManager();
            Channel channel = manager.getChannelInstance();
            Long nowHeight = channel.queryBlockchainInfo().getHeight();//查询当前的区块高度
            result = recoveryService.startRecovery("1", String.valueOf(nowHeight));
        }
        result = recoveryService.startRecovery(startBlock, endBlock);
        return ResponseUtil.constructResponse(200, result, null);

    }



}
