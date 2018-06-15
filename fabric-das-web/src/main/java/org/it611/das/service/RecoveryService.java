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

    //用线程池来接收任务，进行数据恢复
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
        for(int i =Integer.valueOf(startBlock); i<=Integer.valueOf(endBlock)-1; i++){
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

            //从任务队列中获取恢复任务
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


                            //HashMap valueMap = (HashMap)dataMap.get("value");

                            //由于不能根据Id进行区分是什么类型的资产，所有需要利用valueMap中的字段进行一个判断
                            if(dataMap!=null){
                                String key = (String)dataMap.get("key");
                                HashMap value = (HashMap) dataMap.get("value");
                                System.out.println("key:"+key);
                                System.out.println("value"+value);

                                if(key.contains("xlzs")){//如果是学历证书，则id包含xlzs这个字符串

                                    //此时可以将其放到另外的管道中进行处理，也可以直接在这里处理，由于数量较少，就先直接在这里进行处理

                                    mongoTemplate.save(value,"recovery_degreeCertification");//学历证书资产
                                }else if(key.contains("syxxzl")){
                                    mongoTemplate.save(value,"recovery_syxxzl");//实用新型专利资产
                                }else if(key.contains("jsz")){
                                    mongoTemplate.save(value, "recovery_drivingLicence");//驾驶证资产
                                    System.out.println("save+1");
                                    mongoTemplate.save(value, "recovery_audio");//视频资产
                                }else if(key.contains("sp")){
                                    mongoTemplate.save(value, "recovery_video");//音频资产
                                }else if(key.contains("tp")){
                                    mongoTemplate.save(value, "recovery_image");//图片资产
                                }else{
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
        String regex = "Success endorse a asset,k:\\w+,v:\\{(.)+\\}";//正则匹配
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(payloadStr);

        //System.out.println("payloadStr:"+payloadStr);
        StringBuffer buffer = new StringBuffer();
        while (matcher.find()) {
            buffer.append(matcher.group());
        }
        //解析V
        String valueMsg = buffer.toString();
        //System.out.println("valueMsg:"+valueMsg);


        //目前KEY的位数为20位
        //int keyLen = 20;
        //prefix = Success endorse a asset,k: 长度为：26位
        //int prefixLen = 26;
        //int otherLen = 3;
        try{
            //key的取值为index("k:")后三位，到下一逗号截至
            int pos1 = valueMsg.indexOf("k:")+2;
            int pos2 = valueMsg.indexOf(",", pos1);
           // System.out.println("key:");
            String key = valueMsg.substring(pos1, pos2);
            String value = valueMsg.substring(pos2+3, valueMsg.length());

            //System.out.println("key:"+key);
            //System.out.println("value:"+value);

            ObjectMapper mapper = new ObjectMapper();
            HashMap<String, Object> dataMap = mapper.readValue(value, HashMap.class);
//          resultMap.put("assetId", key);
            resultMap.put("key", key);
            resultMap.put("value", dataMap);

            //System.out.printf("success. blockNumber:%d, key:%s, value:%s", blockNumber, key, resultMap);

        }catch (Exception e){
            System.out.printf("error. blockNumber:%d", blockNumber);
        }


        return resultMap;
    }


}

