package org.it611.das.service;


import org.it611.das.domain.*;
import org.it611.das.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * 定时统计计算服务
 */

@Service
public class ScheduledService {

    @Autowired
    private StatisticsService statisticsService;

    @Autowired
    private MongoTemplate mongoTemplate;


/*
    @Scheduled(cron= "0 * * * * 0-6")//每分钟执行
    public void StatisticsPerMin() {



        String localTime = TimeUtil.getLocalTimeFirstSecondTime(new Date());
        String startTime = TimeUtil.getLocalTimeFirstSecondTime((new Date(System.currentTimeMillis()-1000*60)));
        String endTime = TimeUtil.getLocalTimeEndSecondeTime((new Date(System.currentTimeMillis()-1000*60)));

        int degreeCertNumTotal = statisticsService.statisticsAssetTotalByConditions(DegreeCertificate.class, "",startTime, endTime, "");
        int videoNumTotal = statisticsService.statisticsAssetTotalByConditions(Video.class, "",startTime, endTime, "");
        int audioNumTotal = statisticsService.statisticsAssetTotalByConditions(Music.class, "",startTime, endTime, "");
        int photoNumTotal = statisticsService.statisticsAssetTotalByConditions(Photo.class, "",startTime, endTime, "");


        int degreeCertNumUnReviewed = statisticsService.statisticsAssetTotalByConditions(DegreeCertificate.class, "1", startTime, endTime, "");
        int videoNumUnReviewed = statisticsService.statisticsAssetTotalByConditions(Video.class, "0", startTime, endTime, "");
        int audioNumUnReviewed = statisticsService.statisticsAssetTotalByConditions(Music.class, "0", startTime, endTime, "");
        int photoNumUnReviewed = statisticsService.statisticsAssetTotalByConditions(Photo.class, "0", startTime, endTime, "");

        int degreeCertNumReviewed  = statisticsService.statisticsAssetTotalByConditions(DegreeCertificate.class, "1", startTime, endTime, "");
        int videoNumReviewed = statisticsService.statisticsAssetTotalByConditions(Video.class, "1", startTime, endTime, "");
        int audioNumReviewed = statisticsService.statisticsAssetTotalByConditions(Music.class, "1", startTime, endTime, "");
        int photoNumReviewed = statisticsService.statisticsAssetTotalByConditions(Photo.class, "1", startTime, endTime, "");

        int degreeCertNumUnPass  = statisticsService.statisticsAssetTotalByConditions(DegreeCertificate.class, "2", startTime, endTime, "");
        int videoNumUnPass = statisticsService.statisticsAssetTotalByConditions(Video.class, "2", startTime, endTime, "");
        int audioNumUnPass = statisticsService.statisticsAssetTotalByConditions(Music.class, "2", startTime, endTime, "");
        int photoNumUnPass = statisticsService.statisticsAssetTotalByConditions(Photo.class, "2", startTime, endTime, "");


        int degreeCertNumCanceled = statisticsService.statisticsAssetTotalByConditions(DegreeCertificate.class, "3", startTime, endTime, "");
        int videoNumCanceled = statisticsService.statisticsAssetTotalByConditions(Video.class, "3", startTime, endTime, "");
        int audioNumCanceled = statisticsService.statisticsAssetTotalByConditions(Music.class, "3", startTime, endTime, "");
        int photoNumCanceled = statisticsService.statisticsAssetTotalByConditions(Photo.class, "3", startTime, endTime, "");

        Map<String, Object> statisticsPerMinMap = new HashMap<String, Object>();

        statisticsPerMinMap.put("time", localTime);

        statisticsPerMinMap.put("degreeCertNumTotal", degreeCertNumTotal);
        statisticsPerMinMap.put("videoNumTotal", videoNumTotal);
        statisticsPerMinMap.put("audioNumTotal", audioNumTotal);
        statisticsPerMinMap.put("photoNumTotal", photoNumTotal);

        statisticsPerMinMap.put("degreeCertNumUnReviewed", degreeCertNumUnReviewed);
        statisticsPerMinMap.put("videoNumUnReviewed", videoNumUnReviewed);
        statisticsPerMinMap.put("audioNumUnReviewed", audioNumUnReviewed);
        statisticsPerMinMap.put("photoNumUnReviewed", photoNumUnReviewed);

        statisticsPerMinMap.put("degreeCertNumReviewed", degreeCertNumReviewed);
        statisticsPerMinMap.put("videoNumReviewed", videoNumReviewed);
        statisticsPerMinMap.put("audioNumReviewed", audioNumReviewed);
        statisticsPerMinMap.put("photoNumReviewed", photoNumReviewed);

        statisticsPerMinMap.put("degreeCertNumUnPass",degreeCertNumUnPass);
        statisticsPerMinMap.put("videoNumUnPass",videoNumUnPass);
        statisticsPerMinMap.put("audioNumUnPass",audioNumUnPass);
        statisticsPerMinMap.put("photoNumUnPass",photoNumUnPass);


        statisticsPerMinMap.put("degreeCertNumCanceled", degreeCertNumCanceled);
        statisticsPerMinMap.put("videoNumCanceled", videoNumCanceled);
        statisticsPerMinMap.put("audioNumCanceled", audioNumCanceled);
        statisticsPerMinMap.put("photoNumCanceled", photoNumCanceled);

        mongoTemplate.save(statisticsPerMinMap, "statisticsPerMin");
    }

    @Scheduled(cron= "0 0 * * * 0-6")//每小时执行
    public void StatisticsPerHour() {

        */
/**
         * 通过该小时内每分钟的数据分析，来对每小时的数据进行统计
         *//*

        String localTime = TimeUtil.getLocalTimeFirstSecondTime(new Date());
        String startTime = TimeUtil.getLocalTimeFirstSecondTime(new Date(System.currentTimeMillis()-1000*60*60));
        String endTime = TimeUtil.getLocalTimeEndSecondeTime(new Date(System.currentTimeMillis()-1000*60*59));

        HashMap dataMap = simpleStatiscs(startTime, endTime);
        dataMap.put("time", localTime);

        mongoTemplate.save(dataMap, "statisticsPerHour");
    }
*/


    @Scheduled(cron= "0 0 0 * * 0-6")//每天执行
    public void StatisticsPerDay() {


        /**
         * 通过该小时内每分钟的数据分析，来对每小时的数据进行统计
         */
/*
        String localTime = TimeUtil.getLocalTimeFirstSecondTime(new Date());
        String startTime = TimeUtil.getLocalTimeFirstSecondTime(new Date(System.currentTimeMillis()-1000*24*60*60));
        String endTime = TimeUtil.getLocalTimeEndSecondeTime(new Date(System.currentTimeMillis()-1000*24*60*59));


        HashMap dataMap = simpleStatiscs(startTime, endTime);
        dataMap.put("time", localTime);

        mongoTemplate.save(dataMap, "statisticsPerDay");
*/


/*        String localTime = TimeUtil.getLocalTimeFirstSecondTime(new Date());
        String startTime = TimeUtil.getLocalTimeFirstSecondTime((new Date(System.currentTimeMillis()-1000*60)));
        String endTime = TimeUtil.getLocalTimeEndSecondeTime((new Date(System.currentTimeMillis()-1000*60)));*/

        String localTime = TimeUtil.getLocalTimeBeforeDay() + " 00:00:00";
        String startTime = TimeUtil.getLocalTimeBeforeDay() + " 00:00:00";
        String endTime = TimeUtil.getLocalTimeBeforeDay() + " 23:59:59";

        int degreeCertNumTotal = statisticsService.statisticsAssetTotalByConditions(DegreeCertificate.class, "",startTime, endTime, "");
        int drivingLicenceNumTotal = statisticsService.statisticsAssetTotalByConditions(DrivingLicence.class, "", startTime, endTime, "");
        int syxxzlNumTotal = statisticsService.statisticsAssetTotalByConditions(Syxxzl.class, "", startTime, endTime, "");
        int videoNumTotal = statisticsService.statisticsAssetTotalByConditions(Video.class, "",startTime, endTime, "");
        int audioNumTotal = statisticsService.statisticsAssetTotalByConditions(Music.class, "",startTime, endTime, "");
        int photoNumTotal = statisticsService.statisticsAssetTotalByConditions(Photo.class, "",startTime, endTime, "");


        int degreeCertNumUnReviewed = statisticsService.statisticsAssetTotalByConditions(DegreeCertificate.class, "1", startTime, endTime, "");
        int drivingLicenceNumUnReviewed = statisticsService.statisticsAssetTotalByConditions(DrivingLicence.class, "0", startTime, endTime, "");
        int syxxzlNumUnReviewed = statisticsService.statisticsAssetTotalByConditions(Syxxzl.class, "0", startTime, endTime, "");
        int videoNumUnReviewed = statisticsService.statisticsAssetTotalByConditions(Video.class, "0", startTime, endTime, "");
        int audioNumUnReviewed = statisticsService.statisticsAssetTotalByConditions(Music.class, "0", startTime, endTime, "");
        int photoNumUnReviewed = statisticsService.statisticsAssetTotalByConditions(Photo.class, "0", startTime, endTime, "");

        int degreeCertNumReviewed  = statisticsService.statisticsAssetTotalByConditions(DegreeCertificate.class, "1", startTime, endTime, "");
        int drivingLicenceNumReviewed = statisticsService.statisticsAssetTotalByConditions(DrivingLicence.class, "1", startTime, endTime, "");
        int syxxzlNumReviewed = statisticsService.statisticsAssetTotalByConditions(Syxxzl.class, "1", startTime, endTime, "");
        int videoNumReviewed = statisticsService.statisticsAssetTotalByConditions(Video.class, "1", startTime, endTime, "");
        int audioNumReviewed = statisticsService.statisticsAssetTotalByConditions(Music.class, "1", startTime, endTime, "");
        int photoNumReviewed = statisticsService.statisticsAssetTotalByConditions(Photo.class, "1", startTime, endTime, "");

        int degreeCertNumUnPass  = statisticsService.statisticsAssetTotalByConditions(DegreeCertificate.class, "2", startTime, endTime, "");
        int drivingLicenceNumUnPass = statisticsService.statisticsAssetTotalByConditions(DrivingLicence.class, "2", startTime, endTime, "");
        int syxxzlNumUnPass = statisticsService.statisticsAssetTotalByConditions(Syxxzl.class, "2", startTime, endTime, "");
        int videoNumUnPass = statisticsService.statisticsAssetTotalByConditions(Video.class, "2", startTime, endTime, "");
        int audioNumUnPass = statisticsService.statisticsAssetTotalByConditions(Music.class, "2", startTime, endTime, "");
        int photoNumUnPass = statisticsService.statisticsAssetTotalByConditions(Photo.class, "2", startTime, endTime, "");


        int degreeCertNumCanceled = statisticsService.statisticsAssetTotalByConditions(DegreeCertificate.class, "3", startTime, endTime, "");
        int drivingLicenceNumCanceled = statisticsService.statisticsAssetTotalByConditions(DrivingLicence.class, "3", startTime, endTime, "");
        int syxxzlNumCanceled = statisticsService.statisticsAssetTotalByConditions(Syxxzl.class, "3", startTime, endTime, "");
        int videoNumCanceled = statisticsService.statisticsAssetTotalByConditions(Video.class, "3", startTime, endTime, "");
        int audioNumCanceled = statisticsService.statisticsAssetTotalByConditions(Music.class, "3", startTime, endTime, "");
        int photoNumCanceled = statisticsService.statisticsAssetTotalByConditions(Photo.class, "3", startTime, endTime, "");

        Map<String, Object> statisticsPerMinMap = new HashMap<String, Object>();

        statisticsPerMinMap.put("time", localTime);

        statisticsPerMinMap.put("degreeCertNumTotal", degreeCertNumTotal);
        statisticsPerMinMap.put("drivingLiceneNumTotal", drivingLicenceNumTotal);
        statisticsPerMinMap.put("syxxzlNumTotal", syxxzlNumTotal);
        statisticsPerMinMap.put("videoNumTotal", videoNumTotal);
        statisticsPerMinMap.put("audioNumTotal", audioNumTotal);
        statisticsPerMinMap.put("photoNumTotal", photoNumTotal);

        statisticsPerMinMap.put("degreeCertNumUnReviewed", degreeCertNumUnReviewed);
        statisticsPerMinMap.put("drivingLicenceNumUnReviewed",drivingLicenceNumUnReviewed);
        statisticsPerMinMap.put("syxxzlNumUnReviewed",syxxzlNumUnReviewed);
        statisticsPerMinMap.put("videoNumUnReviewed", videoNumUnReviewed);
        statisticsPerMinMap.put("audioNumUnReviewed", audioNumUnReviewed);
        statisticsPerMinMap.put("photoNumUnReviewed", photoNumUnReviewed);

        statisticsPerMinMap.put("degreeCertNumReviewed", degreeCertNumReviewed);
        statisticsPerMinMap.put("drivingLicenceNumReviewed",drivingLicenceNumReviewed);
        statisticsPerMinMap.put("syxxzlNumReviewed", syxxzlNumReviewed);
        statisticsPerMinMap.put("videoNumReviewed", videoNumReviewed);
        statisticsPerMinMap.put("audioNumReviewed", audioNumReviewed);
        statisticsPerMinMap.put("photoNumReviewed", photoNumReviewed);

        statisticsPerMinMap.put("degreeCertNumUnPass",degreeCertNumUnPass);
        statisticsPerMinMap.put("drivingLicenceNumUnPass",drivingLicenceNumUnPass);
        statisticsPerMinMap.put("syxxzlNumUnPass",syxxzlNumUnPass);
        statisticsPerMinMap.put("videoNumUnPass",videoNumUnPass);
        statisticsPerMinMap.put("audioNumUnPass",audioNumUnPass);
        statisticsPerMinMap.put("photoNumUnPass",photoNumUnPass);


        statisticsPerMinMap.put("degreeCertNumCanceled", degreeCertNumCanceled);
        statisticsPerMinMap.put("drivingLicenceNumCanceled",drivingLicenceNumCanceled);
        statisticsPerMinMap.put("syxxzlNumUnCanceled",syxxzlNumCanceled);
        statisticsPerMinMap.put("videoNumCanceled", videoNumCanceled);
        statisticsPerMinMap.put("audioNumCanceled", audioNumCanceled);
        statisticsPerMinMap.put("photoNumCanceled", photoNumCanceled);

        mongoTemplate.save(statisticsPerMinMap, "statisticsPerDay");


    }




    @Scheduled(cron= "0 0 0 1 * ?")//每月第一天进行统计
    public void StatisticsPerMonth() {

        /**
         * 通过该小时内每分钟的数据分析，来对每小时的数据进行统计
         */

        String localTime = TimeUtil.getLocalTimeFirstSecondTime(new Date());
        Calendar rightNow = Calendar.getInstance();
        Integer year = rightNow.get(Calendar.YEAR);
        Integer month = rightNow.get(Calendar.MONTH)+1;

        String nowTime = TimeUtil.getFirstDayOfMonth(year, month)+" 00:00:00";

        //判断今天是否是新年的第一天,则上一个月为上一年的12月
        if(month == 1){
            year = year-1;
            month = 12;
        }

        //否则月份-1即可
        month = month -1;


        //获取该月第一天和最后一天
        String startTime = TimeUtil.getFirstDayOfMonth(year, month);
        String endTime = TimeUtil.getLastDayOfMonth(year, month);


        //2018-06-01 11:11:00

        //填充日期格式
        startTime = startTime+" 00:00:00";
        endTime = endTime+" 23:59:59";


        HashMap dataMap = simpleStatiscs(startTime, endTime);
        dataMap.put("time", localTime);

        mongoTemplate.save(dataMap, "statisticsPerDay");


    }



    private HashMap<String, Object> simpleStatiscs(String startTime, String endTime) {
        // 返回的字段
/*        ProjectionOperation projectionOperation = Aggregation.project("degreeCertNumTotal", "videoNumTotal", "audioNumTotal", "photoNumTotal",
                "degreeCertNumUnReviewed", "videoNumUnReviewed", "audioNumUnReviewed", "photoNumUnReviewed",
                "degreeCertNumReviewed", "videoNumReviewed", "audioNumReviewed", "photoNumReviewed",
                "degreeCertNumUnPass", "videoNumUnPass", "audioNumUnPass", "photoNumUnPass",
                "degreeCertNumCanceled", "videoNumCanceled", "audioNumCanceled", "photoNumCanceled");*/

        // 分组操作，并对每个总条数进行统计
        GroupOperation groupOperation = Aggregation.group()
                .sum("degreeCertNumTotal").as("degreeCertNumTotal")
                .sum("videoNumTotal").as("videoNumTotal")
                .sum("audioNumTotal").as("audioNumTotal")
                .sum("photoNumTotal").as("photoNumTotal")

                .sum("degreeCertNumUnReviewed").as("degreeCertNumUnReviewed")
                .sum("videoNumUnReviewed").as("videoNumUnReviewed")
                .sum("audioNumUnReviewed").as("audioNumUnReviewed")
                .sum("photoNumUnReviewed").as("photoNumUnReviewed")

                .sum("degreeCertNumReviewed").as("degreeCertNumReviewed")
                .sum("videoNumReviewed").as("videoNumReviewed")
                .sum("audioNumReviewed").as("audioNumReviewed")
                .sum("photoNumReviewed").as("photoNumReviewed")

                .sum("degreeCertNumUnPass").as("degreeCertNumUnPass")
                .sum("videoNumUnPass").as("videoNumUnPass")
                .sum("audioNumUnPass").as("audioNumUnPass")
                .sum("photoNumUnPass").as("photoNumUnPass")

                .sum("degreeCertNumCanceled").as("degreeCertNumCanceled")
                .sum("videoNumCanceled").as("videoNumCanceled")
                .sum("audioNumCanceled").as("audioNumCanceled")
                .sum("photoNumCanceled").as("photoNumCanceled");


        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("time").gte(startTime)),
                Aggregation.match(Criteria.where("time").lte(endTime)),
                groupOperation
        );

        // 执行操作
        AggregationResults<HashMap> aggregationResults = this.mongoTemplate.aggregate(aggregation, "statisticsPerMin", HashMap.class);

        System.out.println(aggregationResults.getMappedResults());

        //获取统计结果
        HashMap queryMap = aggregationResults.getMappedResults().get(0);


        int degreeCertNumTotal = (int)queryMap.get("degreeCertNumTotal");
        int videoNumTotal = (int)queryMap.get("videoNumTotal");
        int audioNumTotal = (int)queryMap.get("audioNumTotal");
        int photoNumTotal = (int)queryMap.get("photoNumTotal");

        int degreeCertNumUnReviewed = (int)queryMap.get("degreeCertNumUnReviewed");
        int videoNumUnReviewed = (int)queryMap.get("videoNumUnReviewed");
        int audioNumUnReviewed = (int)queryMap.get("audioNumUnReviewed");
        int photoNumUnReviewed = (int)queryMap.get("photoNumUnReviewed");

        int degreeCertNumReviewed = (int)queryMap.get("degreeCertNumReviewed");
        int videoNumReviewed = (int)queryMap.get("videoNumReviewed");
        int audioNumReviewed = (int)queryMap.get("audioNumReviewed");
        int photoNumReviewed = (int)queryMap.get("photoNumReviewed");

        int degreeCertNumUnPass = (int)queryMap.get("degreeCertNumUnPass");
        int videoNumUnPass = (int)queryMap.get("videoNumUnPass");
        int audioNumUnPass = (int)queryMap.get("audioNumUnPass");
        int photoNumUnPass = (int)queryMap.get("photoNumUnPass");

        int degreeCertNumCanceled = (int)queryMap.get("degreeCertNumCanceled");
        int videoNumCanceled = (int)queryMap.get("videoNumCanceled");
        int audioNumCanceled = (int)queryMap.get("audioNumCanceled");
        int photoNumCanceled = (int)queryMap.get("photoNumCanceled");



        //写入mongodb数据库
        HashMap<String, Object> statisticsMap = new HashMap<String, Object>();
        //statisticsMap.put("time", localTime);

        statisticsMap.put("degreeCertNumTotal",degreeCertNumTotal);
        statisticsMap.put("videoNumTotal",videoNumTotal);
        statisticsMap.put("audioNumTotal",audioNumTotal);
        statisticsMap.put("photoNumTotal",photoNumTotal);

        statisticsMap.put("degreeCertNumUnReviewed",degreeCertNumUnReviewed);
        statisticsMap.put("videoNumUnReviewed",videoNumUnReviewed);
        statisticsMap.put("audioNumUnReviewed",audioNumUnReviewed);
        statisticsMap.put("photoNumUnReviewed",photoNumUnReviewed);

        statisticsMap.put("degreeCertNumReviewed",degreeCertNumReviewed);
        statisticsMap.put("videoNumReviewed",videoNumReviewed);
        statisticsMap.put("audioNumReviewed",audioNumReviewed);
        statisticsMap.put("photoNumReviewed",photoNumReviewed);

        statisticsMap.put("degreeCertNumUnPass",degreeCertNumUnPass);
        statisticsMap.put("videoNumUnPass",videoNumUnPass);
        statisticsMap.put("audioNumUnPass",audioNumUnPass);
        statisticsMap.put("photoNumUnPass",photoNumUnPass);

        statisticsMap.put("degreeCertNumCanceled",degreeCertNumCanceled);
        statisticsMap.put("videoNumCanceled",videoNumCanceled);
        statisticsMap.put("audioNumCanceled",audioNumCanceled);
        statisticsMap.put("photoNumCanceled",photoNumCanceled);

        return statisticsMap;
    }



}
