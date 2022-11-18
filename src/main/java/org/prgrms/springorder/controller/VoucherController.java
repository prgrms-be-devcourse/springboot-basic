package org.prgrms.springorder.controller;

import static org.prgrms.springorder.domain.Message.*;

import org.prgrms.springorder.domain.voucher.VoucherType;
import org.prgrms.springorder.io.IO;
import org.prgrms.springorder.service.VoucherService;
import org.springframework.stereotype.Component;

@Component
public class VoucherController {

	private final IO io;
	private final VoucherService voucherService;

	public VoucherController(IO io, VoucherService voucherService) {
		this.io = io;
		this.voucherService = voucherService;
	}

	public void createVoucher() {
		VoucherType voucherType = VoucherType.getVoucherByOrder(io.read(SELECT_VOUCHER_MESSAGE));
		double value = Double.parseDouble(io.read(voucherType.getMessage()));
		voucherService.createVoucher(voucherType,value);
	}

	public void showVoucherList() {
		io.writeList(voucherService.getList());
	}


}
