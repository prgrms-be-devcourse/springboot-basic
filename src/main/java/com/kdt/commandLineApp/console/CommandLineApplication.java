package com.kdt.commandLineApp.console;

import com.kdt.commandLineApp.AppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class CommandLineApplication {
	private static final Logger logger = LoggerFactory.getLogger(CommandLineApplication.class);

	public static void main(String[] args) {
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
					case BLACKLIST :
						mainLogic.showBlackList();
						break;
					case GIVE_VOUCHER :
						mainLogic.giveVoucher();
						break;
					case TAKE_VOUCHER :
						mainLogic.takeVoucher();
						break;
					case CUSTOMER_LIST_WITH_SAME_VOUCHER :
						mainLogic.getCustomerListWithSameVoucher();
						break;
					case VOUCHER_LIST_OF_CUSTOMER:
						mainLogic.getVoucherListOfCustomer();
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
