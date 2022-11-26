package org.prgrms.springorder;

import org.prgrms.springorder.controller.CommandLineApp;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.HiddenHttpMethodFilter;

@ConfigurationPropertiesScan("org.prgrms.springorder.properties")
@SpringBootApplication
public class SpringorderApplication  {

	public static void main(String[] args) {
		SpringApplication.run(SpringorderApplication.class, args);
	}

	@Bean
	public HiddenHttpMethodFilter hiddenHttpMethodFilter(){
		return new HiddenHttpMethodFilter();
	}
}

