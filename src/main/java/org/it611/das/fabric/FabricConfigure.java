package org.it611.das.fabric;

import org.springframework.stereotype.Component;
import java.util.HashMap;

@Component
public class FabricConfigure {
    public final String DEFAULT_IP = "192.168.10.128";
    public String chaincodeName;
    public String chaincodeVersion;
    public String channelName;
    public HashMap<String,FabricOrg> orgHashMap;

    public String getChaincodeName() {
        return chaincodeName;
    }

    public void setChaincodeName(String chaincodeName) {
        this.chaincodeName = chaincodeName;
    }

    public String getChaincodeVersion() {
        return chaincodeVersion;
    }

    public void setChaincodeVersion(String chaincodeVersion) {
        this.chaincodeVersion = chaincodeVersion;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public HashMap<String, FabricOrg> getOrgHashMap() {
        return orgHashMap;
    }

    public void setOrgHashMap(HashMap<String, FabricOrg> orgHashMap) {
        this.orgHashMap = orgHashMap;
    }

    /**
     * 默认构造函数
     */
    public FabricConfigure() {
        this.chaincodeName = "asset02";
        this.chaincodeVersion = "1.0";
        this.channelName = "mychannel";
        this.orgHashMap = getDefaultOrgHashMap();
    }

    /**
     *提供的一个默认的Org配置表
     */
    public HashMap<String,FabricOrg> getDefaultOrgHashMap(){
        HashMap<String,FabricOrg> orgHashMap=new HashMap<>();
        FabricOrg org1=new FabricOrg("org1","Org1MSP");
        org1.addPeerLocation("peer0org1","grpc://"+DEFAULT_IP+":7051");
        org1.addPeerLocation("peer1org1","grpc://"+DEFAULT_IP+":7051");
        org1.addOrdererLocation("orderer","grpc://"+DEFAULT_IP+":7050");
        org1.setCALocation("http://"+DEFAULT_IP+":7054");
        FabricUser Adminorg1=new FabricUser("peer","Admin","Org1MSP");
        FabricUser User1org1=new FabricUser("peer","User1","Org1MSP");
        org1.addUser(Adminorg1);
        org1.addUser(User1org1);
        org1.setAdmin(Adminorg1);

        FabricOrg org2=new FabricOrg("org2","Org2MSP");
        org2.addPeerLocation("peer0org2","grpc://"+DEFAULT_IP+":7051");
        org2.addPeerLocation("peer1org2","grpc://"+DEFAULT_IP+":7051");
        org2.addOrdererLocation("orderer","grpc://"+DEFAULT_IP+":7050");
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
