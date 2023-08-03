package org.prgrms.kdt.view;

import java.util.List;

import org.prgrms.kdt.model.dto.VoucherResponse;

public interface OutputView {
	void displayCommandGuideMessage();

	void displayExitMessage();

	void displayCreateVoucherMessage();

	void displayAmountErrorMessage();

	void displayVoucherList(List<VoucherResponse> voucherTypes);

	void displayCommandErrorMessage();

}
