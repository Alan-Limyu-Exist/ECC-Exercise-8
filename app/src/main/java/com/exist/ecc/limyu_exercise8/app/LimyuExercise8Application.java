package com.exist.ecc.limyu_exercise8.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
		"com.exist.ecc.limyu_exercise8.core",
		"com.exist.ecc.limyu_exercise8.infra"
})
public class LimyuExercise8Application {

	public static void main(String[] args) {
		SpringApplication.run(LimyuExercise8Application.class, args);
	}

}
