package org.it611;

import org.apache.log4j.Logger;
import org.hyperledger.fabric.sdk.Channel;
import org.hyperledger.fabric.sdk.exception.CryptoException;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.it611.das.fabric.FabricApp;
import org.it611.das.fabric.FabricConfigure;
import org.it611.das.fabric.LedgerRecord;
import org.junit.Before;
import org.junit.Test;

import java.net.MalformedURLException;


public class FabricTest {


    private static Logger logger=Logger.getLogger(FabricTest.class);

    /**
     * 初始化
     */
    @Before
    public void Setup() throws InvalidArgumentException, MalformedURLException, CryptoException, org.hyperledger.fabric_ca.sdk.exception.InvalidArgumentException {
        logger.debug("--------------------------------Fabric Init--------------------------------");
        FabricApp fabricApp=new FabricApp();
        FabricApp.init();
    }



    /**
     * Fabrci插入功能测试
     */
    @Test
    public void TestInsert() throws Exception {
        logger.debug("--------------------------------FABRIC INSERT--------------------------------");
        Channel channel = FabricApp.client.newChannel(FabricConfigure.CHANNLNAME);//name:mychannel
        channel.addPeer(FabricApp.client.newPeer("peer",
                FabricConfigure.getConfigure().get("org1").getPeerLocation("peer0org1")));// grpc://localhost:7051
        channel.addOrderer(FabricApp.client.newOrderer("org1",
                FabricConfigure.getConfigure().get("org1").getOrdererLocation("orderer"))); //grpc://localhost:7050
        channel.initialize();
        LedgerRecord testinfo=new LedgerRecord("B","123123");
        FabricApp.instert(channel,testinfo);
        logger.debug("--------------------------------INSERT END--------------------------------");
    }


    /**
     * 测试链码查询操作
     */
    @Test
    public void TestEpointChainCodeQuery() throws Exception {
        logger.debug("--------------------------------FABRIC QUERY--------------------------------");
        Channel channel = FabricApp.client.newChannel(FabricConfigure.CHANNLNAME);//name:mychannel
        channel.addPeer(FabricApp.client.newPeer("peer",
                FabricConfigure.getConfigure().get("org1").getPeerLocation("peer0org1")));// grpc://localhost:7051
        channel.addOrderer(FabricApp.client.newOrderer("org1",
                FabricConfigure.getConfigure().get("org1").getOrdererLocation("orderer"))); //grpc://localhost:7050
        channel.initialize();
        FabricApp.query(channel, "B");
    }

    

}
