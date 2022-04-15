package com.prgrms.vouchermanagement.voucher.repository;

import java.util.List;
import java.util.UUID;

import com.prgrms.vouchermanagement.voucher.domain.Voucher;

public interface VoucherRepository {
	List<Voucher> findAllVoucher();

	Voucher save(Voucher voucher);

	Voucher findById(UUID id);
}
