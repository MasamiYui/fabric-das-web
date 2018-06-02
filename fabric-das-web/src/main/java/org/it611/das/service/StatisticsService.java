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



}
