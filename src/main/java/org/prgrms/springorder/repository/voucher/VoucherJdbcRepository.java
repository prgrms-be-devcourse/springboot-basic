package org.prgrms.springorder.repository.voucher;

import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.prgrms.springorder.domain.VoucherFactory;
import org.prgrms.springorder.domain.customer.Customer;
import org.prgrms.springorder.domain.voucher.Voucher;
import org.prgrms.springorder.domain.voucher.VoucherType;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Profile("jdbc")
@Repository
public class VoucherJdbcRepository implements VoucherRepository {

	private final NamedParameterJdbcTemplate jdbcTemplate;

	public VoucherJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	private final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
		var voucherId = UUID.fromString(resultSet.getString("voucher_id"));
		var value = resultSet.getInt("voucher_value");
		var createAt = resultSet.getTimestamp("created_at").toLocalDateTime();
		var voucherType = VoucherType.getVoucherByName(resultSet.getString("voucher_type"));
		return VoucherFactory.createVoucher(voucherType, voucherId, value,createAt);
	};

	private Map<String, Object> toParamMap(Voucher voucher) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("voucherId", voucher.getVoucherId().toString());
		paramMap.put("value", voucher.getValue());
		paramMap.put("createdAt", voucher.getCreatedAt());
		paramMap.put("voucherType", voucher.getVoucherType().getName());
		return paramMap;
	}

	@Override
	public void save(Voucher voucher) {

		Map<String, Object> paramMap = toParamMap(voucher);
		jdbcTemplate.update(
			"INSERT INTO voucher(voucher_id, voucher_value,created_at,voucher_type) Values(:voucherId,:value,:createdAt,:voucherType)",
			paramMap);

	}

	@Override
	public Optional<Voucher> findById(UUID voucherId) {
		try {
			return Optional.ofNullable(
				jdbcTemplate.queryForObject("SELECT * FROM voucher WHERE voucher_id = :voucherId",
					Collections.singletonMap("voucherId", voucherId.toString()), voucherRowMapper));
		} catch (DataAccessException e) {
			return Optional.empty();
		}
	}

	@Override
	public List<Voucher> findAll() {
		return jdbcTemplate.query("SELECT * FROM voucher", voucherRowMapper);
	}

	@Override
	public void delete(UUID voucherId) {
		jdbcTemplate.update("DELETE FROM voucher WHERE voucher_id = :voucherId", Collections.singletonMap("voucherId", voucherId.toString()));
	}

	@Override
	public void update(Voucher voucher) {
		Map<String, Object> paramMap = toUpdateParamMap(voucher);
		jdbcTemplate.update(
			"UPDATE voucher SET voucher_value = :value WHERE voucher_id = :voucherId",
			paramMap);
	}

	private Map<String, Object> toUpdateParamMap(Voucher voucher) {
		Map<String, Object> updateParamMap = new HashMap<>();
		updateParamMap.put("voucherId", voucher.getVoucherId().toString());
		updateParamMap.put("value", voucher.getValue());
		return updateParamMap;
	}

	public void clear() {
		jdbcTemplate.update("DELETE FROM voucher", Collections.emptyMap());
	}

}
