package com.example.voucher.domain.voucher.repository;

import com.example.voucher.domain.voucher.Voucher;
import com.example.voucher.domain.voucher.VoucherType;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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

		VoucherType voucherType = voucher.getVoucherType();
		if(voucherType == EMPTY) {
			// TODO: 로그 남기기
			throw new IllegalArgumentException(SERVER_ERROR.name());
		}

		if (voucher.getVoucherId() == null) {
			Voucher createdVoucher = Voucher.create(voucherType, VoucherIdGenerator.generateVoucherId(), voucher.getDiscountAmount());
			store.put(createdVoucher.getVoucherId(), createdVoucher);
			return createdVoucher;
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

	@Override
	public void deleteAll() {
		store.clear();
	}

	@Override
	public int deleteById(Long voucherId) {
		return 0;
	}

	@Override
	public Optional<Voucher> findById(Long voucherId) {
		return Optional.empty();
	}

	@Override
	public List<Voucher> findByCreatedAt(LocalDateTime createdAt) {
		return null;
	}

	@Override
	public List<Voucher> findByVoucherType(VoucherType voucherType) {
		return null;
	}
}
