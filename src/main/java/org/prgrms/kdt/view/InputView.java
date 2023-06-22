package org.prgrms.kdt.view;

import org.prgrms.kdt.enums.Command;
import org.prgrms.kdt.enums.VoucherType;

public interface InputView {

	int getAmount();

	Command getCommand();

	VoucherType getVoucher();
}
