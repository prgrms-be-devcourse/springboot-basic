package org.programmers.springboot.basic;

import lombok.RequiredArgsConstructor;
import org.programmers.springboot.basic.util.manager.CommandManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
@RequiredArgsConstructor
public class VoucherManagementApplication {

	private final ApplicationContext applicationContext;

	public static void main(String[] args) {
		SpringApplication.run(VoucherManagementApplication.class, args);
	}

	@Profile(value = "web")
	@Bean
	public ServletWebServerFactory tomcatServetContainer() {
		return new TomcatServletWebServerFactory();
	}

	@Profile(value = "command")
	@Bean
	public void init() {
		CommandManager commandManager = applicationContext.getBean(CommandManager.class);
		commandManager.run();
		System.exit(SpringApplication.exit(applicationContext, () -> 0));
	}
}
