package com.prgrms.vouchermanagement.io;

import java.util.List;

import com.prgrms.vouchermanagement.voucher.domain.Voucher;

public interface OutputView {
	void showMenu();

	void printVoucher(Voucher target);

	void showVoucherMenu();

	void requestVoucherInfo();

	<T> void showAll(List<T> list);

	void printErrorMessage(String message);

}
