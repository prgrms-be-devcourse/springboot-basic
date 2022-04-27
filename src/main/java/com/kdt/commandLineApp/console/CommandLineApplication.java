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
		MenuMainLogic menuMainLogic = appContext.getBean("menuMainLogic", MenuMainLogic.class);
		VoucherMainLogic voucherMainLogic = appContext.getBean("voucherMainLogic", VoucherMainLogic.class);
		CustomerMainLogic customerMainLogicMainLogic = appContext.getBean("customerMainLogicMainLogic", CustomerMainLogic.class);
		VoucherWalletMainLogic voucherWalletMainLogic = appContext.getBean("voucherWalletMainLogic", VoucherWalletMainLogic.class);

		while (true) {
			menuMainLogic.printMainMenu();
			try {
				Command command  = menuMainLogic.getCommand();
				switch (command) {
					case CREATE :
						voucherMainLogic.createVoucher();
						break;
					case LIST :
						voucherMainLogic.showVouchers();
						break;
					case BLACKLIST :
						customerMainLogicMainLogic.showBlackList();
						break;
					case GIVE_VOUCHER :
						voucherWalletMainLogic.giveVoucher();
						break;
					case TAKE_VOUCHER :
						voucherWalletMainLogic.takeVoucher();
						break;
					case CUSTOMER_LIST_WITH_SAME_VOUCHER :
						voucherWalletMainLogic.getCustomerListWithSameVoucher();
						break;
					case VOUCHER_LIST_OF_CUSTOMER:
						voucherWalletMainLogic.getVoucherListOfCustomer();
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
