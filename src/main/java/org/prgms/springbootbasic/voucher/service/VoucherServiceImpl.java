package org.prgms.springbootbasic.voucher.service;

import java.util.List;
import java.util.Map;

import org.prgms.springbootbasic.voucher.repository.voucher.VoucherRepository;
import org.prgms.springbootbasic.voucher.repository.vouchertype.VoucherTypeRepository;
import org.prgms.springbootbasic.voucher.entity.Voucher;
import org.prgms.springbootbasic.voucher.entity.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class VoucherServiceImpl implements VoucherService {
	private static final Logger log = LoggerFactory.getLogger(VoucherServiceImpl.class);

	private final VoucherRepository voucherRepository;
	private final VoucherTypeRepository voucherTypeRepository;

	@Override
	public void createVoucher(String type, int value) {
		VoucherType voucherType = VoucherType.of(type);
		Voucher voucher = voucherType.createVoucher(value);
		voucherRepository.save(voucher);
	}

	@Override
	public Map<VoucherType, List<Voucher>> list() {
		return voucherRepository.getVoucherListByType();
	}

}
