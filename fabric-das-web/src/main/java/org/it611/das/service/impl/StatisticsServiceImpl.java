package org.it611.das.service.impl;

import com.mongodb.BasicDBObject;
import org.it611.das.domain.DegreeCertificate;
import org.it611.das.domain.Music;
import org.it611.das.domain.Photo;
import org.it611.das.domain.Video;
import org.it611.das.service.StatisticsService;
import org.it611.das.util.ResponseUtil;
import org.it611.das.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    private MongoTemplate mongoTemplate;


    /**
     * 根据时间段，对各类资产数量进行统计
     */
    @Override
    public HashMap statisticsAssetTotal(String startTime, String endTime) {

        // 返回的字段
        //ProjectionOperation projectionOperation = Aggregation.project("degreeCertNum", "audioNum", "videoNum", "photoNum");

        // 日期条件
        //Criteria operator = Criteria.where("submitTime").gte(startTime).where("submitTime").lt(endTime);
        //MatchOperation matchOperation = Aggregation.match(operator);

        //时间处理
        startTime = startTime + " 00:00:00";
        endTime = endTime + " 00:00:00";

        // 分组操作，并对每个总条数进行统计
        GroupOperation videoGroupOperation = Aggregation.group()
                .sum("audioNum").as("audioNum")
                .sum("videoNum").as("videoNum")
                .sum("photoNum").as("photoNum")
                .sum("degreeCertNum").as("degreeCertNum");
/*        GroupOperation audioGroupOperation = Aggregation.group().sum("audioNum").as("audioNum");
        GroupOperation degreeCertGroupOperation = Aggregation.group().sum("degreeCertNum").as("degreeCertNum");
        GroupOperation photoGroupOperation = Aggregation.group().sum("photoNum").as("photoNum");*/
        // 组合条件
        //Aggregation aggregation = Aggregation.newAggregation(projectionOperation, matchOperation, groupOperation);
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("time").gte(startTime)),
                Aggregation.match(Criteria.where("time").lte(endTime)),
                videoGroupOperation
/*                audioGroupOperation,
                degreeCertGroupOperation,
                photoGroupOperation*/
        );

        // 执行操作
        //AggregationResults<HashMap> aggregationResults = this.mongoTemplate.aggregate(aggregation, "statisticsPerMin", HashMap.class);
        AggregationResults<HashMap> aggregationResults = this.mongoTemplate.aggregate(aggregation, "statisticsPerHour", HashMap.class);//先暂时从小时数据库里查询，后面前换成天，月

        System.out.println(aggregationResults.getMappedResults());

        HashMap statisticsMap = new HashMap<String, Object>();

        if (aggregationResults.getMappedResults().size() == 0) {
            statisticsMap.put("degreeCertNum", 0);
            statisticsMap.put("videoNum", 0);
            statisticsMap.put("audioNum", 0);
            statisticsMap.put("photoNum", 0);
            System.out.println(aggregationResults.getMappedResults());
            return statisticsMap;
        }
        HashMap queryMap = aggregationResults.getMappedResults().get(0);
        statisticsMap.put("degreeCertNum", queryMap.get("degreeCertNum"));
        statisticsMap.put("videoNum", queryMap.get("videoNum"));
        statisticsMap.put("audioNum", queryMap.get("audioNum"));
        statisticsMap.put("photoNum", queryMap.get("photoNum"));
        System.out.println(aggregationResults.getMappedResults());
        return statisticsMap;

       /* //聚合
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("time").gte(startTime)),
                Aggregation.match(Criteria.where("time").lte(endTime))
        );
        List<BasicDBObject> list = mongoTemplate.aggregate(aggregation , "statisticsPerMin",  BasicDBObject.class).getMappedResults();
        return list.size();//TODO：感觉不是统计count的最好方法*/

    }

    @Override
    public HashMap statisticsAssetState(Class assetType) {

        HashMap<String, Object> resultMap = new HashMap<>();
        //TODO:暂未使用mongodb count 对其进行优化
        int state0 = mongoTemplate.find(new Query().addCriteria(Criteria.where("state").is("0")), assetType).size();
        int state1 = mongoTemplate.find(new Query().addCriteria(Criteria.where("state").is("1")), assetType).size();
        int state2 = mongoTemplate.find(new Query().addCriteria(Criteria.where("state").is("2")), assetType).size();
        int state3 = mongoTemplate.find(new Query().addCriteria(Criteria.where("state").is("3")), assetType).size();

        resultMap.put("unreviewed", state0);
        resultMap.put("reviewed", state1);
        resultMap.put("unpass", state2);
        resultMap.put("canceled", state3);

        return resultMap;
    }


    /**
     * 根据时间范围统计某一资产个数
     * 时间区间自由设置，min hour day
     * 如：统计2018-06-01 11:11:00 ------ 2018-06-01 11:12:00这一分钟Video资产的数量
     * 如：统计2018-06-01 11:11:00 ------ 2018-06-01 12:11:00这一个小时Audio资产的数量
     */
    @Override
    public int statisticsAssetTotalByConditions(Class assetType, String startTime, String endTime, String ownerId) {

        //聚合
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("submitTime").gte(startTime)),
                Aggregation.match(Criteria.where("submitTime").lte(endTime)),
                Aggregation.match(Criteria.where("ownerId").regex(ownerId))
        );
        List<BasicDBObject> list = mongoTemplate.aggregate(aggregation, assetType, BasicDBObject.class).getMappedResults();
        return list.size();//TODO：感觉不是统计count的最好方法
    }


    @Override
    public HashMap statisticsAssetTrend(String startTime, String endTime, String assetType) {

        //时间解析
        String[] startTimeArr = startTime.split("-");
        String startTimeYear = startTimeArr[0];
        String startTimeMonth = startTimeArr[1];

        String[] endTimeArr = endTime.split("-");
        String endTimeYear = endTimeArr[0];
        String endTimeMonth = endTimeArr[1];


        //时间格式补全
        startTime = TimeUtil.getFirstDayOfMonth(Integer.valueOf(startTimeYear), Integer.valueOf(startTimeMonth));
        endTime = TimeUtil.getLastDayOfMonth(Integer.valueOf(endTimeYear), Integer.valueOf(endTimeMonth));

        //查询条件
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("time").gte(startTime + " 00:00:00")),
                Aggregation.match(Criteria.where("time").lte(endTime + " 00:00:00"))
        );


        System.out.println("startTime:" + startTime);
        System.out.println("endTime:" + endTime);

        HashMap resultMap = parseData(aggregation, "statisticsPerMonth", assetType);

        return resultMap;

    }

    @Override
    public HashMap statisticsAssetTrend(String time, String assetType) {

        String[] timeArr = time.split("-");
        String year = timeArr[0];
        String month = timeArr[1];
        String startTime = TimeUtil.getFirstDayOfMonth(Integer.valueOf(year), Integer.valueOf(month));
        String endTime = TimeUtil.getLastDayOfMonth(Integer.valueOf(year), Integer.valueOf(month));
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("time").gte(startTime)),
                Aggregation.match(Criteria.where("time").lte(endTime))
        );

        HashMap resultMap = parseData(aggregation, "statisticsPerDay", assetType);

        return resultMap;
    }


    public HashMap parseData(Aggregation aggregation, String collectName, String assetType) {

        //整理返回数据
        ArrayList degreeCertNumTotalArr = new ArrayList<Integer>();
        ArrayList videoNumTotalArr = new ArrayList<Integer>();
        ArrayList audioNumTotalArr = new ArrayList<Integer>();
        ArrayList photoNumTotalArr = new ArrayList<Integer>();

        ArrayList degreeCertNumUnReviewedArr = new ArrayList<Integer>();
        ArrayList videoNumUnReviewedArr = new ArrayList<Integer>();
        ArrayList audioNumUnReviewedArr = new ArrayList<Integer>();
        ArrayList photoNumUnReviewedArr = new ArrayList<Integer>();

        ArrayList degreeCertNumReviewedArr = new ArrayList<Integer>();
        ArrayList videoNumReviewedArr = new ArrayList<Integer>();
        ArrayList audioNumReviewedArr = new ArrayList<Integer>();
        ArrayList photoNumReviewedArr = new ArrayList<Integer>();

        ArrayList degreeCertNumUnPassArr = new ArrayList<Integer>();
        ArrayList videoNumUnPassArr = new ArrayList<Integer>();
        ArrayList audioNumUnPassArr = new ArrayList<Integer>();
        ArrayList photoNumUnPassArr = new ArrayList<Integer>();

        ArrayList degreeCertNumCanceledArr = new ArrayList<Integer>();
        ArrayList videoNumCanceledArr = new ArrayList<Integer>();
        ArrayList audioNumCanceledArr = new ArrayList<Integer>();
        ArrayList photoNumCanceledArr = new ArrayList<Integer>();


        List<BasicDBObject> list = mongoTemplate.aggregate(aggregation, collectName, BasicDBObject.class).getMappedResults();
        for (int i = 0; i < list.size(); i++) {
            BasicDBObject data = list.get(i);
            degreeCertNumTotalArr.add(data.get("degreeCertNumTotal"));
            videoNumTotalArr.add(data.get("videoNumTotal"));
            audioNumTotalArr.add(data.get("audioNumTotal"));
            photoNumTotalArr.add(data.get("photoNumTotal"));

            degreeCertNumUnReviewedArr.add(data.get("degreeCertNumUnReviewed"));
            videoNumUnReviewedArr.add(data.get("videoNumUnReviewed"));
            audioNumUnReviewedArr.add(data.get("audioNumUnReviewed"));
            photoNumUnReviewedArr.add(data.get("photoNumUnReviewed"));

            degreeCertNumReviewedArr.add(data.get("degreeCertNumReviewed"));
            videoNumReviewedArr.add(data.get("videoNumReviewed"));
            audioNumReviewedArr.add(data.get("audioNumReviewed"));
            photoNumReviewedArr.add(data.get("photoNumReviewed"));

            degreeCertNumUnPassArr.add(data.get("degreeCertNumUnPass"));
            videoNumUnPassArr.add(data.get("videoNumUnPass"));
            audioNumUnPassArr.add(data.get("audioNumUnPass"));
            photoNumUnPassArr.add(data.get("photoNumUnPass"));


            degreeCertNumCanceledArr.add(data.get("degreeCertNumCanceled"));
            videoNumCanceledArr.add(data.get("videoNumCanceled"));
            audioNumCanceledArr.add(data.get("audioNumCanceled"));
            photoNumCanceledArr.add(data.get("photoNumCanceled"));
        }

        HashMap<String, Object> resultMap = new HashMap();
        switch (assetType) {
            case "-1":
                resultMap.put("degreeCertNumTotal", degreeCertNumTotalArr);
                resultMap.put("videoNumTotal", videoNumTotalArr);
                resultMap.put("audioNumTotal", audioNumTotalArr);
                resultMap.put("photoNumTotal", photoNumTotalArr);
                break;
            case "1":
                resultMap.put("degreeCertNumUnReviewed", degreeCertNumUnReviewedArr);
                resultMap.put("videoNumUnReviewed", videoNumUnReviewedArr);
                resultMap.put("audioNumUnReviewed", audioNumUnReviewedArr);
                resultMap.put("photoNumUnReviewed", photoNumUnReviewedArr);
                break;
            case "2":
                resultMap.put("degreeCertNumReviewed", degreeCertNumReviewedArr);
                resultMap.put("videoNumReviewed", videoNumReviewedArr);
                resultMap.put("audioNumReviewed", audioNumReviewedArr);
                resultMap.put("photoNumReviewed", photoNumReviewedArr);
                break;
            case "3":
                resultMap.put("degreeCertNumUnPass", degreeCertNumUnPassArr);
                resultMap.put("videoNumUnPass", videoNumUnPassArr);
                resultMap.put("audioNumUnPass", audioNumUnPassArr);
                resultMap.put("photoNumUnPass", photoNumUnPassArr);
                break;
            case "4":
                resultMap.put("degreeCertNumCanceled", degreeCertNumCanceledArr);
                resultMap.put("videoNumCanceled", videoNumCanceledArr);
                resultMap.put("audioNumCanceled", audioNumCanceledArr);
                resultMap.put("photoNumCanceled", photoNumCanceledArr);
                break;
            default:
                resultMap.put("degreeCertNumCanceled", 0);
                resultMap.put("videoNumCanceled", 0);
                resultMap.put("audioNumCanceled", 0);
                resultMap.put("photoNumCanceled", 0);
                break;
        }
        return resultMap;

    }



}
