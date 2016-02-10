package com.home.feedo.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by andrew on 07.02.16.
 */

@SpringBootApplication
@ComponentScan(basePackages = {"com.home.feedo"})
public class SpringBootStarter {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootStarter.class,args);
    }
}
