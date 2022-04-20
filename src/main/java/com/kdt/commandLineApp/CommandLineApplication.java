package com.kdt.commandLineApp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class CommandLineApplication {
	private static final Logger logger = LoggerFactory.getLogger(CommandLineApplication.class);

	public static void main(String[] args) {
		CommandLineApplication commandLineApplication = new CommandLineApplication();
		ApplicationContext appContext = new AnnotationConfigApplicationContext(AppContext.class);
		MainLogic mainLogic = appContext.getBean("mainLogic", MainLogic.class);

		while (true) {
			mainLogic.printMainMenu();
			try {
				Command command  = mainLogic.getCommand();
				switch (command) {
					case CREATE :
						mainLogic.createVoucher();
						break;
					case LIST :
						mainLogic.showVouchers();
						break;
					case BLACKLIST:
						mainLogic.showBlackList();
						break;
					case EXIT:
						logger.debug("successfully exit");
						return;
				}
			}
			catch (Exception e) {
				logger.debug(e.getMessage());
			}
		}
	}
}
