package org.it611;

import org.apache.log4j.Logger;
import org.it611.das.couchdb.CouchDB;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class CouchDBTest {

    private static Logger logger=Logger.getLogger(CouchDBTest.class);

    /**
     * 关于couchdb不用也不能对couchdb进行修改，只能进行查询，否则会引起blockchain state的不一致
     * fabric对于已经篡改的state是无感觉的
     */

    @Autowired
    private CouchDB db;


    /**
     * 基本kv查询
     */

/*    @Test
    public void basicKVQuery() {
        *//**
     * 不能这样测试，这样依赖注入是不成功的
     *//*
        String str = db.basicKVQuery("aef01013");
        logger.debug(str);
    }*/
}
