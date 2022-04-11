package org.prgms.springbootbasic.voucher.service;

import org.prgms.springbootbasic.voucher.repository.VoucherRepository;

public class VoucherService {

	private final VoucherRepository voucherRepository;

	public VoucherService(VoucherRepository voucherRepository) {
		this.voucherRepository = voucherRepository;
	}
}
