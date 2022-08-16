package com.prgrms.vouchermanagement.io;

import java.util.List;

import com.prgrms.vouchermanagement.voucher.dto.VoucherInfo;

public interface OutputView {
	void showMenu();

	void printVoucher(VoucherInfo target);

	void showVoucherMenu();

	void requestVoucherInfo();

	<T> void showAll(List<T> list);

	void printErrorMessage(String message);

}
