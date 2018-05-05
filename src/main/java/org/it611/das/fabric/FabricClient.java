package org.it611.das.fabric;

import org.apache.log4j.Logger;
import org.hyperledger.fabric.sdk.*;
import org.hyperledger.fabric.sdk.exception.CryptoException;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.hyperledger.fabric.sdk.exception.TransactionException;
import org.hyperledger.fabric.sdk.security.CryptoSuite;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * 实现封装一些超级账本的操作方法，未结合使用Fabric-CA模块
 */
@Component
public class FabricClient {
    private  Logger logger = Logger.getLogger(FabricClient.class);
    private FabricConfigure configure;

    private HFClient client;
    private CryptoSuite cs;
    private HashMap<String, FabricOrg> orgHashMap;
    private ChaincodeID cid;
    private User peer0org1;
    private Channel defaultChannel;

    private Map<String, byte[]> transientMap;


    public FabricClient() throws InvalidArgumentException, MalformedURLException, CryptoException, org.hyperledger.fabric_ca.sdk.exception.InvalidArgumentException, TransactionException {
        configure = new FabricConfigure();
        cs = CryptoSuite.Factory.getCryptoSuite();
        client = HFClient.createNewInstance();
        client.setCryptoSuite(cs);
        cid = ChaincodeID.newBuilder().setName(configure.getChaincodeName()).setVersion(configure.getChaincodeVersion()).build();
        orgHashMap = configure.getOrgHashMap();
        peer0org1 = orgHashMap.get("org1").getAdmin();
        client.setUserContext(peer0org1);
        defaultChannel = createDefaultChannel();
        transientMap = getDefaultTransientMap();
    }

    //返回默认的管道
    public Channel getDefaultChannel() {
        return defaultChannel;
    }

    private Map<String,byte[]> getDefaultTransientMap() {
        Map<String, byte[]> tm = new HashMap<>();
        tm.put("HyperLedgerFabric", "TransactionProposalRequest:JavaSDK".getBytes(UTF_8));
        tm.put("method", "TransactionProposalRequest".getBytes(UTF_8));
        tm.put("result", ":)".getBytes(UTF_8));
        return tm;
    }

    /**
     * 封装一个默认的channel
     */
    public Channel createDefaultChannel() throws InvalidArgumentException, MalformedURLException, org.hyperledger.fabric_ca.sdk.exception.InvalidArgumentException, TransactionException {

        Channel channel = client.newChannel(configure.getChannelName());//name:mychannel
        channel.addPeer(client.newPeer("peer0org1",
                configure.getOrgHashMap().get("org1").getPeerLocation("peer0org1")));
        channel.addOrderer(client.newOrderer("org1",
                configure.getOrgHashMap().get("org1").getOrdererLocation("orderer")));
        System.out.println( configure.getOrgHashMap().get("org1").getPeerLocation("peer0org1"));
        channel.addEventHub(client.newEventHub("peer0org1", configure.getOrgHashMap().get("org1").getPeerLocation("peer0org1")));
        //EventHub eventHub = new EventHub("peer0.org1.example.com", configure.getOrgHashMap().get("org1").getPeerLocation("peer0org1")),);
        //channel.addEventHub();
        channel.initialize();
        return channel;
    }

    /**
     * 初始化超级账本的客户端等相关属性
     */
    public void init() throws CryptoException, InvalidArgumentException, MalformedURLException, org.hyperledger.fabric_ca.sdk.exception.InvalidArgumentException {
        client = HFClient.createNewInstance();
        client.setCryptoSuite(cs);
        orgHashMap = configure.getOrgHashMap();
        peer0org1 = orgHashMap.get("org1").getAdmin();
        client.setUserContext(peer0org1);
    }

    /**
     * 根据给定的数据调用链码写入账本中,KV形式
     */
    public ProposalResponse instert(Channel channel, LedgerRecord record) throws Exception {
        ProposalResponse prsp = null;
        TransactionProposalRequest req = client.newTransactionProposalRequest();
        req.setChaincodeID(cid);
        req.setFcn("addAsset");
        req.setArgs(record.toStringArray());
        req.setTransientMap(getDefaultTransientMap());
        Collection<ProposalResponse> resps = channel.sendTransactionProposal(req);
        for (ProposalResponse res: resps) {
            prsp = res;
        }
        CompletableFuture<BlockEvent.TransactionEvent> future = channel.sendTransaction(resps);//管道发送数据
        if (prsp.getStatus() != ChaincodeResponse.Status.SUCCESS) {//如果提议失败，抛出异常，结束本次交易
            throw new Exception();
        }
        return prsp;
    }


    /**
     * 根据给定的数据调用链码写入账本中,KV形式
     */
    public ProposalResponse instertAndReceiveFuture(Channel channel, LedgerRecord record) throws Exception {
        ProposalResponse prsp = null;
        TransactionProposalRequest req = client.newTransactionProposalRequest();
        req.setChaincodeID(cid);
        req.setFcn("addAsset");
        req.setArgs(record.toStringArray());
        req.setTransientMap(getDefaultTransientMap());
        Collection<ProposalResponse> resps = channel.sendTransactionProposal(req);
        for (ProposalResponse res: resps) {
            prsp = res;
        }
        CompletableFuture<BlockEvent.TransactionEvent> future = channel.sendTransaction(resps);//管道发送数据
        if (prsp.getStatus() != ChaincodeResponse.Status.SUCCESS) {//如果提议失败，抛出异常，结束本次交易
            throw new Exception();
        }

        future.thenApply(transactionEvent -> {
            if(transactionEvent.isValid()) {
                logger.info("success send transaction to order. Transaction id:"+ transactionEvent.getTransactionID());
            }else {
                logger.info("fail to send transaction to order.");
            }
            //channel.shutdown(true);
            return transactionEvent.getTransactionID();
        }).get(60, TimeUnit.SECONDS);
        return prsp;
    }




    /**
    /**
     * 实现根据给定的Key查询数据
     */
    public String query(Channel channel, String key) throws Exception {
        QueryByChaincodeRequest req = client.newQueryProposalRequest();
        ChaincodeID cid = ChaincodeID.newBuilder().setName(configure.getChaincodeName()).setVersion(configure.getChaincodeVersion()).build();
        req.setChaincodeID(cid);
        req.setFcn("query");
        req.setArgs(new String[]{key});
        //System.out.println("Querying for " + key);
        Collection<ProposalResponse> resps = channel.queryByChaincode(req);
        for (ProposalResponse resp : resps) {
            String payload = new String(resp.getChaincodeActionResponsePayload());
            //logger.debug("response: " + payload);
            //System.out.println(payload);
            /**
             * TODO 之后再修改
             */
            return payload;
        }
        return null;
    }

    /**
     * 查询assetOwner
     */
    public void queryAssetsByOwner(Channel channel, String key) throws Exception {
        QueryByChaincodeRequest req = client.newQueryProposalRequest();
        ChaincodeID cid = ChaincodeID.newBuilder().setName(configure.getChaincodeName()).setVersion(configure.getChaincodeVersion()).build();
        req.setChaincodeID(cid);
        req.setFcn("queryAssetsByOwner");
        req.setArgs(new String[]{key});
        //System.out.println("Querying for " + key);
        Collection<ProposalResponse> resps = channel.queryByChaincode(req);
        for (ProposalResponse resp : resps) {
            String payload = new String(resp.getChaincodeActionResponsePayload());
            logger.debug("response: " + payload);
            //System.out.println(payload);
        }
    }

    /**
     * 实现根据指定Key对Value进行修改
     */
    public void update(Channel channel, LedgerRecord record) throws Exception {
        TransactionProposalRequest req = client.newTransactionProposalRequest();
        req.setChaincodeID(cid);
        req.setFcn("updatekv");
        req.setArgs(record.toStringArray());
        //TODO 该段代码必须调用，但是未在官方的代码中找到相关的代码说明
        Map<String, byte[]> tm2 = new HashMap<>();
        tm2.put("HyperLedgerFabric", "TransactionProposalRequest:JavaSDK".getBytes(UTF_8));
        tm2.put("method", "TransactionProposalRequest".getBytes(UTF_8));
        tm2.put("result", ":)".getBytes(UTF_8));
        req.setTransientMap(tm2);
        Collection<ProposalResponse> resps = channel.sendTransactionProposal(req);
        for (ProposalResponse resp : resps) {
            String payload = new String(resp.getChaincodeActionResponsePayload());
            logger.debug("response: " + payload);
        }
        channel.sendTransaction(resps);
    }

    /**
     * 实现根据指定Key对state进行删除
     */
    public void delete(Channel channel, String key) throws Exception {
        TransactionProposalRequest req = client.newTransactionProposalRequest();
        req.setChaincodeID(cid);
        req.setFcn("delkv");
        req.setArgs(new String[]{key});
        Map<String, byte[]> tm2 = new HashMap<>();
        tm2.put("HyperLedgerFabric", "TransactionProposalRequest:JavaSDK".getBytes(UTF_8));
        tm2.put("method", "TransactionProposalRequest".getBytes(UTF_8));
        tm2.put("result", ":)".getBytes(UTF_8));
        req.setTransientMap(tm2);
        Collection<ProposalResponse> resps = channel.sendTransactionProposal(req);
        for (ProposalResponse resp : resps) {
            String payload = new String(resp.getChaincodeActionResponsePayload());
            logger.debug("response: " + payload);
        }
        channel.sendTransaction(resps);
    }

    /**
     * 根据TransactionId查询信息
     */
    public BlockInfo queryByTransactionId(Channel channel, String transactionId) throws ProposalException, InvalidArgumentException {
        //client..queryBlockByTransactionID("c675d3720afb410314c5d026343a7b7d98f3627a3ebde64265a50a6b2be380d2");
        return channel.queryBlockByTransactionID(transactionId);
    }

    /**
     * 监听Event
     */
/*    public void listenEvent() {
        client.newEventHub()
    }*/
}