package com.example.voucher.domain.voucher.repository;

import com.example.voucher.domain.voucher.Voucher;
import com.example.voucher.domain.voucher.VoucherType;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
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
			throw new IllegalArgumentException(SERVER_ERROR.name());
		}
		Long voucherId = insertAction.executeAndReturnKey(toVoucherParamMap(voucher)).longValue();
		return voucher.getVoucherType()
					  .create(voucherId, voucher.getDiscountAmount());
	}

	@Override
	public List<Voucher> findAll() {
		return namedParameterJdbcTemplate.query(
				"SELECT * FROM vouchers", Collections.emptyMap(), voucherRowMapper);
	}

	@Override
	public void deleteAll() {

	}

	private Map<String, Object> toVoucherParamMap(Voucher voucher) {
		var paramMap = new HashMap<String, Object>();
		paramMap.put("voucher_type", voucher.getVoucherType().getTypeString());
		paramMap.put("discount_amount", voucher.getDiscountAmount());
		return paramMap;
	}

	private static final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
		Long voucherId = resultSet.getLong("voucher_id");
		VoucherType voucherType = VoucherType.of(resultSet.getString("voucher_type"));
		int discountAmount = resultSet.getInt("discount_amount");
		return voucherType.create(voucherId, discountAmount);
	};
}
