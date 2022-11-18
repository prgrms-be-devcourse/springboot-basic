package org.prgrms.springorder.repository.voucher;

import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.prgrms.springorder.domain.VoucherFactory;
import org.prgrms.springorder.domain.voucher.Voucher;
import org.prgrms.springorder.domain.voucher.VoucherType;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Profile("jdbc")
@Repository
public class VoucherJdbcRepository implements VoucherRepository{

	private final NamedParameterJdbcTemplate jdbcTemplate;

	public VoucherJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	private static UUID toUUID(byte[] bytes) {
		ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
		return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
	}

	private final RowMapper<Voucher> voucherRowMapper = (resultSet, i)->{
		var voucherId = toUUID(resultSet.getBytes("voucher_id"));
		var value = resultSet.getInt("voucher_value");
		var voucherType = VoucherType.getVoucherByName(resultSet.getString("voucher_type"));
		return VoucherFactory.createVoucher(voucherType, voucherId, value);
	};

	private Map<String, Object> toParamMap(Voucher voucher) {
		return new HashMap<>(){{
			put("voucherId", voucher.getVoucherId().toString());
			put("value", voucher.getValue());
			put("createdAt", voucher.getCreatedAt());
			put("voucherType", voucher.getVoucherType().getName());
		}};
	}

	@Override
	public void save(Voucher voucher) {

		Map<String, Object> paramMap = toParamMap(voucher);
		jdbcTemplate.update("INSERT INTO voucher(voucher_id, voucher_value,created_at,voucher_type) Values(UUID_TO_BIN(:voucherId),:value,:createdAt,:voucherType)",paramMap);

	}

	@Override
	public Optional<Voucher> findById(UUID voucherId) {
		return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM voucher WHERE voucher_id = UUID_TO_BIN(:voucherId)",
			Collections.singletonMap("voucherId", voucherId.toString().getBytes()), voucherRowMapper));
	}

	@Override
	public List<Voucher> findAll() {
		return jdbcTemplate.query("SELECT * FROM voucher", voucherRowMapper);
	}

	@Override
	public void delete(UUID voucherId) {
		jdbcTemplate.update("DELETE FROM voucher WHERE voucher_id = uuid_to_bin(:voucherId)", Collections.emptyMap());
	}





}
