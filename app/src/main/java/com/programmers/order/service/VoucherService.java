package com.programmers.order.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.order.domain.Voucher;
import com.programmers.order.exception.ServiceException;
import com.programmers.order.repository.VoucherRepository;

@Transactional(readOnly = true)
@Service
public class VoucherService {

	private final VoucherRepository voucherRepository;

	public VoucherService(VoucherRepository voucherRepository) {
		this.voucherRepository = voucherRepository;
	}

	@Transactional
	public Voucher create(Voucher voucher) {
		return voucherRepository.insert(voucher);
	}

	@Transactional
	public Voucher update(Voucher requestUpdate) {

		Voucher voucher = voucherRepository.findById(requestUpdate.getVoucherId())
				.orElseThrow(() -> new ServiceException.NotFoundResource("vocuher resouerce 가 존재하지 않습니다."));
		Voucher updatedVoucher = voucher.update(requestUpdate);

		return voucherRepository.update(updatedVoucher);
	}
}
