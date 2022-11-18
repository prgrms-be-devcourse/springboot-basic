package org.prgrms.springorder.repository.voucher;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.prgrms.springorder.domain.VoucherFactory;
import org.prgrms.springorder.domain.voucher.Voucher;
import org.prgrms.springorder.domain.voucher.VoucherType;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class VoucherJdbcRepository implements VoucherRepository{

	private final NamedParameterJdbcTemplate jdbcTemplate;

	public VoucherJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	private final RowMapper<Voucher> voucherRowMapper = (resultSet, i)->{
		var voucherId = UUID.fromString(resultSet.getString("voucher_id"));
		var value = resultSet.getInt("voucher_value");
		var createAt = resultSet.getTimestamp("created_at").toLocalDateTime();
		var voucherType = VoucherType.getVoucherByName("voucher_type");
		return VoucherFactory.createVoucher(voucherType, voucherId, value);
	};

	private Map<String, Object> toParamMap(Voucher voucher) {
		return new HashMap<>(){{
			put("voucherId", voucher.getVoucherId());
			put("value", voucher.getValue());
			put("createdAt", voucher.getCreatedAt());
			put("voucherType", voucher.getVoucherType());
		}};
	}

	@Override
	public void save(Voucher voucher) {
		Map<String, Object> paramMap = toParamMap(voucher);
		jdbcTemplate.update("INSERT INTO voucher(voucher_id, voucher_value,created_at,voucher_type) Values(:voucherId,:voucher_value,created_at,voucher_type)",paramMap);

	}

	@Override
	public Optional<Voucher> findById(UUID voucherId) {
		return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM voucher WHERE voucher_id = :voucherId",
			Collections.singletonMap("voucherId", voucherId.toString()), voucherRowMapper));
	}

	@Override
	public List<Voucher> findAll() {
		return jdbcTemplate.query("SELECT * FROM voucher", voucherRowMapper);
	}

	public void update(Voucher voucher) {
		Map<String, Object> paramMap = toParamMap(voucher);
		jdbcTemplate.update("UPDATE voucher SET voucher_value = :value WHERE voucher_id = :voucherId",paramMap);
	}

	@Override
	public void delete(UUID voucherId) {
		jdbcTemplate.update("DELETE FROM voucher WHERE voucher_id = :voucherId", Collections.emptyMap());
	}

	public void updateCustomerIdByVoucherId(UUID customerId, UUID voucherId){
		Map<String, Object> paramMap = toIdParamMap(customerId,voucherId);
		jdbcTemplate.update("UPDATE voucher SET customer_id = :customerId WHERE voucher_id = :voucherId", paramMap);
	}

	private Map<String, Object> toIdParamMap(UUID customerId, UUID voucherId) {
		return new HashMap<>(){{
			put("customerId", customerId);
			put("voucherId", voucherId);
		}};
	}

	public List<Voucher> findVouchersByCustomerId(UUID customerId){
		return jdbcTemplate.query("SELECT * FROM voucher INNER JOIN customer on voucher.customer_id = customer.customer_id "
				+ "WHERE voucher.customer_id = :customerId",
			Collections.singletonMap("customerId", customerId.toString()),voucherRowMapper);
	}

	public void delete(UUID customerId, UUID voucherId){
		jdbcTemplate.update("DELETE FROM voucher Using voucher INNER JOIN customer on voucher.customer_id = customer.customer_id "
				+ "WHERE voucher.customer_id = :customerId AND voucher_id = :voucherId",
			Collections.emptyMap());
	}




}
