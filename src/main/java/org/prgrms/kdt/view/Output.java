package org.prgrms.kdt.view;

import java.util.List;

import org.prgrms.kdt.enums.Voucher;

public interface Output {
	public void displayGuideMessage();
	public void displayExitMessage();
	public void displayCreateVoucherMessage();
	public void displayVoucherList(List<Voucher> vouchers);
}
