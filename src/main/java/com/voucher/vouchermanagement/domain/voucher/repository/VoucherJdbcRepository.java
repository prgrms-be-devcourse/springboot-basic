
package com.voucher.vouchermanagement.domain.voucher.repository;

import static com.voucher.vouchermanagement.domain.voucher.repository.JdbcUtils.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.voucher.vouchermanagement.domain.voucher.model.Voucher;
import com.voucher.vouchermanagement.domain.voucher.model.VoucherCriteria;
import com.voucher.vouchermanagement.domain.voucher.model.VoucherType;

@Repository
@Profile({"jdbc", "prod"})
public class VoucherJdbcRepository implements VoucherRepository {

	private final NamedParameterJdbcTemplate jdbcTemplate;

	public VoucherJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Voucher> findAll() {
		return this.jdbcTemplate.query(
			"SELECT * FROM vouchers ORDER BY created_at",
			voucherRowMapper
		);
	}

	public List<Voucher> findByCriteria(VoucherCriteria criteria) {
		String baseSql = "SELECT * FROM vouchers ";
		String where = "WHERE ";
		String and = "AND ";
		String type = "type = :type ";
		String between = "created_at BETWEEN :startAt AND :endAt ";
		String orderBy = "ORDER BY created_at";
		StringBuilder queryBuilder = new StringBuilder();

		if (criteria.getStartAt() == null && criteria.getEndAt() == null && criteria.getType() == null) {
			queryBuilder.append(baseSql);
		} else if (criteria.getType() != null && criteria.getStartAt() != null && criteria.getEndAt() != null) {
			queryBuilder.append(baseSql).append(where).append(type).append(and).append(between);
		} else if (criteria.getType() != null && criteria.getStartAt() == null && criteria.getEndAt() == null) {
			queryBuilder.append(baseSql).append(where).append(type);
		} else if (criteria.getType() == null && criteria.getStartAt() != null && criteria.getEndAt() != null) {
			queryBuilder.append(baseSql).append(where).append(between);
		} else {
			throw new IllegalArgumentException("잘못된 쿼리 파라미터 입니다.");
		}
		queryBuilder.append(orderBy);

		return jdbcTemplate.query(
			queryBuilder.toString(),
			toCriteriaParamMap(criteria),
			voucherRowMapper);
	}

	@Override
	public void deleteById(UUID id) {
		jdbcTemplate.update("DELETE FROM vouchers WHERE id = UNHEX(REPLACE(:id, '-',''))",
			Collections.singletonMap("id", id.toString().getBytes()));
	}

	@Override
	public Optional<Voucher> findById(UUID id) {
		try {
			return Optional.of(
				this.jdbcTemplate.queryForObject("SELECT * FROM vouchers WHERE id = UNHEX(REPLACE(:id, '-','')) ",
					Collections.singletonMap("id", id.toString().getBytes()),
					voucherRowMapper));
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
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

	private Map<String, Object> toCriteriaParamMap(VoucherCriteria criteria) {
		HashMap<String, Object> paramMap = new HashMap<>();

		if (criteria.getType() != null)
			paramMap.put("type", criteria.getType().getTypeName());
		if (criteria.getStartAt() != null)
			paramMap.put("startAt", criteria.getStartAt().toString());
		if (criteria.getEndAt() != null)
			paramMap.put("endAt", criteria.getEndAt().toString());

		return paramMap;
	}
}
