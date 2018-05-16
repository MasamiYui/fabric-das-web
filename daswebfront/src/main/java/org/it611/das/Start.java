package org.it611.das;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@MapperScan("org.it611.das.mapper")
@EnableTransactionManagement//开启事务
public class Start {

    public static void main(String[] args) {
        SpringApplication.run(Start.class, args);
    }
}