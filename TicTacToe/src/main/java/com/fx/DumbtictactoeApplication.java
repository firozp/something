package com.fx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackageClasses = GameController.class)
public class DumbtictactoeApplication {

	public static void main(String[] args) {
		SpringApplication.run(DumbtictactoeApplication.class, args);
	}
}
