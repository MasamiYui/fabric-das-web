package org.it611.das.couchdb;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.it611.das.util.HttpClientUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class CouchDB {

    private static Logger logger=Logger.getLogger(CouchDB.class);

    @Value("${couchdb.ip}")
    private String IP;

    @Value("${couchdb.port}")
    private String PORT;

    @Value("${couchdb.db}")
    private String DB;

    @Value("${couchdb.cc}")
    private String CC;



    /**
     * Response：
     * {
     * 	"_id": "mycca",
     * 	"_rev": "2-588a45b289359afa9dc6e5e7866eaf97",
     * 	"chaincodeid": "mycc",
     * 	"version": "4:0",
     * 	"_attachments": {
     * 		"valueBytes": {
     * 			"content_type": "application/octet-stream",
     * 			"revpos": 2,
     * 			"digest": "md5-hhOYXsSeuPdXrmQ56Hm7Kg==",
     * 			"length": 2,
     * 			"stub": true
     *                }    * 	}
     * }
     */

    /**
     * 请求url为：curl http://localhost:5984/mychannel/mycc%00a
     */

    public String basicKVQuery(String key) {

        String url = IP+PORT+DB+CC+"%00"+key;
        System.out.println("url:"+url);
        String responseStr = HttpClientUtil.doGet(url);
        logger.debug("response:"+responseStr);
        System.out.println("response:"+responseStr);
        return responseStr;
    }

    public Map basicKVQueryAsMap(String key) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        Map map = mapper.readValue(basicKVQuery(key), Map.class);
        return map;
    }


    public static void main(String[] args) {
        System.out.println(new CouchDB().basicKVQuery("aef01013"));
    }
}


