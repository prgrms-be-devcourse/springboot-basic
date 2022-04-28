package com.programmers.order.repository.customervoucher;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.programmers.order.domain.CustomerVoucher;
import com.programmers.order.exception.JdbcException;
import com.programmers.order.message.ErrorLogMessage;
import com.programmers.order.message.ErrorMessage;
import com.programmers.order.message.InfoLogMessage;
import com.programmers.order.utils.TranslatorUtils;

@Profile("jdbc")
@Repository
public class JdbcCustomerVoucherRepository implements CustomerVoucherRepository {
	private static final Logger log = LoggerFactory.getLogger(JdbcCustomerVoucherRepository.class);

	private final DataSource dataSource;
	private final JdbcTemplate jdbcTemplate;
	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	private static final RowMapper<CustomerVoucher> CUSTOMER_ROW_MAPPER = (rs, rowNum) -> {
		UUID id = TranslatorUtils.toUUID(rs.getBytes("id"));
		UUID voucherId = TranslatorUtils.toUUID(rs.getBytes("voucher_id"));
		UUID customerId = TranslatorUtils.toUUID(rs.getBytes("customer_id"));
		LocalDateTime createdAt = rs.getTimestamp(("created_at")).toLocalDateTime();

		return new CustomerVoucher(id, customerId, voucherId, createdAt);
	};

	public JdbcCustomerVoucherRepository(DataSource dataSource, JdbcTemplate jdbcTemplate,
			NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.dataSource = dataSource;
		this.jdbcTemplate = jdbcTemplate;
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	private Map<String, Object> toParameters(CustomerVoucher customerVoucher) {
		return new HashMap<>() {{
			put("id", customerVoucher.getId().toString().getBytes());
			put("customerId", customerVoucher.getCustomerId().toString().getBytes());
			put("voucherId", customerVoucher.getVoucherId().toString().getBytes());
			put("createdAt", customerVoucher.getCreatedAt().toString().getBytes());
		}};
	}

	@Override
	public CustomerVoucher insert(CustomerVoucher customerVoucher) {
		int update = namedParameterJdbcTemplate.update(
				"INSERT INTO customer_voucher(id,customer_id,voucher_id,created_at) values (UUID_TO_BIN(:id) ,UUID_TO_BIN(:customerId), UUID_TO_BIN(:voucherId), :createdAt)",
				toParameters(customerVoucher));

		if (update != 1) {
			log.error(ErrorLogMessage.getPrefix(), ErrorLogMessage.NOT_EXECUTE_QUERY);
			throw new JdbcException.NotExecuteQuery(ErrorMessage.INTERNAL_PROGRAM_ERROR);
		}

		return customerVoucher;
	}

	@Override
	public Optional<CustomerVoucher> findByVoucherId(UUID voucherId) {
		try {
			return Optional.ofNullable(
					namedParameterJdbcTemplate.queryForObject(
							"select * from customer_voucher where voucher_id = UUID_TO_BIN(:voucherId)",
							Collections.singletonMap("voucherId", voucherId.toString().getBytes()), CUSTOMER_ROW_MAPPER)
			);
		} catch (EmptyResultDataAccessException e) {
			log.error(ErrorLogMessage.getPrefix(), ErrorLogMessage.NOT_FOUND_RESOURCE);
		}

		return Optional.empty();
	}

	@Override
	public Optional<CustomerVoucher> findByCustomerId(UUID customerId) {
		try {
			return Optional.ofNullable(
					namedParameterJdbcTemplate.queryForObject(
							"select * from customer_voucher where customer_id = UUID_TO_BIN(:customerId)",
							Collections.singletonMap("customerId", customerId.toString().getBytes()),
							CUSTOMER_ROW_MAPPER)
			);
		} catch (EmptyResultDataAccessException e) {
			log.error(ErrorLogMessage.getPrefix(), ErrorLogMessage.NOT_FOUND_RESOURCE);
		}

		return Optional.empty();
	}

	@Override
	public boolean isDuplicatePublish(UUID customerId, UUID voucherId) {
		try {
			return Optional.ofNullable(
					namedParameterJdbcTemplate.queryForObject(
							"SELECT id FROM customer_voucher WHERE customer_id = UUID_TO_BIN(:customerId) and voucher_id = UUID_TO_BIN(:voucherId);",
							Map.ofEntries(
									Map.entry("voucherId", voucherId.toString().getBytes()),
									Map.entry("customerId", customerId.toString().getBytes())
							), (rs, rowNom) -> TranslatorUtils.toUUID(rs.getBytes("id")))
			).isPresent();
		} catch (EmptyResultDataAccessException e) {
			log.info(InfoLogMessage.getPrefix(),InfoLogMessage.POSSIBLE_REGISTER);
			return false;
		}

	}

}
