package com.programmers.basic.repository;

import java.util.List;

import com.programmers.basic.entity.Voucher;

public interface VoucherRepository {
	Voucher save(Voucher voucher);

	List<Voucher> findAll();

}
