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

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
@EnableAutoConfiguration(exclude={RestTemplateAutoConfiguration.class})
public class WorkShop01Application {
	private static final Logger log = LoggerFactory.getLogger(WorkShop01Application.class);

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(WorkShop01Application.class, args);
		try (Stream<Path> walk = Files.walk(Paths.get("F:\\BackUp\\DB2_DUMP\\sql developer\\DATA_SEMICOLON_DELIMETER"))) {
			List<String> result = walk.filter(Files::isRegularFile)
					.map(x -> x.toString()).collect(Collectors.toList());

//			result.forEach(System.out::println);
			Charset charset = StandardCharsets.UTF_8;
			result.forEach(pathValue -> {
				try {
					log.debug("start replace: filename= {}", pathValue);
					Path path = Paths.get(pathValue);
					String content = new String(Files.readAllBytes(path), charset);
//					content = content.replaceAll("\n", ";");
//					content = content.replaceAll(";INSERT", "INSERT");
					content = content.substring(0, content.length()-1);
					Files.write(path, content.getBytes(charset));
					log.debug("================ end replace ======================");
				} catch (IOException e) {
					e.printStackTrace();
				}
			});

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
