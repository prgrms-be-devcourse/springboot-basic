package com.example.voucher.domain.voucher.repository;

import com.example.voucher.domain.voucher.Voucher;
import com.example.voucher.domain.voucher.VoucherType;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import static com.example.voucher.exception.ErrorMessage.SERVER_ERROR;

@Repository
public class VoucherJdbcRepository implements VoucherRepository{

	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private final SimpleJdbcInsert insertAction;

	public VoucherJdbcRepository(DataSource dataSource, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		insertAction = new SimpleJdbcInsert(dataSource)
				.withTableName("vouchers")
				.usingGeneratedKeyColumns("voucher_id");
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	@Override
	public Voucher save(Voucher voucher) {
		if (voucher == null) {
			throw new IllegalArgumentException(SERVER_ERROR.getMessage());
		}
		Long voucherId = insertAction.executeAndReturnKey(toVoucherParamMap(voucher)).longValue();
		return Voucher.create(voucher.getVoucherType(), voucherId, voucher.getDiscountAmount(), voucher.getCreatedAt(), voucher.getUpdatedAt());
	}

	@Override
	public List<Voucher> findAll() {
		return namedParameterJdbcTemplate.query(
				"SELECT * FROM vouchers", voucherRowMapper);
	}

	@Override
	public void deleteAll() {

	}

	@Override
	public int deleteById(Long voucherId) {
		return namedParameterJdbcTemplate.update("DELETE FROM vouchers WHERE voucher_id = :voucherId", Collections.singletonMap("voucherId", voucherId));
	}

	@Override
	public Optional<Voucher> findById(Long voucherId) {
		List<Voucher> vouchers = namedParameterJdbcTemplate.query("SELECT * FROM vouchers WHERE voucher_id = :voucherId", Collections.singletonMap("voucherId", voucherId), voucherRowMapper);
		if (vouchers.size() != 1) {
			return Optional.empty();
		}
		return Optional.of(vouchers.get(0));
	}

	@Override
	public List<Voucher> findByCreatedAt(LocalDate createdAt) {
		return namedParameterJdbcTemplate.query(
				"SELECT * FROM vouchers WHERE DATE(created_at) = :createdAt", Collections.singletonMap("createdAt", createdAt), voucherRowMapper);
	}

	@Override
	public List<Voucher> findByVoucherType(VoucherType voucherType) {
		return namedParameterJdbcTemplate.query(
				"SELECT * FROM vouchers WHERE voucher_type = :voucherType", Collections.singletonMap("voucherType", voucherType.getTypeString()), voucherRowMapper);
	}

	private Map<String, Object> toVoucherParamMap(Voucher voucher) {
		var paramMap = new HashMap<String, Object>();
		paramMap.put("voucher_type", voucher.getVoucherType().getTypeString());
		paramMap.put("discount_amount", voucher.getDiscountAmount());
		paramMap.put("created_at", voucher.getCreatedAt());
		paramMap.put("updated_at", voucher.getUpdatedAt());
		return paramMap;
	}

	private static final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
		Long voucherId = resultSet.getLong("voucher_id");
		VoucherType voucherType = VoucherType.of(resultSet.getString("voucher_type"));
		int discountAmount = resultSet.getInt("discount_amount");
		LocalDateTime createdAt = toLocalDateTime(resultSet.getTimestamp("created_at"));
		LocalDateTime updatedAt = toLocalDateTime(resultSet.getTimestamp("created_at"));
		return Voucher.create(voucherType, voucherId, discountAmount, createdAt, updatedAt);
	};

	private static LocalDateTime toLocalDateTime(Timestamp timestamp) {
		return timestamp != null ? timestamp.toLocalDateTime() : null;
	}
}
