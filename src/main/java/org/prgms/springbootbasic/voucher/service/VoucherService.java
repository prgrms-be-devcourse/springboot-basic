package org.prgms.springbootbasic.voucher.service;

import org.prgms.springbootbasic.voucher.repository.VoucherRepository;
import org.prgms.springbootbasic.voucher.vo.Voucher;

public class VoucherService {

	private final VoucherRepository voucherRepository;

	public VoucherService(VoucherRepository voucherRepository) {
		this.voucherRepository = voucherRepository;
	}

	public void create(String s, long VoucherParam) {
		VoucherType voucherType = VoucherType.getVoucherType(s);
		Voucher voucher = voucherType.createVoucher(VoucherParam);

		voucherRepository.save(voucher);

	}
}
