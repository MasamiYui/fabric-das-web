package org.it611.das.service;


import org.it611.das.domain.DegreeCertificate;
import org.it611.das.domain.Music;
import org.it611.das.domain.Photo;
import org.it611.das.domain.Video;
import org.it611.das.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Calendar;
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


    @Scheduled(cron= "0 * * * * 0-6")//每分钟执行
    public void StatisticsPerMin() {

        System.out.println("每分钟任务");
        String locolTime = TimeUtil.getLocalTime();
        String beforeTime = TimeUtil.getLocalNextTime(-1000*60);
        int videoNum = statisticsService.statisticsAssetTotalByConditions(Video.class, beforeTime, locolTime, "");
        int audioNum = statisticsService.statisticsAssetTotalByConditions(Music.class, beforeTime, locolTime, "");
        int photoNum = statisticsService.statisticsAssetTotalByConditions(Photo.class, beforeTime, locolTime, "");
        int degreeCertNum = statisticsService.statisticsAssetTotalByConditions(DegreeCertificate.class, beforeTime, locolTime, "");
        Map<String, Object> statisticsPerMinMap = new HashMap<String, Object>();
        statisticsPerMinMap.put("time", locolTime);
        statisticsPerMinMap.put("videoNum", videoNum);
        statisticsPerMinMap.put("audioNum", audioNum);
        statisticsPerMinMap.put("photoNum", photoNum);
        statisticsPerMinMap.put("degreeCertNum", degreeCertNum);
        mongoTemplate.save(statisticsPerMinMap, "statisticsPerMin");
    }

    @Scheduled(cron= "0 0 * * * 0-6")//每小时执行
    public void StatisticsPerHour() {


        /**
         * 通过该小时内每分钟的数据分析，来对每小时的数据进行统计
         */
        String beforeTime = TimeUtil.getLocalNextTime(-1000*60*60);
        String localTime = TimeUtil.getLocalTime();

        // 返回的字段
        ProjectionOperation projectionOperation = Aggregation.project("degreeCertNum", "audioNum", "videoNum", "photoNum");

        // 分组操作，并对每个总条数进行统计
        GroupOperation groupOperation = Aggregation.group()
                .sum("audioNum").as("audioNum")
                .sum("videoNum").as("videoNum")
                .sum("photoNum").as("photoNum")
                .sum("degreeCertNum").as("degreeCertNum");
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("time").gte(beforeTime)),
                Aggregation.match(Criteria.where("time").lte(localTime)),
                groupOperation
        );

        // 执行操作
        AggregationResults<HashMap> aggregationResults = this.mongoTemplate.aggregate(aggregation, "statisticsPerMin", HashMap.class);

        System.out.println(aggregationResults.getMappedResults());

        //获取统计结果
        HashMap queryMap = aggregationResults.getMappedResults().get(0);
        int videoNum = (int)queryMap.get("videoNum");
        int audioNum = (int)queryMap.get("audioNum");
        int photoNum = (int)queryMap.get("photoNum");
        int degreeCertNum = (int)queryMap.get("degreeCertNum");


        //写入mongodb数据库
        Map<String, Object> statisticsPerHourMap = new HashMap<String, Object>();
        statisticsPerHourMap.put("time", localTime);
        statisticsPerHourMap.put("videoNum", videoNum);
        statisticsPerHourMap.put("audioNum", audioNum);
        statisticsPerHourMap.put("photoNum", photoNum);
        statisticsPerHourMap.put("degreeCertNum", degreeCertNum);

        mongoTemplate.save(statisticsPerHourMap, "statisticsPerHour");
    }


    @Scheduled(cron= "0 0 0 * * 0-6")//每天执行
    public void StatisticsPerDay() {


        /**
         * 通过该小时内每分钟的数据分析，来对每小时的数据进行统计
         */
        String beforeTime = TimeUtil.getLocalNextTime(-1000*60*60*60);
        String localTime = TimeUtil.getLocalTime();


        // 返回的字段
        ProjectionOperation projectionOperation = Aggregation.project("degreeCertNum", "audioNum", "videoNum", "photoNum");

        // 分组操作，并对每个总条数进行统计
        GroupOperation groupOperation = Aggregation.group()
                .sum("audioNum").as("audioNum")
                .sum("videoNum").as("videoNum")
                .sum("photoNum").as("photoNum")
                .sum("degreeCertNum").as("degreeCertNum");
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("time").gte(beforeTime)),
                Aggregation.match(Criteria.where("time").lte(localTime)),
                groupOperation
        );

        // 执行操作
        AggregationResults<HashMap> aggregationResults = this.mongoTemplate.aggregate(aggregation, "statisticsPerMin", HashMap.class);

        System.out.println(aggregationResults.getMappedResults());

        //获取统计结果
        HashMap queryMap = aggregationResults.getMappedResults().get(0);
        int videoNum = (int)queryMap.get("videoNum");
        int audioNum = (int)queryMap.get("audioNum");
        int photoNum = (int)queryMap.get("photoNum");
        int degreeCertNum = (int)queryMap.get("degreeCertNum");


        //写入mongodb数据库
        Map<String, Object> statisticsPerDayMap = new HashMap<String, Object>();
        statisticsPerDayMap.put("time", localTime);
        statisticsPerDayMap.put("videoNum", videoNum);
        statisticsPerDayMap.put("audioNum", audioNum);
        statisticsPerDayMap.put("photoNum", photoNum);
        statisticsPerDayMap.put("degreeCertNum", degreeCertNum);

        mongoTemplate.save(statisticsPerDayMap, "statisticsPerDay");

    }




    @Scheduled(cron= "0 0 0 1 * ?")//每月第一天进行统计
    public void StatisticsPerMonth() {

        /**
         * 通过该小时内每分钟的数据分析，来对每小时的数据进行统计
         */
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
        endTime = endTime+" 00:00:00";


        System.out.println(startTime);
        System.out.println(endTime);


        // 返回的字段
        ProjectionOperation projectionOperation = Aggregation.project("degreeCertNum", "audioNum", "videoNum", "photoNum");

        // 分组操作，并对每个总条数进行统计
        GroupOperation groupOperation = Aggregation.group()
                .sum("audioNum").as("audioNum")
                .sum("videoNum").as("videoNum")
                .sum("photoNum").as("photoNum")
                .sum("degreeCertNum").as("degreeCertNum");
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("time").gte(startTime)),
                Aggregation.match(Criteria.where("time").lte(endTime)),
                groupOperation
        );

        // 执行操作
        AggregationResults<HashMap> aggregationResults = this.mongoTemplate.aggregate(aggregation, "statisticsPerDay", HashMap.class);

        System.out.println(aggregationResults.getMappedResults());

        Map<String, Object> statisticsPerMonthMap = new HashMap<String, Object>();

        //获取统计结果
        if(aggregationResults.getMappedResults().size() == 0){
            statisticsPerMonthMap.put("degreeCertNum", 0);
            statisticsPerMonthMap.put("videoNum", 0);
            statisticsPerMonthMap.put("audioNum", 0);
            statisticsPerMonthMap.put("photoNum", 0);
            System.out.println(aggregationResults.getMappedResults());
            mongoTemplate.save(statisticsPerMonthMap, "statisticsPerMonth");
        }


        HashMap queryMap = aggregationResults.getMappedResults().get(0);
        int videoNum = (int)queryMap.get("videoNum");
        int audioNum = (int)queryMap.get("audioNum");
        int photoNum = (int)queryMap.get("photoNum");
        int degreeCertNum = (int)queryMap.get("degreeCertNum");


        //写入mongodb数据库

        statisticsPerMonthMap.put("time", nowTime);
        statisticsPerMonthMap.put("videoNum", videoNum);
        statisticsPerMonthMap.put("audioNum", audioNum);
        statisticsPerMonthMap.put("photoNum", photoNum);
        statisticsPerMonthMap.put("degreeCertNum", degreeCertNum);

        mongoTemplate.save(statisticsPerMonthMap, "statisticsPerMonth");

    }



}
