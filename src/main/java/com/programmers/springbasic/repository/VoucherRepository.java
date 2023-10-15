package com.programmers.springbasic.repository;

import java.util.List;

import com.programmers.springbasic.entity.Voucher;

public interface VoucherRepository {
	Voucher save(Voucher voucher);

	List<Voucher> findAll();

}
