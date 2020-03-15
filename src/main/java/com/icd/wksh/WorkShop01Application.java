package com.icd.wksh;

import com.icd.wksh.controllers.BookController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.web.client.RestTemplateAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.UUID;

@SpringBootApplication
@EnableAutoConfiguration(exclude={RestTemplateAutoConfiguration.class})
public class WorkShop01Application {
	private static final Logger log = LoggerFactory.getLogger(WorkShop01Application.class);

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(WorkShop01Application.class, args);
	}

}
