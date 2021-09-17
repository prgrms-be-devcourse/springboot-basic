package com.prgrms.w3springboot.voucher.repository;

import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.prgrms.w3springboot.voucher.FixedAmountVoucher;
import com.prgrms.w3springboot.voucher.PercentAmountVoucher;
import com.prgrms.w3springboot.voucher.Voucher;
import com.prgrms.w3springboot.voucher.VoucherType;

@Repository
@Profile("production")
public class NamedJdbcVoucherRepository implements VoucherRepository {
	private static final Logger logger = LoggerFactory.getLogger(NamedJdbcVoucherRepository.class);
	private static final int SUCCESS = 1;

	private static final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
		UUID voucherId = toUUID(resultSet.getBytes("voucher_id"));
		long amount = resultSet.getLong("amount");
		VoucherType type = VoucherType.of(resultSet.getString("type").toLowerCase());

		if (type == VoucherType.FIXED) {
			return new FixedAmountVoucher(voucherId, amount, type);
		} else if (type == VoucherType.PERCENT) {
			return new PercentAmountVoucher(voucherId, amount, type);
		}

		throw new IllegalArgumentException("유효하지 않은 바우처 타입입니다.");
	};

	private final NamedParameterJdbcTemplate jdbcTemplate;

	public NamedJdbcVoucherRepository(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	private static UUID toUUID(byte[] bytes) {
		ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
		return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
	}

	private Map<String, Object> toParamMap(Voucher voucher) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("voucherId", voucher.getVoucherId().toString().getBytes());
		paramMap.put("amount", voucher.getAmount());
		paramMap.put("type", voucher.getVoucherType().name());

		return paramMap;
	}

	@Override
	public Optional<Voucher> findById(UUID voucherId) {
		try {
			return Optional.ofNullable(
				jdbcTemplate.queryForObject("select * from vouchers WHERE voucher_id = UUID_TO_BIN(:voucherId)",
					Collections.singletonMap("voucherId", voucherId.toString().getBytes()),
					voucherRowMapper));
		} catch (EmptyResultDataAccessException e) {
			logger.error("데이터가 존재하지 않습니다.", e);
			return Optional.empty();
		}
	}

	@Override
	public Voucher insert(Voucher voucher) {
		int update = jdbcTemplate.update(
			"INSERT INTO vouchers(voucher_id, amount, type) VALUES (UUID_TO_BIN(:voucherId), :amount, :type)",
			toParamMap(voucher));
		throwsExceptionIfNotSuccess(update);

		return voucher;
	}

	@Override
	public Voucher update(Voucher voucher) {
		int update = jdbcTemplate.update(
			"UPDATE vouchers SET amount = :amount WHERE voucher_id = UUID_TO_BIN(:voucherId)",
			toParamMap(voucher));
		throwsExceptionIfNotSuccess(update);

		return voucher;
	}

	@Override
	public void delete(UUID voucherId) {
		int update = jdbcTemplate.update("DELETE FROM vouchers WHERE voucher_id = UUID_TO_BIN(:voucherId)",
			Collections.singletonMap("voucherId", voucherId.toString().getBytes()));
		throwsExceptionIfNotSuccess(update);
	}

	@Override
	public List<Voucher> findAll() {
		return jdbcTemplate.query("select * from vouchers", voucherRowMapper);
	}

	private void throwsExceptionIfNotSuccess(int update) {
		if (update != SUCCESS) {
			throw new NoSuchElementException("입력된 바우처가 존재하지 않습니다.");
		}
	}
}
