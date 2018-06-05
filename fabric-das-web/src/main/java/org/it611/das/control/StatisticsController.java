package org.it611.das.control;

import com.alibaba.fastjson.JSONObject;
import org.it611.das.domain.DegreeCertificate;
import org.it611.das.domain.Music;
import org.it611.das.domain.Photo;
import org.it611.das.domain.Video;
import org.it611.das.service.StatisticsService;
import org.it611.das.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

@Controller
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;



    //显示饼状图
    @RequestMapping("/statistics/assertPie")
    public String pieChart(){return "chart_generalAssertPie";}
    /**
     * 资产分类查询（根据时间段）前台为"2018-06-02","2018-06-03" 时间格式
     * 后台会把00：00：00补充，最后mongodb中时间格式为2018-06-02 00:00:00
     */
    @RequestMapping("/statistics/general/assetType")
    @ResponseBody
    public JSONObject assetTypeGeneral(String startTime, String endTime) {
        HashMap result = statisticsService.statisticsAssetTotal(startTime, endTime);
        return ResponseUtil.constructResponse(200, "ok", result);
    }

    @RequestMapping("/statistics/general/assertState")
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
            default:
                return ResponseUtil.constructResponse(400, "unknown asset type.", null);
        }
        HashMap result = statisticsService.statisticsAssetState(clz);
        return ResponseUtil.constructResponse(200, "ok", result);
    }


    //显示资产趋势条形图1
    @RequestMapping("/statistics/trendLine")
    public String trendLine(){return "chart_assertTrendLine";}

    //时间段数据统计(格式年-月 如：2018-05到2018-06)
    @RequestMapping("/statistics/trend/assertNumByTimeSlot")
    @ResponseBody
    public JSONObject assetStateassetNumTrend(String startTime, String endTime, String stateType){
        HashMap resultMap = statisticsService.statisticsAssetTrend(startTime, endTime, stateType);

        return ResponseUtil.constructResponse(200, "ok", resultMap);
    }
    //具体时间数据统计(格式年-月 如：2018-05)
    @RequestMapping("/statistics/trend/assertNumByTime")
    @ResponseBody
    public JSONObject assetStateassetNumTrend(String time, String stateType){
        HashMap resultMap = statisticsService.statisticsAssetTrend(time, stateType);
        return ResponseUtil.constructResponse(200, "ok", resultMap);
    }



    //显示用户资产图表3
    @RequestMapping("/statistics/userAssertLinePie")
    public String userAssertLinePie(){return "chart_userAssertLine";}

    //根据用户名查询用户所有资产的状态
    @RequestMapping("/statistics/user/detail")
    @ResponseBody
    public JSONObject userAssetGeneral(String userId) {

        HashMap resultMap = statisticsService.statisticsUserAssetDetail(userId);
        return ResponseUtil.constructResponse(200, "ok", resultMap);
    }


}
