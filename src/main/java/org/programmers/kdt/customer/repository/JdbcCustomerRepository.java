package org.programmers.kdt.customer.repository;

import org.programmers.kdt.customer.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

import static org.programmers.kdt.utils.UuidUtils.toUUID;

@Repository
@Profile("local")
public class JdbcCustomerRepository implements CustomerRepository {
	// TODO : AOP 적용
	private static final Logger logger = LoggerFactory.getLogger(JdbcCustomerRepository.class);
	private final NamedParameterJdbcTemplate jdbcTemplate;

	public JdbcCustomerRepository(DataSource dataSource) {
		jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public Customer insert(Customer customer) {
		int update = jdbcTemplate.update(
				"INSERT INTO customers(customer_id, name, email, last_login_at, created_at) VALUES (UUID_TO_BIN(:customer_id), :name, :email, :last_login_at, :created_at)",
				toParamMap(customer)
		);

		if (update != 1) {
			logger.error("[FAILED] Insert a new customer : Already Exists");
		}

		return customer;
	}

	@Override
	public void deleteCustomer(UUID customerId) {
		jdbcTemplate.update("DELETE FROM customers WHERE customer_id = UUID_TO_BIN(:customer_id)", Collections.singletonMap("customer_id", customerId.toString().getBytes()));
		jdbcTemplate.update("DELETE FROM customers_blacklist WHERE customer_id = UUID_TO_BIN(:customer_id)", Collections.singletonMap("customer_id", customerId.toString().getBytes()));
	}

	@Override
	public void deleteAll() {
		jdbcTemplate.update("DELETE FROM customers", Collections.emptyMap());
	}

	@Override
	public Optional<Customer> findById(UUID customerId) {
		try {
			return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM customers WHERE customer_id = UUID_TO_BIN(:customerId)",
					Collections.singletonMap("customerId", customerId.toString().getBytes()),
					rowMapper));
		} catch (EmptyResultDataAccessException e) {
			logger.error("No Such Customer(ID: {0}) Exists. -> {}", customerId, e);
			return Optional.empty();
		}
	}

	@Override
	public List<Customer> findByName(String name) {
		return jdbcTemplate.query("SELECT * FROM customers WHERE name = :name",
					Collections.singletonMap("name", name),
					rowMapper);
	}

	@Override
	public Optional<Customer> findByEmail(String email) {
		try {
			return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM customers WHERE email = :email",
					Collections.singletonMap("email", email),
					rowMapper));
		} catch (EmptyResultDataAccessException e) {
			logger.error("No Such Customer(email: {0}) Exists. -> {}", email, e);
			return Optional.empty();
		}
	}

	@Override
	public List<Customer> findAll() {
		return jdbcTemplate.query("SELECT * FROM customers", rowMapper);
	}

	@Override
	public Customer registerToBlacklist(Customer customer) {
		int update = jdbcTemplate.update(
				"INSERT INTO customers_blacklist(customer_id, name, email, last_login_at, created_at) VALUES (UUID_TO_BIN(:customer_id), :name, :email, :last_login_at, :created_at)",
				toParamMap(customer)
		);

		if (update != 1) {
			logger.error("[FAILED] Register a customer to blacklist : Already Exists");
		}

		return customer;
	}

	@Override
	public List<Customer> findAllBlacklistCustomer() {
		return jdbcTemplate.query("SELECT * FROM customers_blacklist", rowMapper);
	}

	private final RowMapper<Customer> rowMapper = (resultSet, rowNum) -> {
			UUID customerId = toUUID(resultSet.getBytes("customer_id"));
			String name = resultSet.getString("name");
			String email = resultSet.getString("email");
			LocalDateTime lastLoginAt = resultSet.getTimestamp("last_login_at") != null ?
					resultSet.getTimestamp("last_login_at").toLocalDateTime() : null;
			LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
			return new Customer(customerId, name, email, lastLoginAt, createdAt);
		};

	private Map<String, Object> toParamMap(Customer customer) {
		return new HashMap<>() {
			{
				put("customer_id", customer.getCustomerId().toString().getBytes());
				put("name", customer.getName());
				put("email", customer.getEmail());
				put("last_login_at", customer.getLastLoginAt() != null ? Timestamp.valueOf(customer.getLastLoginAt()) : null);
				put("created_at", customer.getCreatedAt());
			}
		};
	}
}
