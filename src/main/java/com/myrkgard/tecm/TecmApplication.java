package com.myrkgard.tecm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.myrkgard.tecm.database.Database;

@SpringBootApplication
public class TecmApplication {

	public static void main(String[] args) {
		SpringApplication.run(TecmApplication.class, args);
	}

}
