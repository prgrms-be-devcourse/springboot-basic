package com.programmers.springbasic.repository.voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.programmers.springbasic.entity.voucher.Voucher;

@Repository
@Profile("test")
public class JdbcVoucherRepository implements VoucherRepository {
	@Override
	public Voucher save(Voucher voucher) {
		return null;
	}

	@Override
	public List<Voucher> findAll() {
		return null;
	}

	@Override
	public Optional<Voucher> findById(UUID id) {
		return Optional.empty();
	}

	@Override
	public void deleteById(UUID id) {

	}
}
