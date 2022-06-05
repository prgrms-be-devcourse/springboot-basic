
package com.voucher.vouchermanagement.domain.voucher.repository;

import static com.voucher.vouchermanagement.domain.voucher.repository.JdbcUtils.*;

import java.time.LocalDateTime;
import java.util.*;

import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Order;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.voucher.vouchermanagement.domain.voucher.model.Voucher;
import com.voucher.vouchermanagement.domain.voucher.model.VoucherCriteria;
import com.voucher.vouchermanagement.domain.voucher.model.VoucherType;
import com.voucher.vouchermanagement.domain.voucher.repository.criteriaquerybuilder.NamedParameterJdbcTemplateQueryBuilder;
import com.voucher.vouchermanagement.domain.voucher.repository.criteriaquerybuilder.builder.OrderByBuilder;
import com.voucher.vouchermanagement.domain.voucher.repository.criteriaquerybuilder.builder.WhereBuilder;

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

	@Override
	public Page<Voucher> findByCriteria(VoucherCriteria criteria, Pageable pageable) {
		Order order = !pageable.getSort().isEmpty() ? pageable.getSort().toList().get(0) : Order.by("id");
		String query = NamedParameterJdbcTemplateQueryBuilder
			.builder("*","vouchers")
			.where(
				criteria.getType() != null ? WhereBuilder.single("type", "type") : null,
				criteria.getPeriod() != null ? WhereBuilder.between("created_at", "startAt", "endAt") : null
			)
			.orderBy(OrderByBuilder.orderBy(order.getProperty(), order.isAscending()))
			.limit(pageable.getPageSize())
			.offset(pageable.getOffset())
			.build();

		List<Voucher> vouchers = jdbcTemplate.query(
			query,
			toCriteriaParamMap(criteria),
			voucherRowMapper);

		return new PageImpl<>(vouchers, pageable, count());
	}

	private int count() {
		return this.jdbcTemplate.getJdbcTemplate().queryForObject("SELECT COUNT(*) FROM vouchers", Integer.class);
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
		if (criteria.getPeriod()!= null && criteria.getPeriod().getStartAt() != null)
			paramMap.put("startAt", criteria.getPeriod().getStartAt().toString());
		if (criteria.getPeriod()!= null && criteria.getPeriod().getEndAt() != null)
			paramMap.put("endAt", criteria.getPeriod().getEndAt().toString());

		return paramMap;
	}
}
