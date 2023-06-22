package org.prgrms.kdt.view;

import java.util.List;

import org.prgrms.kdt.model.dto.VoucherDTO;

public interface OutputView {
	public void displayGuideMessage();
	public void displayExitMessage();
	public void displayCreateVoucherMessage();
	public void displayVoucherList(List<VoucherDTO> voucherTypes);
}
