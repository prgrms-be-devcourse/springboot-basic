package com.example.voucher.service.voucher;

import com.example.voucher.domain.voucher.Voucher;
import com.example.voucher.domain.voucher.VoucherType;
import java.time.LocalDateTime;
import java.util.List;

public interface VoucherService {
	Voucher save(VoucherType voucherType, int discountAmount);
	List<Voucher> findAll();
	void deleteById(Long voucherId);
	Voucher findById(Long voucherId);
	List<Voucher> findByCreatedAt(LocalDateTime createdAt);
	List<Voucher> findByVoucherType(VoucherType voucherType);
}
