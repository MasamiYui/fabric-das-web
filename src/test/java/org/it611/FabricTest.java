package org.it611;

import org.apache.log4j.Logger;
import org.it611.das.DasApplication;
import org.it611.das.fabric.FabricClient;
import org.it611.das.fabric.LedgerRecord;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = DasApplication.class)
@WebAppConfiguration
public class FabricTest {

    private static Logger logger=Logger.getLogger(FabricTest.class);

    @Autowired
    private FabricClient client;

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
    }

}
