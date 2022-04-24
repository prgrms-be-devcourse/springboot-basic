package com.prgrms.vouchermanagement.io;

import org.springframework.stereotype.Component;

@Component
public interface InputView {
	long inputDetailsInfo();

	String inputVoucherType();

	String inputSelectedMenu();
}
