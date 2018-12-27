package com.example.server2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@SpringBootApplication
@ComponentScan(basePackages = {"com.example.server2"})
@EnableAutoConfiguration
public class Server2Application {

	public static void main(String[] args) {
		SpringApplication.run(Server2Application.class, args);
	}

}

