package com.prgms.springbootbasic.ui;

import com.prgms.springbootbasic.util.Menu;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OutputView {
	private static final TextTerminal textTerminal = TextIoFactory.getTextTerminal();
	private final MemberOutputView memberOutputView;
	private final VoucherOutputView voucherOutputView;

	public OutputView(MemberOutputView memberOutputView, VoucherOutputView voucherOutputView) {
		this.memberOutputView = memberOutputView;
		this.voucherOutputView = voucherOutputView;
	}

	public void init() {
		textTerminal.println("=== Application ===");
		textTerminal.println("Type exit to exit program.");
		textTerminal.println("Type voucher to voucher application");
		textTerminal.println("Type member to member application");
	}

	public void initApplication(Menu menu) {
		if (menu == Menu.MEMBER) {
			memberOutputView.initApplication();
			return;
		}
		voucherOutputView.initApplication();
	}

	public void showWhenEntervoucherType() {
		voucherOutputView.showWhenEntervoucherType();
	}

	public void showWhenEnterVoucherNumber() {
		voucherOutputView.showWhenEntervoucherNumber();
	}

	public void showList(List<String> list) {
		list.forEach(textTerminal::println);
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
	
}
