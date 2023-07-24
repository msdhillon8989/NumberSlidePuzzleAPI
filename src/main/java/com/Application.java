package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		System.out.println("Staring Game APP");
		try {
			SpringApplication.run(Application.class, args);
		}catch (Exception e){
			e.printStackTrace();
		}
		System.out.println("Started Game APP");
	}


}