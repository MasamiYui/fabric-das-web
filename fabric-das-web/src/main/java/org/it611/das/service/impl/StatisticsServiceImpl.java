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
import org.springframework.data.mongodb.core.mapreduce.GroupBy;
import org.springframework.data.mongodb.core.mapreduce.GroupByResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;


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
        endTime = endTime + " 23:59:59";

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
            statisticsMap.put("degreeCertNum", "0");
            statisticsMap.put("videoNum", "0");
            statisticsMap.put("audioNum", "0");
            statisticsMap.put("photoNum", "0");
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
    public int statisticsAssetTotalByConditions(Class assetType, String state, String startTime, String endTime, String ownerId) {

        //聚合
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("submitTime").gte(startTime)),//开始时间
                Aggregation.match(Criteria.where("submitTime").lte(endTime)),//结束时间
                Aggregation.match(Criteria.where("ownerId").regex(ownerId)),//拥有者
                Aggregation.match(Criteria.where("state").regex(state))//状态
        );
        List<BasicDBObject> list = mongoTemplate.aggregate(aggregation, assetType, BasicDBObject.class).getMappedResults();
        return list.size();//TODO：感觉不是统计count的最好方法
    }


    @Override
    public HashMap statisticsAssetTrend(String startTime, String endTime, String stateType) {

        //时间解析
        String[] startTimeArr = startTime.split("-");
        String startTimeYear = startTimeArr[0];
        String startTimeMonth = startTimeArr[1];


        String[] endTimeArr = endTime.split("-");
        String endTimeYear = endTimeArr[0];
        String endTimeMonth = endTimeArr[1];


        //统计是下一月的第一天统计的 故采取以下方式
        if(startTimeMonth.equals("12")){
            startTimeYear = String.valueOf(Integer.parseInt(startTimeYear)+1);
            startTimeMonth = "1";
        }else{
            startTimeMonth = String.valueOf(Integer.valueOf(startTimeMonth)+1);
        }

        if(endTimeMonth.equals("12")){
            endTimeYear = String.valueOf(Integer.parseInt(endTimeYear)+1);
            endTimeMonth = "1";
        }else{
            endTimeMonth = String.valueOf(Integer.valueOf(endTimeMonth)+1);
        }



        //时间格式补全
        startTime = TimeUtil.getFirstDayOfMonth(Integer.valueOf(startTimeYear), Integer.valueOf(startTimeMonth))+ " 00:00:00";
        endTime = TimeUtil.getLastDayOfMonth(Integer.valueOf(endTimeYear), Integer.valueOf(endTimeMonth)) + " 23:59:59";


        System.out.println(startTime);
        System.out.println(endTime);


        //查询条件
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("time").gte(startTime)),
                Aggregation.match(Criteria.where("time").lte(endTime))
        );


        System.out.println("startTime:" + startTime);
        System.out.println("endTime:" + endTime);

        HashMap dataMap = parseData(aggregation, "statisticsPerMonth", stateType);


        //如果当前月份还没有结束，则从每日数据库中取值
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH)+1;
        HashMap<String, Object> dataMap2 = null;
        //如果查询条件包含当前月（该月还没有结束）
        if(year == Integer.valueOf(endTimeYear) && month < Integer.valueOf(endTimeMonth)){
            //从天的数据库中进行统计
            String startTimeDay  = TimeUtil.getFirstDayOfMonth(year, month)+ " 00:00:00";
            String endTimeDay = TimeUtil.getLocalTime();
            dataMap2 = parseData2(startTimeDay, endTimeDay,stateType,"statisticsPerDay");
        }

        if(dataMap2 == null){
            return dataMap;
        }
        HashMap resultMap = combineData(dataMap, dataMap2);

        return resultMap;

    }



    @Override
    public HashMap statisticsAssetTrend(String time, String stateType) {

        String[] timeArr = time.split("-");
        String year = timeArr[0];
        String month = timeArr[1];

        String startTime = TimeUtil.getFirstDayOfMonth(Integer.valueOf(year), Integer.valueOf(month))+" 00:00:00";
        String endTime = TimeUtil.getLastDayOfMonth(Integer.valueOf(year), Integer.valueOf(month))+" 23:59:59";
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("time").gte(startTime)),
                Aggregation.match(Criteria.where("time").lte(endTime))
        );

        HashMap resultMap = parseData(aggregation, "statisticsPerDay", stateType);
        return resultMap;




/*
        //统计是下一月的第一天统计的 故采取以下方式
        if(month.equals("12")){
            year = String.valueOf(Integer.parseInt(year)+1);
            month = "1";
        }else{
            month = String.valueOf(Integer.valueOf(month)+1);
        }


        Calendar cal = Calendar.getInstance();
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH)+1;

        HashMap resultMap = new HashMap();*/

       /* if(yearNow == Integer.valueOf(year) && monthNow < Integer.valueOf(month)){
            //从天的数据库中进行统计
            String startTimeDay  = TimeUtil.getFirstDayOfMonth(Integer.valueOf(year), Integer.valueOf(month)-1)+ " 00:00:00";
            String endTimeDay = TimeUtil.getLocalTime();
            resultMap = parseData2(startTimeDay, endTimeDay,stateType,"statisticsPerDay");
            return resultMap;
        }


        String startTime = TimeUtil.getFirstDayOfMonth(Integer.valueOf(year), Integer.valueOf(month))+ " 00:00:00";
        String endTime = TimeUtil.getLastDayOfMonth(Integer.valueOf(year), Integer.valueOf(month))+ " 23:59:59";
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("time").gte(startTime)),
                Aggregation.match(Criteria.where("time").lte(endTime))
        );

        resultMap = parseData(aggregation, "statisticsPerDay", stateType);

        return resultMap;*/
    }









    public HashMap<String, Object> parseData(Aggregation aggregation, String collectName, String stateType) {

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
        switch (stateType) {
            case "-1":
                resultMap.put("degreeCertNum", degreeCertNumTotalArr);
                resultMap.put("videoNum", videoNumTotalArr);
                resultMap.put("audioNum", audioNumTotalArr);
                resultMap.put("photoNum", photoNumTotalArr);
                break;
            case "0":
                resultMap.put("degreeCertNum", degreeCertNumUnReviewedArr);
                resultMap.put("videoNum", videoNumUnReviewedArr);
                resultMap.put("audioNum", audioNumUnReviewedArr);
                resultMap.put("photoNum", photoNumUnReviewedArr);
                break;
            case "1":
                resultMap.put("degreeCertNum", degreeCertNumReviewedArr);
                resultMap.put("videoNum", videoNumReviewedArr);
                resultMap.put("audioNum", audioNumReviewedArr);
                resultMap.put("photoNum", photoNumReviewedArr);
                break;
            case "2":
                resultMap.put("degreeCertNum", degreeCertNumUnPassArr);
                resultMap.put("videoNum", videoNumUnPassArr);
                resultMap.put("audioNum", audioNumUnPassArr);
                resultMap.put("photoNum", photoNumUnPassArr);
                break;
            case "3":
                resultMap.put("degreeCertNum", degreeCertNumCanceledArr);
                resultMap.put("videoNum", videoNumCanceledArr);
                resultMap.put("audioNum", audioNumCanceledArr);
                resultMap.put("photoNum", photoNumCanceledArr);
                break;
            default:
                resultMap.put("degreeCertNum", new ArrayList(0));
                resultMap.put("videoNum", new ArrayList(0));
                resultMap.put("audioNum", new ArrayList(0));
                resultMap.put("photoNum", new ArrayList(0));
                break;
        }
        return resultMap;

    }




    private HashMap combineData(HashMap map1, HashMap<String,Object> map2) {
        ((ArrayList)map1.get("degreeCertNum")).add(map2.get("degreeCertNum"));
        ((ArrayList)map1.get("audioNum")).add(map2.get("audioNum"));
        ((ArrayList)map1.get("videoNum")).add(map2.get("videoNum"));
        ((ArrayList)map1.get("photoNum")).add(map2.get("photoNum"));
        return map1;
    }




    @Override
    public HashMap statisticsUserAssetDetail(String userId) {

        //查询学位证书的信息
        Query query = new Query();
        query.addCriteria(Criteria.where("ownerId").is(userId));
        query.addCriteria(Criteria.where("state").is("0"));
        long degreeCertNumUnReviewed  = mongoTemplate.count(query,DegreeCertificate.class);
        query = new Query();
        query.addCriteria(Criteria.where("ownerId").is(userId));
        query.addCriteria(Criteria.where("state").is("1"));
        long degreeCertNumReviewed  = mongoTemplate.count(query,DegreeCertificate.class);
        query = new Query();
        query.addCriteria(Criteria.where("ownerId").is(userId));
        query.addCriteria(Criteria.where("state").is("2"));
        long degreeCertNumUnPass  = mongoTemplate.count(query,DegreeCertificate.class);
        query = new Query();
        query.addCriteria(Criteria.where("ownerId").is(userId));
        query.addCriteria(Criteria.where("state").is("3"));
        long degreeCertNumCanceled = mongoTemplate.count(query,DegreeCertificate.class);


        //查询视频信息
        query = new Query();
        query.addCriteria(Criteria.where("ownerId").is(userId));
        query.addCriteria(Criteria.where("state").is("0"));
        long videoNumUnReviewed  = mongoTemplate.count(query,"video");
        query = new Query();
        query.addCriteria(Criteria.where("ownerId").is(userId));
        query.addCriteria(Criteria.where("state").is("1"));
        long videoNumReviewed  = mongoTemplate.count(query,"video");
        query = new Query();
        query.addCriteria(Criteria.where("ownerId").is(userId));
        query.addCriteria(Criteria.where("state").is("2"));
        long videoNumUnPass  = mongoTemplate.count(query,"video");
        query = new Query();
        query.addCriteria(Criteria.where("ownerId").is(userId));
        query.addCriteria(Criteria.where("state").is("3"));
        long videoNumCanceled = mongoTemplate.count(query,"video");


        //查询音频信息
        query = new Query();
        query.addCriteria(Criteria.where("ownerId").is(userId));
        query.addCriteria(Criteria.where("state").is("0"));
        long audioNumUnReviewed  = mongoTemplate.count(query,"music");
        query = new Query();
        query.addCriteria(Criteria.where("ownerId").is(userId));
        query.addCriteria(Criteria.where("state").is("1"));
        long audioNumReviewed  = mongoTemplate.count(query,"music");
        query = new Query();
        query.addCriteria(Criteria.where("ownerId").is(userId));
        query.addCriteria(Criteria.where("state").is("2"));
        long audioNumUnPass  = mongoTemplate.count(query,"music");
        query = new Query();
        query.addCriteria(Criteria.where("ownerId").is(userId));
        query.addCriteria(Criteria.where("state").is("3"));
        long audioNumCanceled = mongoTemplate.count(query,"music");


        //查询图片证书信息
        query = new Query();
        query.addCriteria(Criteria.where("ownerId").is(userId));
        query.addCriteria(Criteria.where("state").is("0"));
        long photoNumUnReviewed  = mongoTemplate.count(query,"photo");
        query = new Query();
        query.addCriteria(Criteria.where("ownerId").is(userId));
        query.addCriteria(Criteria.where("state").is("1"));
        long photoNumReviewed  = mongoTemplate.count(query,"photo");
        query = new Query();
        query.addCriteria(Criteria.where("ownerId").is(userId));
        query.addCriteria(Criteria.where("state").is("2"));
        long photoNumUnPass  = mongoTemplate.count(query,"photo");
        query = new Query();
        query.addCriteria(Criteria.where("ownerId").is(userId));
        query.addCriteria(Criteria.where("state").is("3"));
        long photoNumCanceled = mongoTemplate.count(query,"photo");

        //包装信息
        ArrayList<Long> degreeCertNumArr = new ArrayList();
        ArrayList<Long> videoCertNumArr = new ArrayList();
        ArrayList<Long> audioNumArr = new ArrayList();
        ArrayList<Long> photoNumArr = new ArrayList();

        degreeCertNumArr.add(degreeCertNumUnReviewed);
        degreeCertNumArr.add(degreeCertNumReviewed);
        degreeCertNumArr.add(degreeCertNumUnPass);
        degreeCertNumArr.add(degreeCertNumCanceled);

        videoCertNumArr.add(videoNumUnReviewed);
        videoCertNumArr.add(videoNumReviewed);
        videoCertNumArr.add(videoNumUnPass);
        videoCertNumArr.add(videoNumCanceled);

        audioNumArr.add(audioNumUnReviewed);
        audioNumArr.add(audioNumReviewed);
        audioNumArr.add(audioNumUnPass);
        audioNumArr.add(audioNumCanceled);

        photoNumArr.add(photoNumUnReviewed);
        photoNumArr.add(photoNumReviewed);
        photoNumArr.add(photoNumUnPass);
        photoNumArr.add(photoNumCanceled);

        //返回信息
        HashMap<String, Object> resultMap = new HashMap();
        resultMap.put("degreeCertNum", degreeCertNumArr);
        resultMap.put("videoCertNum", videoCertNumArr);
        resultMap.put("audioNum", audioNumArr);
        resultMap.put("photoNum", degreeCertNumArr);

        return resultMap;
    }



    public HashMap parseData2(String startTime, String endTime, String assetState, String collectionName) {
        //startTime = startTime + " 00:00:00";
        //endTime = endTime + " 23:59:59";

        GroupOperation groupOperation = null;

        // 分组操作，并对每个总条数进行统计
        switch (assetState) {
            case "-1":
                groupOperation = Aggregation.group()
                        .sum("degreeCertNumTotal").as("degreeCertNum")
                        .sum("videoNumTotal").as("videoNum")
                        .sum("audioNumTotal").as("audioNum")
                        .sum("photoNumTotal").as("photoNum");
                break;
            case "0":
                groupOperation = Aggregation.group()
                        .sum("degreeCertNumUnReviewed").as("degreeCertNum")
                        .sum("videoNumUnReviewed").as("videoNum")
                        .sum("audioNumUnReviewed").as("audioNum")
                        .sum("photoNumUnReviewed").as("photoNum");
                break;
            case "1":
                groupOperation = Aggregation.group()
                        .sum("degreeCertNumReviewed").as("degreeCertNum")
                        .sum("videoNumReviewed").as("videoNum")
                        .sum("audioNumReviewed").as("audioNum")
                        .sum("photoNumReviewed").as("photoNum");
                break;
            case "2":
                groupOperation = Aggregation.group()
                        .sum("degreeCertNumUnPass").as("degreeCertNum")
                        .sum("videoNumUnPass").as("videoNum")
                        .sum("audioNumUnPass").as("audioNum")
                        .sum("photoNumUnPass").as("photoNum");
                break;
            case "3":
                groupOperation = Aggregation.group()
                        .sum("degreeCertNumCanceled").as("degreeCertNum")
                        .sum("videoNumCanceled").as("videoNum")
                        .sum("audioNumCanceled").as("audioNum")
                        .sum("photoNumCanceled").as("photoNum");
                break;
            default:
                //异常处理
                break;

        }


        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("time").gte(startTime)),
                Aggregation.match(Criteria.where("time").lte(endTime)),
                groupOperation
        );

        // 执行操作
     AggregationResults<HashMap> aggregationResults = this.mongoTemplate.aggregate(aggregation, collectionName, HashMap.class);//先暂时从小时数据库里查询，后面前换成天，月

        System.out.println(aggregationResults.getMappedResults());

        HashMap statisticsMap = new HashMap<String, Object>();

        if (aggregationResults.getMappedResults().size() == 0) {
            statisticsMap.put("degreeCertNum", "0");
            statisticsMap.put("videoNum", "0");
            statisticsMap.put("audioNum", "0");
            statisticsMap.put("photoNum", "0");
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
    }

}
