package org.prgrms.kdt.view;

import java.util.List;

import org.prgrms.kdt.enums.Command;
import org.prgrms.kdt.enums.Voucher;

public class Console implements Input, Output {

	@Override
	public Command getCommand() {
		return null;
	}

	@Override
	public Voucher getVoucher() {
		return null;
	}

	@Override
	public void displayGuideMessage() {

	}

	@Override
	public void displayExitMessage() {

	}

	@Override
	public void displayCreateVoucherMessage() {

	}

	@Override
	public void displayVoucherList(List<Voucher> vouchers) {

	}
}
