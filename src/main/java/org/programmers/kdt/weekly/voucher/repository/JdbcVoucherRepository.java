package org.programmers.kdt.weekly.voucher.repository;

import static org.programmers.kdt.weekly.utils.UtilFunction.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.programmers.kdt.weekly.voucher.model.Voucher;
import org.programmers.kdt.weekly.voucher.model.VoucherType;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class JdbcVoucherRepository implements VoucherRepository {

	private static final int SUCCESS = 1;
	private final NamedParameterJdbcTemplate jdbcTemplate;

	public JdbcVoucherRepository(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	private final RowMapper<Voucher> voucherRowMapper = (rs, rowMapper) -> {
		var voucherId = toUUID(rs.getBytes("voucher_id"));
		var voucherType = VoucherType.valueOf(rs.getString("type"));
		var value = Integer.parseInt(rs.getString("value"));
		var createdAt = toLocalDateTime(rs.getTimestamp("created_at"));

		return voucherType.create(voucherId, value, createdAt);
	};

	private Map<String, Object> toParamMap(Voucher voucher) {
		HashMap<String, Object> voucherMap = new HashMap<>();

		voucherMap.put("voucherId", voucher.getVoucherId().toString().getBytes());
		voucherMap.put("value", voucher.getValue());
		voucherMap.put("voucherType", voucher.getVoucherType().name());
		voucherMap.put("createdAt", voucher.getCreatedAt().toString());

		return voucherMap;
	}

	@Override
	public Voucher insert(Voucher voucher) {
		String insertSql = "INSERT INTO voucher(voucher_id, value, type, created_at) VALUES(UNHEX(REPLACE(:voucherId, '-', '')), :value, :voucherType, :createdAt)";
		var update = this.jdbcTemplate.update(insertSql, toParamMap(voucher));

		if (update != SUCCESS) {
			throw new RuntimeException("Failed to save voucher");
		}

		return voucher;
	}

	@Override
	public Voucher update(Voucher voucher) {
		String updateValueSql = "UPDATE voucher SET value = :value WHERE voucher_id = UNHEX(REPLACE(:voucherId, '-', ''))";
		var update = this.jdbcTemplate.update(updateValueSql, toParamMap(voucher));

		if (update != SUCCESS) {
			throw new RuntimeException("Failed to update voucher");
		}
		return voucher;
	}

	@Override
	public List<Voucher> findAll() {
		String selectSql = "SELECT * FROM voucher";

		return this.jdbcTemplate.query(selectSql, voucherRowMapper);
	}

	@Override
	public Optional<Voucher> findById(UUID voucherId) {
		String selectByIdSql = "SELECT * FROM voucher WHERE voucher_id = UNHEX(REPLACE(:voucherId, '-', ''))";
		var voucher = this.jdbcTemplate.queryForObject(selectByIdSql,
			Collections.singletonMap("voucherId", voucherId.toString().getBytes()),
			voucherRowMapper);

		return Optional.ofNullable(voucher);
	}

	@Override
	public List<Voucher> findByType(VoucherType voucherType) {
		String findByTypeSql = "SELECT * FROM voucher WHERE type = :voucherType";
		var vouchers = this.jdbcTemplate.query(findByTypeSql,
			Collections.singletonMap("voucherType", voucherType.toString()),
			voucherRowMapper);

		return vouchers;
	}

	@Override
	public void deleteById(UUID voucherId) {
		String deleteByIdSql = "DELETE FROM voucher WHERE voucher_id = UNHEX(REPLACE(:voucherId, '-', ''))";
		this.jdbcTemplate.update(deleteByIdSql,
			Collections.singletonMap("voucherId", voucherId.toString().getBytes()));
	}

	@Override
	public void deleteAll() {
		String deleteSql = "DELETE FROM voucher";
		this.jdbcTemplate.update(deleteSql, Collections.emptyMap());
	}

	@Override
	public List<Voucher> findByCreatedAt(LocalDate begin, LocalDate end) {
		String findByCreatedAtSql = "SELECT * FROM voucher WHERE DATE(created_at) between :begin and :end";

		return this.jdbcTemplate.query(findByCreatedAtSql, Map.of("begin", begin, "end", end),
			voucherRowMapper);
	}
}