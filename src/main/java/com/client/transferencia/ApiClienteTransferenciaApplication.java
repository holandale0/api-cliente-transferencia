package com.client.transferencia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.client.transferencia")
@EnableAutoConfiguration
@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class ApiClienteTransferenciaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiClienteTransferenciaApplication.class, args);
	}

}
