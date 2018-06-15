package org.it611.das.control;

import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;
import org.it611.das.service.OwnerService;
import org.it611.das.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

@Controller
public class DataRecoveryController {

    private static Logger logger = Logger.getLogger(DataRecoveryController.class);


    @RequestMapping("/dataRecovery/index")
    public String dataRecovery(){
        return "data_recoveryAndBak";
    }


}