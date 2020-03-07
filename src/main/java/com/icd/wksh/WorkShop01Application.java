package com.icd.wksh;

import com.icd.wksh.configs.Canon;
import com.icd.wksh.configs.Printer;
import com.icd.wksh.controllers.HelloController;
import com.icd.wksh.daos.UserDao;
import com.icd.wksh.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class WorkShop01Application {
	private static final Logger log = LoggerFactory.getLogger(HelloController.class);

//	@Autowired
//	@Qualifier("Canon1")
//	private Printer printer;

//	@Autowired
//	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(WorkShop01Application.class, args);
//		Printer printer = (Printer) context.getBean("Canon1");
//		printer.print();
//		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//		log.debug("encode: {}", passwordEncoder.encode("password"));
//		UserDao userDao = context.getBean(UserDao.class);
//		User admin = userDao.getUserByUsername("admin");
//		log.debug("admin: {}", admin);
//		HelloController printer = context.getBean(HelloController.class);
//		printer.test();
//		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//		String passwordHash = passwordEncoder.encode("sdasdasdasdasd");
//		log.debug("passwordHash= {}",passwordHash);
	}

}
