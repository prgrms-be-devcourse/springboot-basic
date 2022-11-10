package com.programmers.kwonjoosung.springbootbasicjoosung;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan(basePackages = "com.programmers.kwonjoosung.springbootbasicjoosung.config")
@SpringBootApplication
public class SpringBootBasicJoosungApplication implements CommandLineRunner {

	private final MainLauncher mainLauncher;

	SpringBootBasicJoosungApplication(MainLauncher mainLauncher){
		this.mainLauncher = mainLauncher;
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringBootBasicJoosungApplication.class, args);
	}

	@Override
	public void run(String... args)  {
		mainLauncher.run();
	}
}
