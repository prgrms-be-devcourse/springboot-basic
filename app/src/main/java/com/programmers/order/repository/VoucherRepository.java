package com.programmers.order.repository;

import java.util.List;

import com.programmers.order.domain.Voucher;

public interface VoucherRepository {
	Voucher saveVoucher(Voucher voucher);

	List<Voucher> getVouchers();

}
