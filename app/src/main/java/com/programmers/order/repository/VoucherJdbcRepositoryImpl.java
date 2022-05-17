package com.programmers.order.repository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.programmers.order.domain.Voucher;
import com.programmers.order.domain.VoucherType;
import com.programmers.order.exception.JdbcException;
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

	public long count() {
		return jdbcTemplate.queryForObject(
				"select count(*) vouhers",
				Collections.emptyMap()
				, Long.class
		);
	}

	@Override
	public Page<Voucher> findAll(Pageable pageable) {
		List<Voucher> vouchers = jdbcTemplate.query(
				"select * from vouchers order by created_at desc limit :pageSize offSet :offSet",
				toPagingParams(pageable),
				VOUCHER_ROW_MAPPER
		);

		Long count = jdbcTemplate.queryForObject(
				"select count(*) from vouchers",
				Collections.emptyMap(),
				Long.class
		);

		if (count == null) {
			throw new JdbcException.NotExecuteQueryException("voucher 조회 중 count query 가 실행되지 않습니다.");
		}

		return new PageImpl<Voucher>(vouchers, pageable, count);
	}

	@Override
	public void deleteByVoucherId(UUID voucherId) {
		int delete = jdbcTemplate.update(
				"delete from vouchers where voucher_id=UUID_TO_BIN(:voucherId)",
				Collections.singletonMap("voucherId", voucherId.toString().getBytes())
		);

		if (delete != 1) {
			throw new JdbcException.NotExecuteQueryException("delete query 정상적으로 동작하지 않았습니다..");
		}
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

	private Map<String, Object> toPagingParams(Pageable pageable) {
		Map<String, Object> map = new HashMap<>();

		// bug : 해당 부분이 내림차순으로 작동이 안됨 ...
		String sort = pageable.getSort().toString().replace(":", " ");
		map.put("sort", sort);
		// bug...

		map.put("offSet", pageable.getOffset());
		map.put("pageSize", pageable.getPageSize());
		return map;
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
