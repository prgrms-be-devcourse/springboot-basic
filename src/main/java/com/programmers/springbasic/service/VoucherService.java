package com.programmers.springbasic.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.programmers.springbasic.dto.CreateFixedAmountVoucherRequest;
import com.programmers.springbasic.dto.CreatePercentDiscountVoucherRequest;
import com.programmers.springbasic.dto.GetVouchersResponse;
import com.programmers.springbasic.entity.customer.Customer;
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

	public Voucher getVoucherDetail(String voucherId) {
		return voucherRepository.findById(UUID.fromString(voucherId)).orElseThrow();
	}

	public Voucher updateVoucher(UUID uuid, long newDiscountValue) {
		Voucher voucher = voucherRepository.findById(uuid).orElseThrow(); //todo throw Exception
		voucher.changeDiscountValue(newDiscountValue);
		return voucherRepository.update(voucher);
	}

	public void deleteVoucher(String voucherId) {
		Voucher voucher = voucherRepository.findById(UUID.fromString(voucherId)).orElseThrow();
		voucherRepository.deleteById(voucher.getVoucherId());
	}
}
