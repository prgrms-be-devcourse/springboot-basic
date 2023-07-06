package org.weekly.weekly;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;

@SpringBootApplication
public class VoucherManageApplication {
	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(VoucherManageApplication.class, args);
		Arrays.stream(Arrays.deepToString(context.getBeanDefinitionNames()).split(","))
						.forEach(System.out::println);
		context.getBean(VoucherManagementController.class).start();
	}
}