package org.prgrms.springorder.repository.voucher;

import static org.prgrms.springorder.utils.JdbcUtil.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.prgrms.springorder.domain.voucher.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Profile("jdbc")
@Repository
public class VoucherJdbcRepository implements VoucherRepository {

	private final NamedParameterJdbcTemplate jdbcTemplate;

	public VoucherJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void save(Voucher voucher) {

		Map<String, Object> paramMap = toParamMap(voucher);
		jdbcTemplate.update(
			"INSERT INTO voucher(voucher_id, voucher_value,voucher_created_at,voucher_type) Values(:voucherId,:value,:voucherCreatedAt,:voucherType)",
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
	public void deleteById(UUID voucherId) {
		try {
			jdbcTemplate.update("DELETE FROM voucher WHERE voucher_id = :voucherId",
				Collections.singletonMap("voucherId", voucherId.toString()));
		} catch (DataAccessException e) {
			throw e;
		}
	}

	@Override
	public void updateByObject(Voucher voucher) {
		Map<String, Object> paramMap = toUpdateParamMap(voucher);
		jdbcTemplate.update(
			"UPDATE voucher SET voucher_value = :value WHERE voucher_id = :voucherId",
			paramMap);

	}

	@Override
	public void clear() {
		jdbcTemplate.update("DELETE FROM voucher", Collections.emptyMap());
	}

}
