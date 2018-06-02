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

import java.util.HashMap;
import java.util.Map;

@Service
public class ScheduledService {

    @Autowired
    private StatisticsService statisticsService;

    @Autowired
    private MongoTemplate mongoTemplate;


    @Scheduled(cron= "0 * * * * 0-6")//每分钟执行
    public void StatisticsPerMin() {

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


        System.out.println("ok");
       /* String locolTime = TimeUtil.getLocalTime();
        String beforeTime = TimeUtil.getLocalMinNextTime(-1000*60*60);//小时整点统计
        int videoNum = statisticsService.statisticsAssetTotalByConditions(Video.class, beforeTime, locolTime, "");
        int audioNum = statisticsService.statisticsAssetTotalByConditions(Music.class, beforeTime, locolTime, "");
        int photoNum = statisticsService.statisticsAssetTotalByConditions(Photo.class, beforeTime, locolTime, "");
        int degreeCertNum = statisticsService.statisticsAssetTotalByConditions(DegreeCertificate.class, beforeTime, locolTime, "");
        Map<String, Object> statisticsPerHourMap = new HashMap<String, Object>();
        statisticsPerHourMap.put("time", locolTime);
        statisticsPerHourMap.put("videoNum", videoNum);
        statisticsPerHourMap.put("audioNum", audioNum);
        statisticsPerHourMap.put("photoNum", photoNum);
        statisticsPerHourMap.put("degreeCertNum", degreeCertNum);
        mongoTemplate.save(statisticsPerHourMap, "statisticsPerHour");*/
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

       /* String locolTime = TimeUtil.getLocalTime();
        String beforeTime = TimeUtil.getLocalNextTime(-1000*60*60);//小时整点统计
        int videoNum = statisticsService.statisticsAssetTotalByConditions(Video.class, beforeTime, locolTime, "");
        int audioNum = statisticsService.statisticsAssetTotalByConditions(Music.class, beforeTime, locolTime, "");
        int photoNum = statisticsService.statisticsAssetTotalByConditions(Photo.class, beforeTime, locolTime, "");
        int degreeCertNum = statisticsService.statisticsAssetTotalByConditions(DegreeCertificate.class, beforeTime, locolTime, "");
        Map<String, Object> statisticsPerDayMap = new HashMap<String, Object>();
        statisticsPerDayMap.put("time", locolTime);
        statisticsPerDayMap.put("videoNum", videoNum);
        statisticsPerDayMap.put("audioNum", audioNum);
        statisticsPerDayMap.put("photoNum", photoNum);
        statisticsPerDayMap.put("degreeCertNum", degreeCertNum);
        mongoTemplate.save(statisticsPerDayMap, "statisticsPerDay");*/

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


}
