package org.prgrms.kdt.view;

import java.util.List;

import org.prgrms.kdt.model.dto.VoucherDTO;

public interface OutputView {
	void displayCommandGuideMessage();

	void displayExitMessage();

	void displayCreateVoucherMessage();

	void displayAmountErrorMessage();

	void displayVoucherList(List<VoucherDTO> voucherTypes);

	void displayCommandErrorMessage();

}
