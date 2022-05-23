package com.programmers.order.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.order.controller.dto.VoucherDto;
import com.programmers.order.domain.Voucher;
import com.programmers.order.domain.VoucherType;
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
				.orElseThrow(() -> new ServiceException.NotFoundResource("voucher resource 가 존재하지 않습니다."));
		Voucher updatedVoucher = voucher.update(requestUpdate);

		return voucherRepository.update(updatedVoucher);
	}

	public Page<Voucher> lookUpVouchers(VoucherDto.Search searchDto, Pageable pageable) {
		return voucherRepository.findAll(pageable, searchDto.buildCondition());
	}

	public Voucher lookUpVoucher(UUID voucherId) {
		return voucherRepository.findById(voucherId)
				.orElseThrow(() -> new ServiceException.NotFoundResource("voucher resource 가 존재하지 않습니다."));
	}

	@Transactional
	public void deleteById(UUID voucherId) {
		voucherRepository.deleteByVoucherId(voucherId);
	}
}
