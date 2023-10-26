package com.programmers.springbasic.service;

import static com.programmers.springbasic.enums.ErrorCode.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.programmers.springbasic.dto.CreateFixedAmountVoucherRequest;
import com.programmers.springbasic.dto.CreatePercentDiscountVoucherRequest;
import com.programmers.springbasic.dto.GetVouchersResponse;
import com.programmers.springbasic.entity.voucher.Voucher;
import com.programmers.springbasic.repository.voucher.VoucherRepository;

@Service
public class VoucherService {

	private final VoucherRepository voucherRepository;

	public VoucherService(VoucherRepository voucherRepository) {
		this.voucherRepository = voucherRepository;
	}

	public List<GetVouchersResponse> getVouchers() {
		List<Voucher> vouchers = voucherRepository.findAll();
		return vouchers.stream().map(GetVouchersResponse::new).collect(Collectors.toList());
	}

	public Voucher createPercentDiscountVoucher(CreatePercentDiscountVoucherRequest request) {
		return voucherRepository.insert(request.toEntity(UUID.randomUUID()));
	}

	public Voucher createFixedAmountVoucher(CreateFixedAmountVoucherRequest request) {
		return voucherRepository.insert(request.toEntity(UUID.randomUUID()));
	}

	public Voucher getVoucherDetail(UUID voucherId) {
		return voucherRepository.findById(voucherId)
			.orElseThrow(() -> new NoSuchElementException(VOUCHER_NOT_FOUND.getMessage()));
	}

	public Voucher updateVoucher(UUID uuid, long newDiscountValue) {
		Voucher voucher = voucherRepository.findById(uuid)
			.orElseThrow(() -> new NoSuchElementException(VOUCHER_NOT_FOUND.getMessage()));
		voucher.changeDiscountValue(newDiscountValue);
		return voucherRepository.update(voucher);
	}

	public void deleteVoucher(UUID voucherId) {
		Voucher voucher = voucherRepository.findById(voucherId)
			.orElseThrow(() -> new NoSuchElementException(VOUCHER_NOT_FOUND.getMessage()));
		voucherRepository.deleteById(voucher.getVoucherId());
	}
}
