package com.programmers.springbasic.repository.voucher;

import java.util.List;

import com.programmers.springbasic.entity.voucher.Voucher;

public interface VoucherRepository {
	Voucher save(Voucher voucher);

	List<Voucher> findAll();

}
