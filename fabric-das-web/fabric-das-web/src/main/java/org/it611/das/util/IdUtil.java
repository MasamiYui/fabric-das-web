package org.it611.das.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class IdUtil {

    /**
     * 暂时确定的18位随机数生成策略，满足Id携带随机数以及递增,前面也可添加业务码
     * @return
     */
    public static String getId() {
        String id="";
        //获取当前时间戳
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
        String temp = sf.format(new Date());
        //获取6位随机数
        int random=(int) ((Math.random()+1)*100000);
        id=temp+random;
        return id;
    }


}
