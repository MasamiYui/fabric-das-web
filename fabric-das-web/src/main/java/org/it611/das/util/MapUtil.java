package org.it611.das.util;

import java.util.HashMap;

public class MapUtil {

    //设置HashMap的value为空
    public static HashMap setMapValue(HashMap<String, Object> hashMap) {
        HashMap<String, Object> hm = new HashMap<String,Object>();
        for (String key : hashMap.keySet()) {
            hm.put(key, "");
        }
        return hm;
    }
}
