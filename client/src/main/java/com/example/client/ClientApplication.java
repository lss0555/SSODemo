package com.example.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;

import javax.servlet.annotation.WebServlet;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.client"})
@ServletComponentScan
@EnableAutoConfiguration
public class ClientApplication {
	public static void main(String[] args) {
		SpringApplication.run(ClientApplication.class, args);
	}
}

