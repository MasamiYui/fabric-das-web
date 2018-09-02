package org.it611;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bouncycastle.util.encoders.Hex;
import org.hyperledger.fabric.sdk.BlockInfo;
import org.hyperledger.fabric.sdk.Channel;
import org.hyperledger.fabric.sdk.exception.CryptoException;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.hyperledger.fabric.sdk.exception.TransactionException;
import org.it611.das.DasApplication;
import org.it611.das.fabric.ChaincodeManager;
import org.it611.das.fabric.util.FabricManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DasApplication.class)
public class RabbitMQTest {

    @Autowired
    RabbitTemplate rabbitTemplate;


    @Test
    public void contextLoads(){

        for(int i =1; i<61; i++){
            Map<String, Object> map = new HashMap<>();
            map.put("chennelId", "channel02");
            map.put("chaincode","asset02");
            map.put("blockNumber",i);
            System.out.println(map);
            rabbitTemplate.convertAndSend("exchange.direct","data.recovery",map);
        }


    }

    @Test
    public void recv() {

        HashMap msgMap = (HashMap)rabbitTemplate.receiveAndConvert("data.recovery");
        System.out.println(msgMap);
    }



    @Test
    public void Test3() throws IOException, NoSuchAlgorithmException, InvocationTargetException, NoSuchMethodException, InstantiationException, InvalidKeySpecException, CryptoException, InvalidArgumentException, IllegalAccessException, NoSuchProviderException, TransactionException, ClassNotFoundException, ProposalException {
        ChaincodeManager manager = FabricManager.obtain().getManager();
        Channel channel = manager.getChannelInstance();
        //System.out.println(channel.queryTransactionByID("7be99dbc18aaf496a0278bd25678d37f0192b2d518932269c0e5f30389db9e67").getProcessedTransaction().getTransactionEnvelope().getPayload().toStringUtf8());
        String payloadStr = channel.queryTransactionByID("7be99dbc18aaf496a0278bd25678d37f0192b2d518932269c0e5f30389db9e67").getProcessedTransaction().getTransactionEnvelope().getPayload().toStringUtf8();
        System.out.println(payloadStr);
        String regex = "Success endorse a asset,k:\\d+,v:\\{(.)+\\}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(payloadStr);
        StringBuffer buffer = new StringBuffer();
        //System.out.println(matcher.group());
        while (matcher.find()) {
            buffer.append(matcher.group());
        }
        System.out.println(buffer.toString());

        /*while (matcher.find()) {
            buffer.append(matcher.group());
        }

        System.out.println("匹配结果：=====》》》");
        System.out.println(buffer);
        //解析V
        String result = buffer.toString();
        String preifx = "Success endorse a asset,k:";
        int prefixLen = preifx.length();
        int keyLen = "20180521201647178689".length();
        String key = result.substring(prefixLen, prefixLen + keyLen);
        System.out.println(key);
        String value = result.substring(prefixLen+keyLen+3, result.length());
        System.out.println(value);
        ObjectMapper mapper = new ObjectMapper();
        HashMap dataNap = mapper.readValue(value, HashMap.class);
        System.out.println(dataNap);
        System.out.println(dataNap.size());
        System.out.println(dataNap.containsKey("degree_name"));*/

    }


    @Test
    public void getTreansactionId() throws IOException, NoSuchAlgorithmException, InvocationTargetException, NoSuchMethodException, InstantiationException, InvalidKeySpecException, CryptoException, InvalidArgumentException, IllegalAccessException, NoSuchProviderException, TransactionException, ClassNotFoundException, ProposalException {

        ChaincodeManager manager = FabricManager.obtain().getManager();
        Channel channel = manager.getChannelInstance();

        BlockInfo blockInfo = channel.queryBlockByNumber(10);
        for (Iterator<BlockInfo.EnvelopeInfo> iterator = blockInfo.getEnvelopeInfos().iterator(); iterator.hasNext();){

            BlockInfo.EnvelopeInfo endorserInfo= iterator.next();
            System.out.printf("transactionId: %s \n",endorserInfo.getTransactionID());
        }

        Long nowHeight = channel.queryBlockchainInfo().getHeight();//查询当前的区块高度
        System.out.println(nowHeight);


    }




}
