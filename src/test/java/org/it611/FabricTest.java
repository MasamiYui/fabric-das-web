package org.it611;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import org.apache.log4j.Logger;
import org.hyperledger.fabric.sdk.Channel;
import org.hyperledger.fabric.sdk.exception.CryptoException;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.it611.das.fabric.FabricApp;
import org.it611.das.fabric.FabricConfigure;
import org.it611.das.fabric.LedgerRecord;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.net.MalformedURLException;


public class FabricTest {

    private static Logger logger=Logger.getLogger(FabricTest.class);

    /**
     * 基本资产
     */
    public class BaseAsset{

    }

    /**
     * 学生证资产定义
     * 参考：重庆大学学生证信息
     */
    public class StuIdCard extends BaseAsset{
        public String schoolName;//学校名称
        public String id;//学号
        public String name;//姓名
        public String sex;//性别
        public String dataOfBirth;//出生年月
        public String idCard;//身份证号码
        public String lengthOfSchooling;//学院
        public String college;//学院
        public String address;//籍贯
        public String schoolTime;//入校时间
        public String timeOfIssue;//发证时间

        public StuIdCard(String schoolName, String id, String name, String sex, String dataOfBirth, String idCard, String lengthOfSchooling, String college, String address, String schoolTime, String timeOfIssue) {
            this.schoolName = schoolName;
            this.id = id;
            this.name = name;
            this.sex = sex;
            this.dataOfBirth = dataOfBirth;
            this.idCard = idCard;
            this.lengthOfSchooling = lengthOfSchooling;
            this.college = college;
            this.address = address;
            this.schoolTime = schoolTime;
            this.timeOfIssue = timeOfIssue;
        }

        public String getSchoolName() {
            return schoolName;
        }

        public void setSchoolName(String schoolName) {
            this.schoolName = schoolName;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getDataOfBirth() {
            return dataOfBirth;
        }

        public void setDataOfBirth(String dataOfBirth) {
            this.dataOfBirth = dataOfBirth;
        }

        public String getIdCard() {
            return idCard;
        }

        public void setIdCard(String idCard) {
            this.idCard = idCard;
        }

        public String getLengthOfSchooling() {
            return lengthOfSchooling;
        }

        public void setLengthOfSchooling(String lengthOfSchooling) {
            this.lengthOfSchooling = lengthOfSchooling;
        }

        public String getCollege() {
            return college;
        }

        public void setCollege(String college) {
            this.college = college;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getSchoolTime() {
            return schoolTime;
        }

        public void setSchoolTime(String schoolTime) {
            this.schoolTime = schoolTime;
        }

        public String getTimeOfIssue() {
            return timeOfIssue;
        }

        public void setTimeOfIssue(String timeOfIssue) {
            this.timeOfIssue = timeOfIssue;
        }
    }

    /**
     * 资产定义
     */
    public class Asset {
        public String assetId;//资产Id
        public String assetType;//资产类型
        public String assetOwner;//资产拥有者
        public String assetName;//资产名称
        public String submitTime;//提交时间
        public BaseAsset assetDetail;//资产详细信息

        public Asset(String assetId, String assetType, String assetOwner, String assetName, String submitTime, BaseAsset assetDetail) {
            this.assetId = assetId;
            this.assetType = assetType;
            this.assetOwner = assetOwner;
            this.assetName = assetName;
            this.submitTime = submitTime;
            this.assetDetail = assetDetail;
        }

        public String getAssetId() {
            return assetId;
        }

        public void setAssetId(String assetId) {
            this.assetId = assetId;
        }

        public String getAssetType() {
            return assetType;
        }

        public void setAssetType(String assetType) {
            this.assetType = assetType;
        }

        public String getAssetOwner() {
            return assetOwner;
        }

        public void setAssetOwner(String assetOwner) {
            this.assetOwner = assetOwner;
        }

        public String getAssetName() {
            return assetName;
        }

        public void setAssetName(String assetName) {
            this.assetName = assetName;
        }

        public String getSubmitTime() {
            return submitTime;
        }

        public void setSubmitTime(String submitTime) {
            this.submitTime = submitTime;
        }

        public BaseAsset getAssetDetail() {
            return assetDetail;
        }

        public void setAssetDetail(BaseAsset assetDetail) {
            this.assetDetail = assetDetail;
        }
    }


    //---------------------------------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------------------------

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
     * 资产对象转JSON字符串
     * @param
     * @return
     */
    public String AssetObjectToJsonStr(Asset asset) {
        return "";
    }


    /**
     * 总结：对于couchdb而言，对于相同的Key的插入不会提示存在，也不会提示错误，而是直接覆盖，也许为了更高的吞吐率？
     * 只能在链码层或Service层进行判断，放在链码层会更合适
     */



    /**
     * 插入资产
     */
    @Test
    public void TestInsert1() throws Exception {
        logger.debug("--------------------------------FABRIC INSERT--------------------------------");
        Channel channel = FabricApp.client.newChannel(FabricConfigure.CHANNLNAME);//name:mychannel
        channel.addPeer(FabricApp.client.newPeer("peer",
                FabricConfigure.getConfigure().get("org1").getPeerLocation("peer0org1")));// grpc://localhost:7051
        channel.addOrderer(FabricApp.client.newOrderer("org1",
                FabricConfigure.getConfigure().get("org1").getOrdererLocation("orderer"))); //grpc://localhost:7050
        channel.initialize();

        StuIdCard stuIdCard = new StuIdCard("重庆邮电大学","S161231012","新垣結衣",
        "女", "1988-06-11", "430902199901011234","3", "软件学院",
                "日本冲绳", "2016-03-03", "2016-03-15");
        Asset assetObj = new Asset("aef01013","1","430902199901011234",
                "新垣結衣的身份证","2018-04-06",stuIdCard);


        ObjectMapper mapper = new ObjectMapper();
        String assetStr = mapper.writeValueAsString(assetObj);
      //  System.out.println(assetStr);//print msg

        LedgerRecord testinfo=new LedgerRecord("aef01013",assetStr);
        FabricApp.instert(channel,testinfo);
        logger.debug("--------------------------------INSERT END--------------------------------");
    }



    /**
     * 插入资产
     */
    @Test
    public void TestInsert2() throws Exception {
        logger.debug("--------------------------------FABRIC INSERT--------------------------------");
        Channel channel = FabricApp.client.newChannel(FabricConfigure.CHANNLNAME);//name:mychannel
        channel.addPeer(FabricApp.client.newPeer("peer",
                FabricConfigure.getConfigure().get("org1").getPeerLocation("peer0org1")));// grpc://localhost:7051
        channel.addOrderer(FabricApp.client.newOrderer("org1",
                FabricConfigure.getConfigure().get("org1").getOrdererLocation("orderer"))); //grpc://localhost:7050
        channel.initialize();

        StuIdCard stuIdCard = new StuIdCard("重庆大学","S161231034","长泽雅美",
                "女", "1988-06-11", "430902199901011234","3", "软件学院",
                "日本冲绳", "2016-03-03", "2016-03-15");
        Asset assetObj = new Asset("aef01013","1","430902199901011234",
                "新垣結衣的身份证","2018-04-06",stuIdCard);


        ObjectMapper mapper = new ObjectMapper();
        String assetStr = mapper.writeValueAsString(assetObj);
        //  System.out.println(assetStr);//print msg

        LedgerRecord testinfo=new LedgerRecord("aef01014",assetStr);
        FabricApp.instert(channel,testinfo);
        logger.debug("--------------------------------INSERT END--------------------------------");
    }


    /**
     * 插入资产
     */
    @Test
    public void TestInsert3() throws Exception {
        logger.debug("--------------------------------FABRIC INSERT--------------------------------");
        Channel channel = FabricApp.client.newChannel(FabricConfigure.CHANNLNAME);//name:mychannel
        channel.addPeer(FabricApp.client.newPeer("peer",
                FabricConfigure.getConfigure().get("org1").getPeerLocation("peer0org1")));// grpc://localhost:7051
        channel.addOrderer(FabricApp.client.newOrderer("org1",
                FabricConfigure.getConfigure().get("org1").getOrdererLocation("orderer"))); //grpc://localhost:7050
        channel.initialize();

        StuIdCard stuIdCard = new StuIdCard("电子科技大学","S161231000","滨崎步",
                "女", "1988-06-11", "430902199901011234","3", "软件学院",
                "日本冲绳", "2016-03-03", "2016-03-15");
        Asset assetObj = new Asset("aef01013","1","430902199901011234",
                "新垣結衣的身份证","2018-04-06",stuIdCard);


        ObjectMapper mapper = new ObjectMapper();
        String assetStr = mapper.writeValueAsString(assetObj);
        //  System.out.println(assetStr);//print msg

        LedgerRecord testinfo=new LedgerRecord("aef01014",assetStr);
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
        FabricApp.query(channel, "aef01013");
    }



}
