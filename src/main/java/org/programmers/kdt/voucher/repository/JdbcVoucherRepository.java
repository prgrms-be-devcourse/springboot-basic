package org.programmers.kdt.voucher.repository;

import org.programmers.kdt.voucher.FixedAmountVoucher;
import org.programmers.kdt.voucher.PercentDiscountVoucher;
import org.programmers.kdt.voucher.Voucher;
import org.programmers.kdt.voucher.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

import static org.programmers.kdt.utils.UuidUtils.toUUID;

@Repository
@Profile("local")
public class JdbcVoucherRepository implements VoucherRepository {
	// TODO : AOP 적용
	private static final Logger logger = LoggerFactory.getLogger(JdbcVoucherRepository.class);
	private final NamedParameterJdbcTemplate jdbcTemplate;

	public JdbcVoucherRepository(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Voucher insert(Voucher voucher) {
		int update = jdbcTemplate.update(
				"INSERT INTO vouchers(voucher_id, type, amount) VALUES (UUID_TO_BIN(:voucher_id), :type, :amount)",
				toParamMap(voucher)
		);

		if (update != 1) {
			logger.error("[FAILED] Insert a new voucher : Already Exists");
		}

		return voucher;
	}

	@Override
	public Optional<Voucher> findById(UUID voucherId) {
		try {
			return Optional.ofNullable(jdbcTemplate.queryForObject(
					"SELECT * FROM vouchers WHERE voucher_id = UUID_TO_BIN(:voucher_id)",
					Collections.singletonMap("voucher_id", voucherId.toString().getBytes()),
					rowMapper)
			);
		} catch (EmptyResultDataAccessException e) {
			logger.error("No Such Voucher(ID: {}) Exists. -> {}", voucherId, e);
			return Optional.empty();
		}
	}

	@Override
	public void deleteVoucher(UUID voucherId) {
		jdbcTemplate.update("DELETE FROM vouchers WHERE voucher_id = UUID_TO_BIN(:voucher_id)",
				Collections.singletonMap("voucher_id", voucherId.toString().getBytes()));
	}

	@Override
	public List<Voucher> findAll() {
		return jdbcTemplate.query("SELECT * FROM vouchers", rowMapper);
	}

	private final RowMapper<Voucher> rowMapper = (resultSet, rowNum) -> {
		UUID voucherId = toUUID(resultSet.getBytes("voucher_id"));
		VoucherType type = VoucherType.of(resultSet.getString("type"));
		long amount = resultSet.getLong("amount");
		return type.equals(VoucherType.FIXED) ?
				new FixedAmountVoucher(voucherId, amount) : new PercentDiscountVoucher(voucherId, amount);
	};

	private Map<String, Object> toParamMap(Voucher voucher) {
		return new HashMap<>() {
			{
				put("voucher_id", voucher.getVoucherId().toString().getBytes());
				put("type", voucher.getVoucherType().toString());
				put("amount", voucher.getDiscount());
			}
		};
	}
}
