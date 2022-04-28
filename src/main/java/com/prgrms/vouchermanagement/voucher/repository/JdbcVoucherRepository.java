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
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.prgrms.vouchermanagement.commons.exception.UpdateFailException;
import com.prgrms.vouchermanagement.voucher.VoucherType;
import com.prgrms.vouchermanagement.voucher.domain.Voucher;

@Repository
@Primary
public class JdbcVoucherRepository implements VoucherRepository {
	private final Logger logger = LoggerFactory.getLogger(JdbcVoucherRepository.class);
	private final JdbcTemplate jdbcTemplate;

	public JdbcVoucherRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	private static RowMapper<Voucher> voucherRowMapper = (rs, i) -> {
		UUID voucher_id = toUUID(rs.getBytes("voucher_id"));
		long discount_info = rs.getLong("discount_info");
		LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
		VoucherType type = VoucherType.from(rs.getString("type"));

		return type.getVoucher(voucher_id, discount_info, createdAt);
	};

	static UUID toUUID(byte[] bytes) {
		ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
		return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
	}

	@Override
	public List<Voucher> findAllVoucher() {
		return jdbcTemplate.query("select * from vouchers", voucherRowMapper);
	}

	@Override
	public Voucher insert(Voucher voucher) {
		try {
			jdbcTemplate.update(
				"INSERT INTO vouchers(voucher_id, type, discount_info, created_at) VALUES (UUID_TO_BIN(?), ?, ?, ?)",
				voucher.getVoucherId().toString().getBytes(),
				voucher.getType().getMappingCode(),
				voucher.getDiscountInfo(),
				Timestamp.valueOf(voucher.getCreatedTime()));
		} catch (DuplicateKeyException e) {
			logger.info("Voucher {} insert fail", voucher);

			throw new UpdateFailException(e);
		} catch (DataAccessException e) {
			logger.info("Voucher {} insert fail", voucher);

			throw new UpdateFailException(e);
		}

		return voucher;
	}

	@Override
	public Optional<Voucher> findById(UUID voucherId) {
		try {
			return Optional.ofNullable(
				jdbcTemplate.queryForObject("select * from vouchers WHERE voucher_id = UUID_TO_BIN(?)",
					voucherRowMapper, voucherId.toString().getBytes()));
		} catch (DataAccessException e) {
			logger.info("find voucher by id {} fail", voucherId);

			return Optional.empty();
		}
	}

	@Override
	public boolean deleteById(UUID voucherId) {
		try {
			jdbcTemplate.update("DELETE FROM vouchers where voucher_id = UUID_TO_BIN(?)",
				voucherId.toString().getBytes());
		}catch (DataAccessException e){
			logger.info("delete by id {} 실패", voucherId);

			return false;
		}
		return true;
	}

	@Override
	public boolean deleteAll() {
		try {
			jdbcTemplate.update("DELETE FROM vouchers");
		}catch (DataAccessException e){
			logger.info("deleteAll 실패");

			return false;
		}
		return true;
	}
}
