package com.example.voucher.service.voucher;

import com.example.voucher.domain.voucher.Voucher;
import com.example.voucher.domain.voucher.VoucherType;

public interface VoucherService {
	Voucher save(VoucherType voucherType, int discountAmount);
}
