package com.devcourse.springbootbasic;

import com.devcourse.springbootbasic.engine.Platform;
import com.devcourse.springbootbasic.engine.config.YamlProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySources;

@SpringBootApplication
@EnableConfigurationProperties
public class SpringbootBasicApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootBasicApplication.class, args)
				.getBean(Platform.class)
				.run();
	}

}
