package com.prgms.springbootbasic.ui;

import com.prgms.springbootbasic.model.Member;
import com.prgms.springbootbasic.model.Voucher;
import com.prgms.springbootbasic.util.Menu;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Console {
	
	private final InputView inputView;
	private final OutputView outputView;
	private final StringConversion stringConversion;
	
	public Console(InputView inputView, OutputView outputView, StringConversion stringConversion) {
		this.inputView = inputView;
		this.outputView = outputView;
		this.stringConversion = stringConversion;
	}
	
	public String init() {
		outputView.init();
		return inputView.enterMenu();
	}

	public String initApplication(Menu menu) {
		outputView.initApplication(menu);
		return inputView.enterMenu();
	}

	public void showVoucherList(List<Voucher> vouchers) {
		List<String> list = stringConversion.changeVoucherToString(vouchers);
		outputView.showList(list);
	}

	public void showMemberList(List<Member> members) {
		List<String> list = stringConversion.changeMemberToString(members);
		outputView.showList(list);
	}
	
	public String inputVoucherType() {
		outputView.showWhenEntervoucherType();
		return inputView.enterVoucherType();
	}
	
	public Long inputVoucherNumber() {
		outputView.showWhenEnterVoucherNumber();
		return inputView.enterVoucherNumber();
	}
	
	public void exit() {
		outputView.exit();
		inputView.close();
		outputView.close();
	}
	
	public void showExceptionMessage(String message) {
		outputView.showExceptionMessage(message);
	}

}
