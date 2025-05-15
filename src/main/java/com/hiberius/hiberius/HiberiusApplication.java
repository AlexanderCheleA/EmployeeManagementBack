package com.hiberius.hiberius;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.hiberius.hiberius"})
public class HiberiusApplication {

	public static void main(String[] args) {
		SpringApplication.run(HiberiusApplication.class, args);
	}

}
