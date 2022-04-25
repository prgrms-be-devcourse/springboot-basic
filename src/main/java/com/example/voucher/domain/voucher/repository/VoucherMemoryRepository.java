package com.example.voucher.domain.voucher.repository;

import com.example.voucher.domain.voucher.Voucher;
import com.example.voucher.domain.voucher.VoucherType;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static com.example.voucher.domain.voucher.VoucherType.EMPTY;
import static com.example.voucher.exception.ErrorMessage.SERVER_ERROR;

@Repository
public class VoucherMemoryRepository implements VoucherRepository {
	private static final Map<Long, Voucher> store = new ConcurrentHashMap<>();

	@Override
	public Voucher save(Voucher voucher) {

		if (voucher == null) {
			// TODO: 로그 남기기
			throw new IllegalArgumentException(SERVER_ERROR.name());
		}

		if (voucher.getVoucherId() == null) {
			VoucherType voucherType = VoucherType.of(voucher.getClass().getSimpleName());

			if (voucherType == EMPTY) {
				// TODO: 로그 남기기
				throw new IllegalArgumentException(SERVER_ERROR.name());
			}

			voucher = voucherType.create(VoucherIdGenerator.generateVoucherId(), voucher.getDiscountAmount());
		}

		store.put(voucher.getVoucherId(), voucher);
		return voucher;
	}

	@Override
	public List<Voucher> findAll() {
		return store.values()
				.stream()
				.collect(Collectors.toList());
	}
}
