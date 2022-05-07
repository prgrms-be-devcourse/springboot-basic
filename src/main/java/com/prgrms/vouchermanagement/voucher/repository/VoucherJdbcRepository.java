package com.prgrms.vouchermanagement.voucher.repository;

import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.prgrms.vouchermanagement.voucher.VoucherType;
import com.prgrms.vouchermanagement.voucher.domain.Voucher;

@Repository
@Primary
public class VoucherJdbcRepository implements VoucherRepository {
	private final Logger logger = LoggerFactory.getLogger(VoucherJdbcRepository.class);
	private final JdbcTemplate jdbcTemplate;

	public VoucherJdbcRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	private static RowMapper<Voucher> voucherRowMapper = (rs, i) -> {
		UUID voucher_id = toUUID(rs.getBytes("voucher_id"));
		long discount_info = rs.getLong("discount_info");
		LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
		VoucherType type = VoucherType.of(rs.getInt("type"));

		return type.getVoucher(voucher_id, discount_info, createdAt);
	};

	static UUID toUUID(byte[] bytes) {
		ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
		return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
	}

	@Override
	public List<Voucher> findAll() {
		return jdbcTemplate.query("select * from vouchers", voucherRowMapper);
	}

	@Override
	public Voucher insert(Voucher voucher) {
		jdbcTemplate.update(
			"INSERT INTO vouchers(voucher_id, type, discount_info, created_at) VALUES (UUID_TO_BIN(?), ?, ?, ?)",
			voucher.getVoucherId().toString().getBytes(),
			voucher.getType().getMappingCode(),
			voucher.getDiscountInfo(),
			Timestamp.valueOf(voucher.getCreatedTime()));

		return voucher;
	}

	@Override
	public Optional<Voucher> findById(UUID voucherId) {
		try {
			return Optional.ofNullable(
				jdbcTemplate.queryForObject("select * from vouchers WHERE voucher_id = UUID_TO_BIN(?)",
					voucherRowMapper, voucherId.toString().getBytes()));
		} catch (EmptyResultDataAccessException e) {
			logger.info("id {} 을 가진 Voucher 가 존재하지 않습니다", voucherId);

			return Optional.empty();
		}
	}

	@Override
	public long deleteById(UUID voucherId) {
		return jdbcTemplate.update("DELETE FROM vouchers where voucher_id = UUID_TO_BIN(?)",
			voucherId.toString().getBytes());
	}

	@Override
	public long deleteAll() {
		return jdbcTemplate.update("DELETE FROM vouchers");
	}
}
