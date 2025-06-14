package com.example.freeswitchuser;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value={"com.example.freeswitchuser.**.mapper*"})
public class FreeswitchUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(FreeswitchUserApplication.class, args);
    }

}
