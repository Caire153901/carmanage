package com.wmt.carmanage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class CarManageApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(CarManageApplication.class, args);
	}
}
