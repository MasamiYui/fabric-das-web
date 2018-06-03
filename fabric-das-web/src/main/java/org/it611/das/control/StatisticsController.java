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

    @RequestMapping("/statistics/general/state")
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
            case "学历证书":
                clz = DegreeCertificate.class;
                break;
            default:
                return ResponseUtil.constructResponse(400, "unknown asset type.", null);
        }
        HashMap result = statisticsService.statisticsAssetState(clz);
        return ResponseUtil.constructResponse(200, "ok", result);
    }

}
