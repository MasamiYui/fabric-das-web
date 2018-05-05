package org.it611;

import org.apache.log4j.Logger;
import org.hyperledger.fabric.sdk.BlockEvent;
import org.hyperledger.fabric.sdk.BlockInfo;
import org.it611.das.DasApplication;
import org.it611.das.fabric.FabricClient;
import org.it611.das.fabric.LedgerRecord;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Random;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = DasApplication.class)
@WebAppConfiguration
public class FabricTest {

    private static Logger logger=Logger.getLogger(FabricTest.class);

    @Autowired
    private FabricClient client;


    /**
     * 总结：对于couchdb而言，对于相同的Key的插入不会提示存在，也不会提示错误，而是直接覆盖，也许为了更高的吞吐率？
     * 只能在链码层或Service层进行判断，放在链码层会更合适
     */

    /**
     * 插入资产
     */
    @Test
    public void TestInsert1() throws Exception {
        LedgerRecord testinfo=new LedgerRecord("test1","test1");//kv
        client.instert(client.getDefaultChannel(),testinfo);
    }

    /**
     * 测试链码查询操作
     */
    @Test
    public void TestChainCodeQuery() throws Exception {
        System.out.println(client.query(client.getDefaultChannel(), "test1"));
    }

    /**
     * 指定doc中的关键字查询
     */
    @Test
    public void TestQueryByAssetOwner() throws Exception{
        //System.out.println(client.queryAssetsByOwner(client.getDefaultChannel(),"test1"););
        String txId = "c675d3720afb410314c5d026343a7b7d98f3627a3ebde64265a50a6b2be380d2";
        BlockInfo blockInfo = client.queryByTransactionId(client.getDefaultChannel(),txId);
        System.out.println(blockInfo.getChannelId());
        System.out.println(blockInfo.getDataHash().toString());
        System.out.println(blockInfo.getBlockNumber());
    }

    @Test
    public void TestFabricEventListner() {


    }

    @Test
    public void TestAfterChannelSended() throws Exception {
        int randomNum = 50+8;
        logger.info("insert key:test"+randomNum+",value:test"+randomNum);
        LedgerRecord testinfo=new LedgerRecord("test"+randomNum,"test"+randomNum);//kv
        client.instertAndReceiveFuture(client.getDefaultChannel(),testinfo);
    }




}
