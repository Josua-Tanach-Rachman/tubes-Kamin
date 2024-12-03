package com.example.Tubes.Kamin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication (exclude = {SecurityAutoConfiguration.class})
public class TubesKaminApplication  {

	public static void main(String[] args) {
		SpringApplication.run(TubesKaminApplication.class, args);
	}

}
