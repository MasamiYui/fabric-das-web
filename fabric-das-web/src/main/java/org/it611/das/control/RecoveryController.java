package org.it611.das.control;

import com.alibaba.fastjson.JSONObject;
import org.hyperledger.fabric.sdk.Channel;
import org.hyperledger.fabric.sdk.exception.CryptoException;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
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

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;

@Controller
public class RecoveryController {

    @Autowired
    private RecoveryService recoveryService;


    @RequestMapping(value = "/recovery/do", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject doRecovery(String startBlock, String endBlock) throws IOException, NoSuchAlgorithmException, InvocationTargetException, NoSuchMethodException, InstantiationException, InvalidKeySpecException, CryptoException, InvalidArgumentException, IllegalAccessException, NoSuchProviderException, TransactionException, ClassNotFoundException {

        String result = "";
        if(startBlock.equals("") && endBlock.equals("")){//如果同时为null,即用户不设置，则默认全部恢复

            ChaincodeManager manager = FabricManager.obtain().getManager();
            Channel channel = manager.getChannelInstance();
            //TODO 当前最高区块查询, 现在最高暂定61
            result = recoveryService.startRecovery("1", "61");
        }
        result = recoveryService.startRecovery(startBlock, endBlock);
        return ResponseUtil.constructResponse(200, result, null);

    }

}
