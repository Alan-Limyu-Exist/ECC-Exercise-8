package com.exist.ecc.limyu_exercise8.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({
		"com.exist.ecc.limyu_exercise8.core",
		"com.exist.ecc.limyu_exercise8.infra",
		"com.exist.ecc.limyu_exercise8.app"
})
public class LimyuExercise8Application {

	public static void main(String[] args) {
		SpringApplication.run(LimyuExercise8Application.class, args);
	}

}
