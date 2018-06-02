package org.it611;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;
import org.bson.Document;

import com.mongodb.spark.MongoSpark;
import com.mongodb.spark.rdd.api.java.JavaMongoRDD;

public final class ReadFromMongoDB {

/*    public static void main(final String[] args) throws InterruptedException {
        System.setProperty("hadoop.home.dir", "C:\\Users\\YUI\\Desktop\\hadooponwindows");
        SparkSession spark = SparkSession.builder()
                //.master("spark://192.168.11.130:50002")
                .master("local")
                .appName("test")
                .config("spark.mongodb.input.uri", "mongodb://192.168.11.130/spark.input")
                .config("spark.mongodb.output.uri", "mongodb://192.168.11.130/spark.output")
                .getOrCreate();

        // Create a JavaSparkContext using the SparkSession's SparkContext object
        JavaSparkContext jsc = new JavaSparkContext(spark.sparkContext());

        *//*Start Example: Read data from MongoDB************************//*
        JavaMongoRDD<Document> rdd = MongoSpark.load(jsc);
        *//*End Example**************************************************//*

        // Analyze data from MongoDB
        System.out.println(rdd.count());
        System.out.println(rdd.first().toJson());

        jsc.close();

    }*/
}