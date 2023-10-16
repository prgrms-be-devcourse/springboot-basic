package study.dev.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import study.dev.spring.app.ConsoleVoucherApplicationRunner;

@SpringBootApplication
public class VoucherApplication {

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(VoucherApplication.class, args);

		ConsoleVoucherApplicationRunner runner = applicationContext.getBean(ConsoleVoucherApplicationRunner.class);

		while (true) {
			runner.run();
		}
	}
}
