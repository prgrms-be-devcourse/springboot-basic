package org.prgrms.springorder.service;

import java.util.List;
import java.util.stream.Collectors;

import org.prgrms.springorder.domain.VoucherFactory;
import org.prgrms.springorder.domain.voucher.Voucher;
import org.prgrms.springorder.domain.voucher.VoucherType;
import org.prgrms.springorder.repository.voucher.VoucherRepository;
import org.springframework.stereotype.Component;

@Component
public class VoucherService {

	private final VoucherRepository voucherRepository;

	public VoucherService(VoucherRepository voucherRepository) {
		this.voucherRepository = voucherRepository;
	}

	public void createVoucher(VoucherType voucherType, double value) {
		Voucher voucher = VoucherFactory.createVoucher(voucherType, value);
		voucherRepository.save(voucher);
	}

	public List<String> getList() {
		return voucherRepository.findAll().stream().map(Voucher::toString).collect(Collectors.toList());
	}

}
