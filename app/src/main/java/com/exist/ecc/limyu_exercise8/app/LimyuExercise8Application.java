package com.exist.ecc.limyu_exercise8.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {
		"com.exist.ecc.limyu_exercise8.app",
		"com.exist.ecc.limyu_exercise8.core.service"
})
@EnableJpaRepositories(basePackages = "com.exist.ecc.limyu_exercise8.core.dao.repository")
@EntityScan(basePackages = "com.exist.ecc.limyu_exercise8.core.model")
public class LimyuExercise8Application {

	public static void main(String[] args) {
		SpringApplication.run(LimyuExercise8Application.class, args);
	}

}
