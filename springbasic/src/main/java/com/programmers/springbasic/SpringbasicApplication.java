package com.programmers.springbasic;

import com.programmers.springbasic.domain.customer.controller.CustomerController;
import com.programmers.springbasic.domain.io.IOConsole;
import com.programmers.springbasic.domain.voucher.controller.VoucherController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@Slf4j
@SpringBootApplication
public class SpringbasicApplication {
	public static void main(String[] args) {
		// Web
		SpringApplication.run(SpringbasicApplication.class, args);

// 		// Console
//		ApplicationContext applicationContext = new SpringApplication(SpringbasicApplication.class).run(args);
//
//		IOConsole ioConsole = applicationContext.getBean(IOConsole.class);
//		VoucherController voucherController = applicationContext.getBean(VoucherController.class);
//		CustomerController customerController = applicationContext.getBean(CustomerController.class);
//
//		try {
//			while (true) {
//				ioConsole.showMainMenu();
//				String getMenuChoice = ioConsole.getInput();
//
//				switch (getMenuChoice) {
//					case "1": {
//						customerController.run();
//						break;
//					}
//					case "2": {
//						voucherController.run();
//						break;
//					}
//				}
//			}
//		} catch (Exception e) {
//			log.error(e.getMessage());
//		}

	}
}
