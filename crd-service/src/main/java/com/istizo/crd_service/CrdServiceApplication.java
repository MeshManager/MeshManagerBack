package com.istizo.crd_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CrdServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrdServiceApplication.class, args);
	}

}
