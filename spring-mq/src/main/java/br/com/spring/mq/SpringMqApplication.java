package br.com.spring.mq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class SpringMqApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringMqApplication.class, args);
	}

}
