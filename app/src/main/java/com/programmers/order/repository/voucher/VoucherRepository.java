package com.programmers.order.repository.voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.programmers.order.domain.Voucher;

public interface VoucherRepository {
	Voucher saveVoucher(Voucher voucher);

	List<Voucher> getVouchers();

	Optional<Voucher> findById(UUID voucherId);

}
