package com.company.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.company.app.config")
public class LoginSvcApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoginSvcApplication.class, args);
	}

}
