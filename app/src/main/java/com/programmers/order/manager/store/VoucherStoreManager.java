package com.programmers.order.manager.store;

import java.util.List;

import com.programmers.order.domain.Voucher;

public interface VoucherStoreManager {
	Voucher saveVoucher(Voucher voucher);

	List<Voucher> getVouchers();

}
