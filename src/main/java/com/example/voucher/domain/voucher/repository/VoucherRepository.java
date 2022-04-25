package com.example.voucher.domain.voucher.repository;

import com.example.voucher.domain.voucher.Voucher;

import java.util.List;

public interface VoucherRepository {
	Voucher save(Voucher voucher);
	List<Voucher> findAll();
}
