package org.it611.das.control;

import com.alibaba.fastjson.JSONObject;
import org.it611.das.domain.Video;
import org.it611.das.service.StatisticsService;
import org.it611.das.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;


    /**
     * 资产分类查询（根据时间段）"2018-06-02 00:00:00","2018-06-03 00:00:00" 时间格式
     */
    @RequestMapping("/statistics/general/assetType")
    @ResponseBody
    public JSONObject assetTypeGeneral(String startTime, String endTime) {
        HashMap result = statisticsService.statisticsAssetTotal(startTime, endTime);
        return ResponseUtil.constructResponse(200, "ok", result);
    }

    @RequestMapping("/statistics/general/state")
    @ResponseBody
    public JSONObject assetStateassetTypeGeneral() {
        HashMap result = statisticsService.statisticsAssetState(Video.class);
        return ResponseUtil.constructResponse(200, "ok", result);
    }


}
