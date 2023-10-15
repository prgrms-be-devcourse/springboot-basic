package com.programmers.springbasic.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.programmers.springbasic.entity.Voucher;

@Repository
public class FileVoucherRepository implements VoucherRepository{
	@Override
	public Voucher save(Voucher voucher) {
		return null;
	}

	@Override
	public List<Voucher> findAll() {
		return null;
	}
}
