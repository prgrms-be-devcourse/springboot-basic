package com.programmers.order.repository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.programmers.order.domain.Voucher;
import com.programmers.order.domain.VoucherType;
import com.programmers.order.exception.JdbcException.JdbcException;
import com.programmers.order.provider.VoucherProvider;
import com.programmers.order.utils.TranslatorUtils;

@Repository
public class VoucherJdbcRepositoryImpl implements VoucherRepository {

	private static final Integer EXIST_TRUE = 1;
	private final NamedParameterJdbcTemplate jdbcTemplate;
	private final VoucherProvider voucherProvider;

	public VoucherJdbcRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate, VoucherProvider voucherProvider) {
		this.jdbcTemplate = jdbcTemplate;
		this.voucherProvider = voucherProvider;
	}

	private Map<String, Object> toVoucherParams(Voucher voucher) {
		Map<String, Object> params = new HashMap<>();
		params.put("voucherId", voucher.getVoucherId().toString().getBytes());
		params.put("voucherType", voucher.getVoucherType().toString());
		params.put("discountValue", voucher.getDiscountValue());
		params.put("quantity", voucher.getQuantity());
		params.put("expirationAt", voucher.getExpirationAt());
		params.put("createdAt", voucher.getCreatedAt());
		params.put("updatedAt", voucher.getUpdatedAt());

		return params;
	}

	@Override
	public Voucher insert(Voucher voucher) {
		int insert = jdbcTemplate.update(
				"INSERT INTO vouchers(voucher_id, voucher_type, discount_value,quantity, expiration_at, created_at, updated_at) "
						+ "VALUES (UUID_TO_BIN(:voucherId),:voucherType,:discountValue,:quantity,:expirationAt,:createdAt,:updatedAt)",
				toVoucherParams(voucher)
		);

		if (insert != 1) {
			throw new JdbcException.NotExecuteQueryException("voucher insert 하는데 오류가 발생했습니다.");
		}

		return voucher;
	}

	@Override
	public Voucher update(Voucher voucher) {
		int update = jdbcTemplate.update(
				"update vouchers set voucher_type =:voucherType, quantity =:quantity, discount_value =:discountValue, expiration_at =:expirationAt, updated_at =:updatedAt"
						+ " where voucher_id = UUID_TO_BIN(:voucherId)",
				toVoucherParams(voucher)
		);

		if (update != 1) {
			throw new JdbcException.NotExecuteQueryException("voucher update 하는데 오류가 발생했습니다.");
		}

		return voucher;
	}

	@Override
	public Optional<Voucher> findById(UUID voucherId) {
		try {
			return Optional.ofNullable(jdbcTemplate.queryForObject(
					"select * from vouchers where voucher_id = UUID_TO_BIN(:voucherId)",
					Collections.singletonMap("voucherId", voucherId.toString().getBytes()),
					VOUCHER_ROW_MAPPER
			));

		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	@Override
	public List<Voucher> findAll() {
		return null;
	}

	@Override
	public void deleteByVoucherId(UUID voucherId) {

	}

	@Override
	public void deleteAll() {
		jdbcTemplate.update(
				"delete from vouchers",
				Collections.emptyMap()
		);
	}

	@Override
	public boolean isNotExist(UUID voucherId) {
		Integer existence = jdbcTemplate.queryForObject(
				"select count(*) from vouchers where voucher_id = :voucherId",
				Collections.singletonMap("VoucherId", voucherId.toString().getBytes()),
				Integer.class
		);

		voucherProvider.internalOf(VoucherType.FIX)
		;
		return existence != EXIST_TRUE;
	}

	private RowMapper<Voucher> VOUCHER_ROW_MAPPER = ((resultSet, rowNum) -> {
		UUID voucherId = TranslatorUtils.toUUID((resultSet.getBytes("voucher_id")));
		VoucherType voucherType = VoucherType.valueOf(resultSet.getString("voucher_type"));
		long discountValue = resultSet.getLong("discount_value");
		long quantity = resultSet.getLong("quantity");
		LocalDateTime expirationAt = TranslatorUtils.toLocalDateTIme(resultSet.getTimestamp("expiration_at"));
		LocalDateTime createdAt = TranslatorUtils.toLocalDateTIme(resultSet.getTimestamp("created_at"));
		LocalDateTime updatedAt = TranslatorUtils.toLocalDateTIme(resultSet.getTimestamp("updated_at"));

		// NOTE : voucherProvider 변수명으로 접근하면 초기화 하라고 한다...?
		return getVoucherProvider().internalOf(voucherType)
				.create(voucherId, discountValue, quantity, expirationAt, createdAt, updatedAt);
	});

	private VoucherProvider getVoucherProvider() {
		return voucherProvider;
	}
}
