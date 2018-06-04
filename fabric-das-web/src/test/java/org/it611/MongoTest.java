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
}
