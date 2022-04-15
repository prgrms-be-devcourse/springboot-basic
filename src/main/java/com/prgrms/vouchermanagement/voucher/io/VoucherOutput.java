package com.prgrms.vouchermanagement.voucher.io;

import java.util.List;

import com.prgrms.vouchermanagement.voucher.domain.Voucher;

public interface VoucherOutput {
	void showMenu();

	void notifyNoMappingSelection();

	void showVoucherInfo(Voucher voucher);

	void showVoucherMenu();

	void failCreation();

	void requestVoucherInfo();

	<T> void showAll(List<T> list);
}
