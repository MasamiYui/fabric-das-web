package org.it611;


import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.it611.das.DasApplication;
import org.it611.das.domain.DegreeCertificate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.mapreduce.GroupBy;
import org.springframework.data.mongodb.core.mapreduce.GroupByResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.HashMap;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DasApplication.class)
@WebAppConfiguration
public class MongoTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void Test1() {

        Query query = new Query();
        query.addCriteria(Criteria.where("ownerId").is("123124"));
        query.addCriteria(Criteria.where("state").is("3"));
        long num = mongoTemplate.count(query, "degreeCertificate");
        query = new Query();
        query.addCriteria(Criteria.where("ownerId").is("123124"));
        long num2 = mongoTemplate.count(query, "music");

        System.out.println(num2);

    }


    @Test
    public void Test2(){
/*
        GroupOperation groupOperation = null;
        groupOperation = Aggregation.group()
                .sum("degreeCertNumReviewed").as("degreeCertNum")
                .sum("videoNumReviewed").as("videoNum")
                .sum("audioNumReviewed").as("audioNum")
                .sum("photoNumReviewed").as("photoNum");



        Aggregation aggregation = Aggregation.newAggregation(
 */
/*               Aggregation.match(Criteria.where("time").gte("2018-06-01 00:00:00")),
                Aggregation.match(Criteria.where("time").lte("2018-06-04 00:00:00")),*//*

                groupOperation
        );

        // 执行操作
        AggregationResults<HashMap> aggregationResults = this.mongoTemplate.aggregate(aggregation, "statisticsPerDay", HashMap.class);//先暂时从小时数据库里查询，后面前换成天，月

        System.out.println(aggregationResults.getMappedResults());

        System.out.println(aggregationResults.getMappedResults());
*/

        /*startTime = startTime + " 00:00:00";
        endTime = endTime + " 23:59:59";*/

        // 分组操作，并对每个总条数进行统计
        GroupOperation videoGroupOperation = Aggregation.group()
                .sum("audioNumReviewed").as("audioNumReviewed")
                .sum("videoNumReviewed").as("videoNumReviewed")
                .sum("photoNumReviewed").as("photoNumReviewed")
                .sum("degreeCertNumReviewed").as("degreeCertNumReviewed");
/*        GroupOperation audioGroupOperation = Aggregation.group().sum("audioNum").as("audioNum");
        GroupOperation degreeCertGroupOperation = Aggregation.group().sum("degreeCertNum").as("degreeCertNum");
        GroupOperation photoGroupOperation = Aggregation.group().sum("photoNum").as("photoNum");*/
        // 组合条件
        //Aggregation aggregation = Aggregation.newAggregation(projectionOperation, matchOperation, groupOperation);
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("time").gte("2018-06-01 00:00:00")),
                Aggregation.match(Criteria.where("time").lte("2018-06-04 00:00:00")),
                videoGroupOperation
/*                audioGroupOperation,
                degreeCertGroupOperation,
                photoGroupOperation*/
        );

        // 执行操作
        //AggregationResults<HashMap> aggregationResults = this.mongoTemplate.aggregate(aggregation, "statisticsPerMin", HashMap.class);
        AggregationResults<HashMap> aggregationResults = this.mongoTemplate.aggregate(aggregation, "statisticsPerDay", HashMap.class);//先暂时从小时数据库里查询，后面前换成天，月

        System.out.println(aggregationResults.getMappedResults());


    }


    @Test
    public void Test3(){
        //聚合
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("time").gte("2018-05-01 00:00:00")),
                Aggregation.match(Criteria.where("time").lte("2018-05-31 23:59:59"))
        );
        List<BasicDBObject> list = mongoTemplate.aggregate(aggregation, "statisticsPerDay", BasicDBObject.class).getMappedResults();
        System.out.println(list.size());
    }


}
