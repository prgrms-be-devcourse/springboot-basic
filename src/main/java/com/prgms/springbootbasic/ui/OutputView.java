package com.prgms.springbootbasic.ui;

import com.prgms.springbootbasic.domain.Customer;
import com.prgms.springbootbasic.util.Menu;
import com.prgms.springbootbasic.domain.Voucher;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OutputView {

	private static final TextTerminal textTerminal = TextIoFactory.getTextTerminal();
	private final CustomerOutputView customerOutputView;
	private final VoucherOutputView voucherOutputView;

	public OutputView(CustomerOutputView customerOutputView, VoucherOutputView voucherOutputView) {
		this.customerOutputView = customerOutputView;
		this.voucherOutputView = voucherOutputView;
	}

	public void init() {
		textTerminal.println("=== Application ===");
		textTerminal.println("Type exit to exit program.");
		textTerminal.println("Type voucher to voucher application");
		textTerminal.println("Type customer to customer application");
	}

	public void initApplication(Menu menu) {
		if (menu == Menu.CUSTOMER) {
			customerOutputView.initApplication();
			return;
		}
		voucherOutputView.initApplication();
	}

	public void showCustomerList(List<Customer> customers) {
		List<String> customerList = customerOutputView.changeCustomerToString(customers);
		showList(customerList);
	}

	public void showVoucherList(List<Voucher> vouchers) {
		List<String> voucherList = voucherOutputView.changeVoucherToString(vouchers);
		showList(voucherList);
	}

	public void showWhenEntervoucherType() {
		voucherOutputView.showWhenEntervoucherType();
	}

	public void showWhenEnterVoucherNumber() {
		voucherOutputView.showWhenEntervoucherNumber();
	}

	public void exit() {
		textTerminal.println("\nexit program.");
	}
	
	public void showExceptionMessage(String message) {
		textTerminal.println("\n" + message);
	}
	
	public void close() {
		textTerminal.dispose();
	}

	private void showList(List<String> list) {
		list.forEach(l -> textTerminal.println(l));
	}
	
}
