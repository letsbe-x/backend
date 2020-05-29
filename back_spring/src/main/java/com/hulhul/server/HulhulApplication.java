package com.hulhul.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class HulhulApplication {

	public static void main(String[] args) {
		SpringApplication.run(HulhulApplication.class, args);
	}

}
