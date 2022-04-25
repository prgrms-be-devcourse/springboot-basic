package com.example.voucher.service.voucher;

import com.example.voucher.domain.voucher.Voucher;
import com.example.voucher.domain.voucher.VoucherType;
import java.util.List;

public interface VoucherService {
	Voucher save(VoucherType voucherType, int discountAmount);
	List<Voucher> findAll();
}
