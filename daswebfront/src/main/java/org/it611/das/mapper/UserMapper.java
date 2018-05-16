package org.it611.das.mapper;

import org.it611.das.domain.User;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public interface UserMapper {

    HashMap<String, Object> findUserByPhone(String phone);

    HashMap<String, Object> findUserByEmail(String email);

    HashMap<String, Object> findUserByIdCard(String idcard);

    int addUser(User user);

}
