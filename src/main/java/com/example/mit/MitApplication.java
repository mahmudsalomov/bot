package com.example.mit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;
@SpringBootApplication
public class MitApplication {

	public static void main(String[] args) {
		ApiContextInitializer.init();
		SpringApplication.run(MitApplication.class, args);
	}

}
