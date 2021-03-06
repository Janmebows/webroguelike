package com.fdm.webroguelike;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = { "com.fdm.controller", "com.fdm.setup", "com.fdm.model" })
@EnableJpaRepositories(basePackages = { "com.fdm.dal" })
@EntityScan(basePackages = { "com.fdm.model" })
public class WebroguelikeApplication {

	public static void main(String[] args) {
		Logger.getLogger("rootLogger").trace("Application Started");
		SpringApplication.run(WebroguelikeApplication.class, args);
	}

}
