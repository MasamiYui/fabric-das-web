package org.it611.das.control;

import com.alibaba.fastjson.JSONObject;
import org.it611.das.domain.*;
import org.it611.das.service.StatisticsService;
import org.it611.das.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

@Controller
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;



    //显示饼状图
    @RequestMapping(value = "/statistics/assertPie", method = RequestMethod.GET)
    public String pieChart(){

        return "chart_generalAssertPie";
    }


    /**
     * 资产分类查询（根据时间段）前台为"2018-06-02","2018-06-03" 时间格式
     * 后台会把00：00：00补充，最后mongodb中时间格式为2018-06-02 00:00:00
     */
    @RequestMapping(value = "/statistics/general/assetType", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject assetTypeGeneral(String startTime, String endTime) {
        HashMap result = statisticsService.statisticsAssetTotal(startTime, endTime);
        return ResponseUtil.constructResponse(200, "ok", result);
    }

    @RequestMapping(value = "/statistics/general/assetState", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject assetStateassetTypeGeneral(String assetType) {

        Class clz = null;

        switch (assetType) {
            case "视频":
                clz = Video.class;
                break;
            case "音频":
                clz = Music.class;
                break;
            case "图片":
                clz = Photo.class;
                break;
            case "学位证书":
                clz = DegreeCertificate.class;
                break;
            case "实用新型专利":
                clz = Syxxzl.class;
                break;
            case "机动车驾驶证":
                clz = DrivingLicence.class;
                break;
            default:
                return ResponseUtil.constructResponse(400, "unknown asset type.", null);
        }
        HashMap result = statisticsService.statisticsAssetState(clz);
        return ResponseUtil.constructResponse(200, "ok", result);
    }


    //显示资产趋势条形图1
    @RequestMapping(value = "/statistics/trendLine", method = RequestMethod.GET)
    public String trendLine(){return "chart_assertTrendLine";}


    //时间段数据统计(格式年-月 如：2018-05到2018-06)
    @RequestMapping(value = "/statistics/trend/assertNumByTimeSlot", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject assetStateassetNumTrend(String startTime, String endTime, String stateType){
        HashMap resultMap = statisticsService.statisticsAssetTrend(startTime, endTime, stateType);

        return ResponseUtil.constructResponse(200, "ok", resultMap);
    }


    //具体时间数据统计(格式年-月 如：2018-05)
    @RequestMapping(value = "/statistics/trend/assertNumByTime", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject assetStateassetNumTrend(String time, String stateType){
        HashMap resultMap = statisticsService.statisticsAssetTrend(time, stateType);
        return ResponseUtil.constructResponse(200, "ok", resultMap);
    }


    //显示用户资产图表3
    @RequestMapping(value = "/statistics/userAssertLinePie", method = RequestMethod.GET)
    public String userAssertLinePie(){return "chart_userAssertLine";}

    //根据用户名查询用户所有资产的状态
    @RequestMapping(value = "/statistics/user/detail", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject userAssetGeneral(String userId) {

        HashMap resultMap = statisticsService.statisticsUserAssetDetail(userId);
        return ResponseUtil.constructResponse(200, "ok", resultMap);
    }


}
