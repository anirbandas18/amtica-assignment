package com.toab.app.signup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;



@SpringBootApplication
@EnableCaching
@EnableAutoConfiguration(exclude = { 
	    SecurityAutoConfiguration.class 
	})
public class SignUpApplication {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SpringApplication.run(SignUpApplication.class, args);
	}

}
