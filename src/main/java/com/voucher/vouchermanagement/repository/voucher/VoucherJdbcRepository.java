
package com.voucher.vouchermanagement.repository.voucher;

import static com.voucher.vouchermanagement.repository.JdbcUtils.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.voucher.vouchermanagement.model.voucher.Voucher;
import com.voucher.vouchermanagement.model.voucher.VoucherType;

@Repository
@Profile("prod")
public class VoucherJdbcRepository implements VoucherRepository {

	private final NamedParameterJdbcTemplate jdbcTemplate;

	public VoucherJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Voucher> findAll() {
		return this.jdbcTemplate.query(
			"SELECT * FROM vouchers",
			voucherRowMapper
		);
	}

	@Override
	public void deleteById(UUID id) {
		jdbcTemplate.update("DELETE FROM vouchers WHERE id = UNHEX(REPLACE(:id, '-',''))",
			Collections.singletonMap("id", id.toString().getBytes()));
	}

	@Override
	public Optional<Voucher> findById(UUID id) {
		return Optional.ofNullable(
			this.jdbcTemplate.queryForObject("SELECT * FROM vouchers WHERE id = UNHEX(REPLACE(:id, '-','')) ",
				Collections.singletonMap("id", id.toString().getBytes()),
				voucherRowMapper)
		);
	}

	@Override
	public Voucher update(Voucher voucher) {
		int update = jdbcTemplate.update("UPDATE vouchers SET value = :value WHERE id = UNHEX(REPLACE(:id, '-',''))",
			toParamMap(voucher));
		if (update != 1) {
			throw new RuntimeException("수정되지 않았습니다.");
		}

		return voucher;
	}

	@Override
	public void deleteAll() {
		this.jdbcTemplate.update("DELETE FROM vouchers", Collections.emptyMap());
	}

	@Override
	public void insert(Voucher voucher) {
		try {
			int update = this.jdbcTemplate.update(
				"INSERT INTO vouchers VALUES(UNHEX(REPLACE(:id, '-', '')), :value, :type, :createdAt)",
				toParamMap(voucher));
		} catch (Exception e) {
			throw new RuntimeException("데이터를 생성할 수 없습니다.");
		}
	}

	private static final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
		UUID id = toUUID(resultSet.getBytes("id"));
		Long value = resultSet.getLong("value");
		VoucherType type = VoucherType.getVoucherTypeByName(resultSet.getString("type"));
		LocalDateTime createdAt = toLocalDateTime(resultSet.getTimestamp("created_at"));

		return type.create(id, value, createdAt);
	};

	private Map<String, Object> toParamMap(Voucher voucher) {
		HashMap<String, Object> paramMap = new HashMap<>();

		paramMap.put("id", voucher.getVoucherId().toString().getBytes());
		paramMap.put("value", voucher.getValue());
		paramMap.put("type", voucher.getClass().getSimpleName());
		paramMap.put("createdAt", voucher.getCreatedAt().toString());

		return paramMap;
	}
}
