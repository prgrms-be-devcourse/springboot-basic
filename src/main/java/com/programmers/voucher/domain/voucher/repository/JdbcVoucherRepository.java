package com.programmers.voucher.domain.voucher.repository;

import java.nio.ByteBuffer;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.programmers.voucher.domain.voucher.model.Voucher;
import com.programmers.voucher.domain.voucher.model.VoucherType;
import com.programmers.voucher.domain.voucher.util.VoucherFactory;
import com.programmers.voucher.exception.DataUpdateException;
import com.programmers.voucher.exception.ExceptionMessage;
import com.programmers.voucher.exception.NotFoundException;

@Repository
@Profile({"jdbc", "test"})
public class JdbcVoucherRepository implements VoucherRepository {

	private static final Logger log = LoggerFactory.getLogger(JdbcVoucherRepository.class);
	private final NamedParameterJdbcTemplate jdbcTemplate;

	public JdbcVoucherRepository(DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public Voucher save(Voucher voucher) {
		int save = jdbcTemplate.update(
			"INSERT INTO vouchers(voucher_id, voucher_type, discount, created_at) VALUES (UUID_TO_BIN(:voucherId), :voucherType, :discount, :createdAt)",
			toParamMap(voucher));

		if (save != 1) {
			log.error(ExceptionMessage.DATA_UPDATE_FAIL.getMessage());
			throw new DataUpdateException();
		}
		return voucher;
	}

	@Override
	public Voucher findById(UUID voucherId) {
		return Optional.ofNullable(
				jdbcTemplate.queryForObject("SELECT * FROM vouchers WHERE voucher_id = UUID_TO_BIN(:voucherId)",
					toIdMap(voucherId),
					voucherRowMapper))
			.orElseThrow(() -> {
				log.error(ExceptionMessage.VOUCHER_NOT_FOUND.getMessage());
				throw new NotFoundException(ExceptionMessage.VOUCHER_NOT_FOUND.getMessage());
			});
	}

	@Override
	public void delete(UUID voucherId) {
		int delete = jdbcTemplate.update("DELETE FROM vouchers WHERE voucher_id = UUID_TO_BIN(:voucherId)",
			toIdMap(voucherId));

		if (delete != 1) {
			log.error(ExceptionMessage.VOUCHER_NOT_FOUND.getMessage());
			throw new NotFoundException(ExceptionMessage.VOUCHER_NOT_FOUND.getMessage());
		}
	}

	@Override
	public List<Voucher> findAll() {
		return jdbcTemplate.query("SELECT * FROM vouchers", voucherRowMapper);
	}

	@Override
	public void clear() {
		jdbcTemplate.update("DELETE FROM vouchers", Collections.emptyMap());
	}

	private Map<String, Object> toParamMap(Voucher voucher) {
		return Map.of(
			"voucherId", voucher.getVoucherId().toString(),
			"voucherType", voucher.getVoucherType().name(),
			"discount", voucher.getDiscount(),
			"createdAt", voucher.getCreatedAt()
		);
	}

	private Map<String, Object> toIdMap(UUID voucherId) {
		return Collections.singletonMap("voucherId", voucherId.toString());
	}

	private RowMapper<Voucher> voucherRowMapper = (ResultSet rs, int rowNum) -> {
		UUID voucherId = toUUID(rs.getBytes("voucher_id"));
		String discount = String.valueOf(rs.getDouble("discount"));
		VoucherType voucherType = VoucherType.getVoucherType(rs.getString("voucher_type"));
		LocalDateTime createdAt = rs.getObject("created_at", LocalDateTime.class);
		return VoucherFactory.createVoucher(voucherId, voucherType, discount, createdAt);
	};

	private UUID toUUID(byte[] bytes) {
		ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
		return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
	}
}
