package org.it611;

import org.apache.log4j.Logger;
import org.it611.das.domain.CouchDB;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(CouchDB.class)
public class CourchDBTest {

    private static Logger logger=Logger.getLogger(CourchDBTest.class);

    /**
     * 关于couchdb不用也不能对couchdb进行修改，只能进行查询，否则会引起blockchain state的不一致
     * fabric对于已经篡改的state是无感觉的
     */

/*    @Autowired
    private CouchDB couchDB;


    @Test
    public void TestCouchDBCfg(){
        System.out.println(couchDB.getDb());
        System.out.println(couchDB.getPort());
        System.out.println(couchDB.getUrl());

    }*/

}
