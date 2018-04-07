package org.it611.das.fabric;

import org.hyperledger.fabric_ca.sdk.exception.InvalidArgumentException;

import java.net.MalformedURLException;
import java.util.HashMap;

/**
 * 加载未使用CA模块时用户的CA证书以及私钥信息，并按照要求加载对应的组织机构节点信息
 * 考虑在下一个版本中采用静态配置的方式配置Fabrc网络信息
 */

public class FabricConfigure {
    public static String CHAINCODENAME="asset02";
    public static String CHAINCODEVERSION="1.0";
    public static String CHANNLNAME="mychannel";
    public static HashMap<String,FabricOrg> getConfigure() throws MalformedURLException, InvalidArgumentException {
        HashMap<String,FabricOrg> orgHashMap=new HashMap<>();
        FabricOrg org1=new FabricOrg("org1","Org1MSP");
        org1.addPeerLocation("peer0org1","grpc://localhost:7051");
        org1.addPeerLocation("peer1org1","grpc://localhost:7051");
        org1.addOrdererLocation("orderer","grpc://localhost:7050");
        org1.setCALocation("http://localhost:7054");
        FabricUser Adminorg1=new FabricUser("peer","Admin","Org1MSP");
        FabricUser User1org1=new FabricUser("peer","User1","Org1MSP");
        org1.addUser(Adminorg1);
        org1.addUser(User1org1);
        org1.setAdmin(Adminorg1);

        FabricOrg org2=new FabricOrg("org2","Org2MSP");
        org2.addPeerLocation("peer0org2","grpc://localhost:7051");
        org2.addPeerLocation("peer1org2","grpc://localhost:7051");
        org2.addOrdererLocation("orderer","grpc://localhost:7050");
        FabricUser Adminorg2=new FabricUser("peer","Admin","Org2MSP");
        FabricUser User1org2=new FabricUser("peer","User1","Org2MSP");
        org1.addUser(Adminorg2);
        org1.addUser(User1org2);
        org1.setAdmin(Adminorg2);

        orgHashMap.put("org1",org1);
        orgHashMap.put("org2",org2);
        return orgHashMap;
    }



}
