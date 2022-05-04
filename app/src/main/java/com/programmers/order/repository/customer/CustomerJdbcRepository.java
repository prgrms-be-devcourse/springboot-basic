package com.programmers.order.repository.customer;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.programmers.order.domain.Customer;
import com.programmers.order.exception.JdbcException;
import com.programmers.order.message.ErrorMessage;
import com.programmers.order.message.LogMessage;
import com.programmers.order.utils.TranslatorUtils;

@Profile("jdbc")
@Repository
public class CustomerJdbcRepository implements CustomerRepository {

	final Logger log = LoggerFactory.getLogger(CustomerJdbcRepository.class);

	private final DataSource dataSource;
	private final JdbcTemplate jdbcTemplate;
	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	private static final RowMapper<Customer> CUSTOMER_ROW_MAPPER = (rs, rowNum) -> {
		UUID id = TranslatorUtils.toUUID(rs.getBytes("customer_id"));
		String name = rs.getString("name");
		String email = rs.getString("email");
		LocalDateTime lastLoginAt = rs.getTimestamp("last_login_at")
				== null ? null : rs.getTimestamp("last_login_at")
				.toLocalDateTime();
		LocalDateTime createdAt = rs.getTimestamp(("created_at")).toLocalDateTime();

		return new Customer(id, name, email, lastLoginAt, createdAt);
	};

	public CustomerJdbcRepository(DataSource dataSource, JdbcTemplate jdbcTemplate,
			NamedParameterJdbcTemplate namedParameterJdbcTemplate1) {
		this.dataSource = dataSource;
		this.jdbcTemplate = jdbcTemplate;
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate1;
	}

	private Map<String, Object> toParameters(Customer customer) {
		return new HashMap<>() {{
			put("customerId", customer.getCustomerId().toString().getBytes());
			put("name", customer.getName());
			put("email", customer.getEmail());
			put("createdAt", Timestamp.valueOf(customer.getCreatedAt()));
			put("lastLoginAt", customer.getLastLoginAt() != null ? Timestamp.valueOf(customer.getLastLoginAt()) : null);
		}};
	}

	@Override
	public int count() {
		return namedParameterJdbcTemplate.queryForObject("select count(*) from customers",
				Collections.EMPTY_MAP,
				Integer.class);
	}

	@Override
	public int countByEmail(String email) {
		return namedParameterJdbcTemplate.queryForObject("select count(*) from customers where email=:email",
				Collections.singletonMap("email", email),
				Integer.class);
	}

	@Override
	public Customer insert(Customer customer) {
		int update = namedParameterJdbcTemplate.update(
				"INSERT INTO customers(customer_id, name, email, created_at) VALUES (UUID_TO_BIN(:customerId), :name , :email, :createdAt)",
				toParameters(customer));

		if (update != 1) {
			log.error(LogMessage.ErrorLogMessage.getPrefix(), LogMessage.ErrorLogMessage.NOT_EXECUTE_QUERY);
			throw new JdbcException.NotExecuteQuery(ErrorMessage.INTERNAL_PROGRAM_ERROR);
		}

		return customer;
	}

	@Override
	public Customer update(Customer customer) {
		int result = namedParameterJdbcTemplate.update(
				"UPDATE customers SET name = :name, email = :email, last_login_at = :lastLoginAt WHERE customer_id = UUID_TO_BIN(:customerId)",
				toParameters(customer)
		);

		if (result != 1) {
			log.error(LogMessage.ErrorLogMessage.getPrefix(), LogMessage.ErrorLogMessage.NOT_EXECUTE_QUERY);
			throw new JdbcException.NotExecuteQuery(ErrorMessage.INTERNAL_PROGRAM_ERROR);
		}

		return customer;
	}

	@Override
	public List<Customer> findAll() {
		return namedParameterJdbcTemplate.query("select * from customers", CUSTOMER_ROW_MAPPER);
	}

	@Override
	public Optional<Customer> findById(UUID customerId) {

		try {
			return Optional.ofNullable(
					namedParameterJdbcTemplate.queryForObject(
							"select * from customers where customer_id = UUID_TO_BIN( :customerId )",
							Collections.singletonMap("customerId", customerId.toString().getBytes()),
							CUSTOMER_ROW_MAPPER)
			);
		} catch (EmptyResultDataAccessException e) {
			log.error(LogMessage.ErrorLogMessage.getPrefix(), LogMessage.ErrorLogMessage.NOT_FOUND_RESOURCE);
		}

		return Optional.empty();
	}

	@Override
	public Optional<Customer> findByName(String name) {

		try {
			return Optional.ofNullable(
					namedParameterJdbcTemplate.queryForObject("SELECT * FROM customers WHERE name =:name",
							Collections.singletonMap("name", name),
							CUSTOMER_ROW_MAPPER)
			);
		} catch (EmptyResultDataAccessException e) {
			log.error(LogMessage.ErrorLogMessage.getPrefix(), LogMessage.ErrorLogMessage.NOT_FOUND_RESOURCE);
		}

		return Optional.empty();
	}

	@Override
	public Optional<Customer> findByEmail(String email) {
		try {
			return Optional.ofNullable(
					namedParameterJdbcTemplate.queryForObject("select * from customers WHERE email = :email",
							Collections.singletonMap("email", email),
							CUSTOMER_ROW_MAPPER));
		} catch (EmptyResultDataAccessException e) {
			log.error(LogMessage.ErrorLogMessage.getPrefix(), LogMessage.ErrorLogMessage.NOT_FOUND_RESOURCE);
		}

		return Optional.empty();
	}

	@Override
	public void deleteAll() {
		namedParameterJdbcTemplate.update("DELETE FROM customers", Collections.emptyMap());
	}

}
