package org.prgrms.kdt.view;

import org.prgrms.kdt.enums.Command;
import org.prgrms.kdt.enums.Voucher;

public interface Input {

	Command getCommand();

	Voucher getVoucher();
}
