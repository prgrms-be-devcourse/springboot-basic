package com.prgrms.vouchermanagement.voucher.io;

import org.springframework.stereotype.Component;

@Component
public interface VoucherInput {
	long inputVoucherInfo();

	String selectVoucherType();

	String selectedMenu();
}
