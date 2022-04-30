package com.programmers.order.repository.voucher;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.programmers.order.domain.Voucher;
import com.programmers.order.dto.VoucherDto;
import com.programmers.order.exception.JdbcException;
import com.programmers.order.factory.VoucherManagerFactory;
import com.programmers.order.manager.VoucherManager;
import com.programmers.order.message.ErrorMessage;
import com.programmers.order.message.LogMessage;
import com.programmers.order.type.VoucherType;
import com.programmers.order.utils.TranslatorUtils;

@Profile("jdbc")
@Repository
public class JdbcVoucherRepository implements VoucherRepository {
	final Logger log = LoggerFactory.getLogger(JdbcVoucherRepository.class);

	private final VoucherManagerFactory voucherManagerFactory;
	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public JdbcVoucherRepository(VoucherManagerFactory voucherManagerFactory,
			NamedParameterJdbcTemplate namedJdbcTemplate) {
		this.voucherManagerFactory = voucherManagerFactory;
		this.namedParameterJdbcTemplate = namedJdbcTemplate;
	}

	private Map<String, Object> toParameters(Voucher voucher) {
		return new HashMap<>() {{
			put("voucherId", voucher.getVoucherId().toString().getBytes());
			put("voucherType", voucher.getVoucherType());
			put("discountValue", voucher.getDiscountValue());
			put("createdAt", Timestamp.valueOf(voucher.getCreatedAt()));
		}};
	}

	@Override
	public Voucher insert(Voucher voucher) {
		int update = namedParameterJdbcTemplate.update(
				"INSERT INTO vouchers(voucher_id, voucher_type, discount_value, created_at) VALUES (UUID_TO_BIN(:voucherId), :voucherType , :discountValue, :createdAt)",
				toParameters(voucher));

		if (update != 1) {
			log.error(LogMessage.ErrorLogMessage.getPrefix(), LogMessage.ErrorLogMessage.NOT_EXECUTE_QUERY);
			throw new JdbcException.NotExecuteQuery(ErrorMessage.INTERNAL_PROGRAM_ERROR);
		}

		return voucher;
	}

	@Override
	public List<Voucher> findAll() {
		return namedParameterJdbcTemplate.query("select * from vouchers", this.getVoucherRowMapper());
	}

	@Override
	public Optional<Voucher> findById(UUID voucherId) {

		try {
			return Optional.ofNullable(
					namedParameterJdbcTemplate
							.queryForObject("select * from vouchers where voucher_id = UUID_TO_BIN(:voucher_id)",
									Collections.singletonMap("voucher_id", voucherId.toString().getBytes()),
									this.getVoucherRowMapper()));
		} catch (EmptyResultDataAccessException e) {
			log.error(LogMessage.ErrorLogMessage.getPrefix(), LogMessage.ErrorLogMessage.NOT_FOUND_RESOURCE);
			return Optional.empty();
		}
	}

	@Override
	public void delete(UUID voucherId) {
		namedParameterJdbcTemplate.update(
				"DELETE FROM customers where customer_id = :customerId",
				Collections.singletonMap("voucher_id", voucherId.toString().getBytes())
		);
	}

	private RowMapper<Voucher> getVoucherRowMapper() {
		return (rs, rowNum) -> {
			UUID id = TranslatorUtils.toUUID(rs.getBytes("voucher_id"));
			String voucherType = rs.getString("voucher_type");
			long discountValue = rs.getLong("discount_value");
			LocalDateTime createdAt = rs.getTimestamp(("created_at")).toLocalDateTime();

			VoucherType type = VoucherType.MatchTheType(voucherType);
			VoucherManager voucherManager = voucherManagerFactory.getVoucherManager(type);
			VoucherDto.Resolver resolver = new VoucherDto.Resolver(id, discountValue, createdAt);
			return voucherManager.resolve(resolver);
		};
	}

}
