package org.it611.das.fabric.util;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLDecoder;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.hyperledger.fabric.sdk.exception.CryptoException;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.hyperledger.fabric.sdk.exception.TransactionException;


import org.it611.das.fabric.ChaincodeManager;
import org.it611.das.fabric.FabricConfig;
import org.it611.das.fabric.bean.Chaincode;
import org.it611.das.fabric.bean.Orderers;
import org.it611.das.fabric.bean.Peers;
//import lombok.extern.slf4j.Slf4j;

public class FabricManager {

    private ChaincodeManager manager;

    private static FabricManager instance = null;

    public static FabricManager obtain()
            throws CryptoException, InvalidArgumentException, NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException, TransactionException, IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        if (null == instance) {
            synchronized (FabricManager.class) {
                if (null == instance) {
                    instance = new FabricManager();
                }
            }
        }
        return instance;
    }

    private FabricManager()
            throws CryptoException, InvalidArgumentException, NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException, TransactionException, IOException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        manager = new ChaincodeManager(getConfig());
    }

    /**
     * 获取节点服务器管理器
     *
     * @return 节点服务器管理器
     */
    public ChaincodeManager getManager() {
        return manager;
    }

    /**
     * 根据节点作用类型获取节点服务器配置
     *
     * @return 节点服务器配置
     */
    private FabricConfig getConfig() {
        FabricConfig config = new FabricConfig();
        config.setOrderers(getOrderers());
        config.setPeers(getPeers());
        //config.setChaincode(getChaincode("mychannel", "mycc", "github.com/hyperledger/fabric/demo/chaincode/go/chaincode_example02", "1.0"));
        config.setChaincode(getChaincode("mychannel", "asset02", "github.com/hyperledger/fabric/demo/chaincode/go/chaincode_example02", "1.0"));
        config.setChannelArtifactsPath(getChannleArtifactsPath());
        config.setCryptoConfigPath(getCryptoConfigPath());
        return config;
    }

    private Orderers getOrderers() {
        Orderers orderer = new Orderers();
        orderer.setOrdererDomainName("example.com");
        //orderer.addOrderer("orderer.example.com", "grpc://192.168.83.143:7050");
        orderer.addOrderer("orderer.example.com", "grpc://192.168.10.128:7050");
        //orderer.addOrderer("orderer0.example.com", "grpc://x.x.x.xx:7050");
        //orderer.addOrderer("orderer2.example.com", "grpc://x.x.x.xxx:7050");
        return orderer;
    }

    /**
     * 获取节点服务器集
     *
     * @return 节点服务器集
     */
    private Peers getPeers() {
        Peers peers = new Peers();
        peers.setOrgName("Org1MSP");
        peers.setOrgMSPID("Org1MSP");
        peers.setOrgDomainName("org1.example.com");
        peers.addPeer("peer0.org1.example.com", "peer0.org1.example.com", "grpc://192.168.10.128:7051", "grpc://192.168.10.128:7053", "http://192.168.10.128:7054");
        return peers;
    }

    /**
     * 获取智能合约
     *
     * @param channelName
     *            频道名称
     * @param chaincodeName
     *            智能合约名称
     * @param chaincodePath
     *            智能合约路径
     * @param chaincodeVersion
     *            智能合约版本
     * @return 智能合约
     */
    private Chaincode getChaincode(String channelName, String chaincodeName, String chaincodePath, String chaincodeVersion) {
        Chaincode chaincode = new Chaincode();
        chaincode.setChannelName(channelName);
        chaincode.setChaincodeName(chaincodeName);
        chaincode.setChaincodePath(chaincodePath);
        chaincode.setChaincodeVersion(chaincodeVersion);
        chaincode.setInvokeWatiTime(100000);
        chaincode.setDeployWatiTime(120000);
        return chaincode;
    }

    /**
     * 获取channel-artifacts配置路径
     *
     * @return /WEB-INF/classes/fabric/channel-artifacts/
     */
    private String getChannleArtifactsPath() {
        String directorys = FabricManager.class.getClassLoader().getResource("fabric").getFile();
        try {
            directorys = URLDecoder.decode(directorys, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        // log.debug("directorys = " + directorys);
        File directory = new File(directorys);
       // log.debug("directory = " + directory.getPath());

        return directory.getPath() + "/channel-artifacts/";
    }

    /**
     * 获取crypto-config配置路径
     *
     * @return /WEB-INF/classes/fabric/crypto-config/
     */
    private String getCryptoConfigPath() {
        String directorys = FabricManager.class.getClassLoader().getResource("fabric").getFile();
        try {
            directorys = URLDecoder.decode(directorys, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //log.debug("directorys = " + directorys);
        File directory = new File(directorys);
        //log.debug("directory = " + directory.getPath());

        return directory.getPath() + "/crypto-config/";
    }
    public static void main(String[] args) throws InvalidArgumentException, NoSuchAlgorithmException, IOException, TransactionException, NoSuchProviderException, CryptoException, InvalidKeySpecException, InterruptedException, ExecutionException, TimeoutException, ProposalException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        //log.debug("测试");
        ChaincodeManager manager = FabricManager.obtain().getManager();
        String[] arguments = new String[]{"test1", "test1"};
        Map<String, String> result = manager.invoke("addAsset", arguments);
        System.out.println(result.toString());
        //String[] argQuery = new String[]{"test60"};
        //Map<String, String> res = manager.query("query", argQuery);
        //System.out.println(res.toString());
    }
}