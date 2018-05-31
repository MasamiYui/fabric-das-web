package org.it611.das.service;


import java.util.HashMap;

/**
 * 统计服务
 */
public interface StatisticsService {

    //根据时间范围统计某一资产个数
    int statisticsAssetTotalByConditions(Class assetType, String startTime, String endTime, String ownerId);

    //根据时间范围选择，各类资产所占百分比(因为数据实在太少，从按天统计的数据库里，归并数据，进行统计)
    HashMap statisticsAssetTotal(String startTime, String endTime);

    HashMap statisticsAssetState(Class assetType);

    HashMap statisticsAssetTrend(String startTime, String endTime, String assetType);//根据时间段查询各类资产的审理数量趋势（yyyy年-mm月—yyyy年-mm月）

    HashMap statisticsAssetTrend(String time, String assetType);//查询某个月的资产审理数量趋势



}
