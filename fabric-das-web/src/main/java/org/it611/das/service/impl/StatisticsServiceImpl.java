package org.it611.das.service.impl;

import com.mongodb.BasicDBObject;
import org.it611.das.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;


@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    private MongoTemplate mongoTemplate;


    /**
     *根据时间段，对各类资产数量进行统计
     */
    @Override
    public HashMap statisticsAssetTotal(String startTime, String endTime) {

        // 返回的字段
        //ProjectionOperation projectionOperation = Aggregation.project("degreeCertNum", "audioNum", "videoNum", "photoNum");

        // 日期条件
        //Criteria operator = Criteria.where("submitTime").gte(startTime).where("submitTime").lt(endTime);
        //MatchOperation matchOperation = Aggregation.match(operator);

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

        HashMap queryMap = aggregationResults.getMappedResults().get(0);
        HashMap statisticsMap = new HashMap<String, Object>();
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
        List<BasicDBObject> list = mongoTemplate.aggregate(aggregation , assetType,  BasicDBObject.class).getMappedResults();
        return list.size();//TODO：感觉不是统计count的最好方法
    }


}
