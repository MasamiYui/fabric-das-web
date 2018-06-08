package org.it611.das.service;



import com.fasterxml.jackson.databind.ObjectMapper;
import org.hyperledger.fabric.sdk.BlockInfo;
import org.hyperledger.fabric.sdk.Channel;
import org.hyperledger.fabric.sdk.TransactionInfo;
import org.it611.das.fabric.ChaincodeManager;
import org.it611.das.fabric.util.FabricManager;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
public class RecoveryService {


    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    RabbitTemplate rabbitTemplate;

    //用线程池来进行数据恢复
    private final ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);

    //管道
    ChaincodeManager manager =null;
    Channel channel = null;
    {
        try {
           manager = FabricManager.obtain().getManager();
           channel = manager.getChannelInstance();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public String startRecovery(String startBlock, String endBlock) {

        //1.如果存在备份数据库，就先删除

        //2.设置任务，将任务丢往消息队列
        for(int i =Integer.valueOf(startBlock); i<=Integer.valueOf(endBlock); i++){
            Map<String, Object> map = new HashMap<>();
            map.put("chennelId", "channel02");
            map.put("chaincode","asset02");
            map.put("blockNumber",i);
            System.out.println(map);
            rabbitTemplate.convertAndSend("exchange.direct","data.recovery",map);
        }
        return "ok";
    }


    /**
     * 如果队列中没有恢复消息，则不会执行数据库的恢复操作
     * @param taskInfo
     */
    @RabbitListener(queues = "data.recovery")
    public void receive(HashMap taskInfo){

        //for(int i=0; i<10; i++){
        //    final int index = i;
            //获取任务放到任务执行池中处理
            fixedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    try {

                        //执行数据库恢复的任务
                        System.out.printf("================Thread:=====================\n");
                        //解析需要处理的任务
                        int blockNumber = (int)taskInfo.get("blockNumber");

                        //获取BlockInfo
                        BlockInfo blockInfo = channel.queryBlockByNumber(blockNumber);

                        for (Iterator<BlockInfo.EnvelopeInfo> iterator = blockInfo.getEnvelopeInfos().iterator(); iterator.hasNext();){

                            BlockInfo.EnvelopeInfo endorserInfo= iterator.next();
                            String transactionId = endorserInfo.getTransactionID();//获取TransactionId
                            TransactionInfo transactionInfo = channel.queryTransactionByID(transactionId);//获取Tranaction信息
                            String payloadStr = transactionInfo.getProcessedTransaction().getTransactionEnvelope().getPayload().toStringUtf8();//获取payload信息
                            HashMap dataMap = parsePayload(blockNumber, payloadStr);//通过正则表达式对有用信息进行匹配，并对Proposal中的KV进行解析
                            //String key = (String)dataMap.get("key");
                            //HashMap valueMap = (HashMap)dataMap.get("value");

                            //由于不能根据Id进行区分是什么类型的资产，所有需要利用valueMap中的字段进行一个判断
                            if(dataMap!=null){
                                if(dataMap.containsKey("degreeConferringUnit")){//如果是学历证书，这里取一个只有学历证书特有的字段，如果有，则为学历证书的资产

                                    //此时可以将其放到另外的管道中进行处理，也可以直接在这里处理，由于数量较少，就先直接在这里进行处理

                                    mongoTemplate.save(dataMap,"degreeCertification_bak");
                                }else if(dataMap.containsKey("author")){//TODO:key必须代表资产类型，否则因为无法判断音频、视频、图片无法判断

                                    mongoTemplate.save(dataMap,"other_bak");
                                }else {
                                    //不处理
                                    continue;
                                }
                            }


                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            });

        }
//   / }


    //payload解析方法
    private HashMap parsePayload(int blockNumber, String payloadStr) throws IOException {

        HashMap<String, Object> resultMap = new HashMap();
        String regex = "Success endorse a asset,k:\\d+,v:\\{(.)+\\}";//正则匹配
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(payloadStr);
        StringBuffer buffer = new StringBuffer();
        while (matcher.find()) {
            buffer.append(matcher.group());
        }
        //解析V
        String valueMsg = buffer.toString();
        //目前KEY的位数为20位
        int keyLen = 20;
        //prefix = Success endorse a asset,k: 长度为：26位
        int prefixLen = 26;
        int otherLen = 3;
        try{
            String key = valueMsg.substring(prefixLen, prefixLen + keyLen);
            String value = valueMsg.substring(prefixLen+keyLen+otherLen, valueMsg.length());
            ObjectMapper mapper = new ObjectMapper();
            resultMap = mapper.readValue(value, HashMap.class);
            resultMap.put("assetId", key);
/*            resultMap.put("key", key);
            resultMap.put("value", valueMap);*/

            System.out.printf("success. blockNumber:%d, key:%s, value:%s", blockNumber, key, resultMap);

        }catch (Exception e){
            System.out.printf("error. blockNumber:%d", blockNumber);
        }


        return resultMap;
    }


}

