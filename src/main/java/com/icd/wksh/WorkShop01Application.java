package com.icd.wksh;

import com.icd.wksh.controllers.HelloController;
import com.icd.wksh.daos.UserDao;
import com.icd.wksh.models.User;
import com.icd.wksh.simple.Canon;
import com.icd.wksh.simple.Printer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class WorkShop01Application {
	private static final Logger log = LoggerFactory.getLogger(HelloController.class);

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(WorkShop01Application.class, args);
//		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//		log.debug("encode: {}", passwordEncoder.encode("password"));
//		UserDao userDao = context.getBean(UserDao.class);
//		User admin = userDao.getUserByUsername("admin");
//		log.debug("admin: {}", admin);
//		HelloController printer = context.getBean(HelloController.class);
//		printer.test();
	}

}
