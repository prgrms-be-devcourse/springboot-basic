package org.prgrms.springorder.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.prgrms.springorder.domain.Voucher;
import org.prgrms.springorder.domain.VoucherType;
import org.prgrms.springorder.repository.VoucherRepository;
import org.springframework.stereotype.Service;

@Service
public class VoucherService {

	private final VoucherRepository voucherRepository;

	public VoucherService(VoucherRepository voucherRepository) {
		this.voucherRepository = voucherRepository;
	}

	public void createVoucher(VoucherType voucherType, long value) {
		Voucher voucher = voucherType.voucherMaker(UUID.randomUUID(), value);
		voucherRepository.save(voucher);
	}

	public List<String> getList() {
		return voucherRepository.findAll().stream().map(Voucher::toString).collect(Collectors.toList());
	}

}
