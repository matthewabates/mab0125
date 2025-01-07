package com.example.tool_rental;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ToolRentalApplication {

	public static void main(String[] args) {
		SpringApplication.run(ToolRentalApplication.class, args);
	}

}
