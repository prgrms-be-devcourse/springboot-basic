package com.programmers.order.repository;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.programmers.order.domain.Voucher;
import com.programmers.order.exception.JdbcException.JdbcException;
import com.programmers.order.provider.VoucherProvider;

@Repository
public class VoucherJdbcRepositoryImpl implements VoucherRepository {

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
			throw new JdbcException.NotExecuteQueryException("voucher insert 하는데 오류가 났습니다.");
		}

		return voucher;
	}

	@Override
	public Voucher update(Voucher voucher) {
		return null;
	}

	@Override
	public Optional<Voucher> findById(UUID voucherId) {
		return Optional.empty();
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
}
