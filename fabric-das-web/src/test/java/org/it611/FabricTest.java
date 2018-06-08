package org.it611;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.hyperledger.fabric.sdk.exception.CryptoException;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.hyperledger.fabric.sdk.exception.TransactionException;
import org.it611.das.DasApplication;
import org.it611.das.fabric.ChaincodeManager;
import org.it611.das.fabric.util.FabricManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;


@RunWith(SpringRunner.class)
//@SpringBootTest(classes = DasApplication.class)
@WebAppConfiguration
public class FabricTest {

    //private static Logger logger=Logger.getLogger(FabricTest.class);



    /**
     * 总结：对于couchdb而言，对于相同的Key的插入不会提示存在，也不会提示错误，而是直接覆盖，也许为了更高的吞吐率？
     * 只能在链码层或Service层进行判断，放在链码层会更合适
     */

    @Test
    public void TestInsert1() throws InvalidArgumentException, NoSuchAlgorithmException, IOException, TransactionException, NoSuchProviderException, CryptoException, InvalidKeySpecException, InterruptedException, ExecutionException, TimeoutException, ProposalException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        ChaincodeManager manager = FabricManager.obtain().getManager();
        String[] arguments = new String[]{"test2", "test2"};
        Map<String, String> result = manager.invoke("addAsset", arguments);
        System.out.println(result.toString());
    }

    @Test
    public void TestQuery1() throws InvalidArgumentException, NoSuchAlgorithmException, IOException, TransactionException, NoSuchProviderException, CryptoException, InvalidKeySpecException, ProposalException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        ChaincodeManager manager = FabricManager.obtain().getManager();
        String[] argQuery = new String[]{"20180508215226111861"};
        Map<String, String> fabricStateData = manager.query("query", argQuery);
        String dataJson = fabricStateData.get("data");
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap map=objectMapper.readValue(dataJson, HashMap.class);
        System.out.println(fabricStateData);
        System.out.println(map.get("address"));


    }



}
