package com.imchat.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Administrator
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.imchat.**.mapper"})
public class ChatWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChatWebApplication.class, args);
    }

}
