package study.dev.spring.app;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import study.dev.spring.app.exception.ExitException;
import study.dev.spring.customer.presentation.ConsoleCustomerController;
import study.dev.spring.voucher.presentation.ConsoleVoucherController;
import study.dev.spring.wallet.presentation.ConsoleWalletController;

@Component
@RequiredArgsConstructor
public class CommandExecutor {

	private final ConsoleVoucherController voucherController;
	private final ConsoleCustomerController customerController;
	private final ConsoleWalletController walletController;

	public void exit() {
		throw new ExitException();
	}

	public void blackList() {
		customerController.getAllBlackListCustomers();
	}

	public void allCustomers() {
		customerController.getAllCustomers();
	}

	public void customersByVoucher() {
		customerController.getCustomersByVoucher();
	}

	public void createVoucher() {
		voucherController.createVoucher();
	}

	public void allVouchers() {
		voucherController.getAllVouchers();
	}

	public void vouchersByCustomer() {
		voucherController.getVouchersByCustomer();
	}

	public void addWallet() {
		walletController.addWallet();
	}

	public void removeWalletByCustomer() {
		walletController.removeWalletByCustomer();
	}
}
