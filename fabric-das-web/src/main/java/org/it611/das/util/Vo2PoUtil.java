package org.it611.das.util;

import org.it611.das.domain.*;
import org.it611.das.vo.UserVo;

public class Vo2PoUtil {

    public static User UserVo2Po(UserVo userVo){

        User user = new User(userVo.getId(), userVo.getName(), userVo.getSex(), userVo.getNation(),
                userVo.getDate(), userVo.getAddress(), TimeUtil.getLocalTime(), 1, userVo.getFiles());
        return user;

    }

}
