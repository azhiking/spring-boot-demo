package com.tomhurry.dynamic.datasource;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"com.tomhurry.dynamic.datasource.dao", " com.tomhurry.dynamic.datasource.sniffer.engine.dao", "com.tomhurry.dynamic.datasource.sniffer.director.dao"})
public class DynamicDatasourceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DynamicDatasourceApplication.class, args);
    }

}
