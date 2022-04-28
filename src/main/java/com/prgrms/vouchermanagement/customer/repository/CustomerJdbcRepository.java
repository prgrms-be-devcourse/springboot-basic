package com.prgrms.vouchermanagement.customer.repository;

import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.prgrms.vouchermanagement.commons.exception.UpdateFailException;
import com.prgrms.vouchermanagement.customer.domain.Customer;

@Repository
public class CustomerJdbcRepository implements CustomerRepository {

	private static final Logger logger = LoggerFactory.getLogger(CustomerJdbcRepository.class);

	private final JdbcTemplate jdbcTemplate;

	private static final RowMapper<Customer> customerRowMapper = (resultSet, i) -> {
		var customerName = resultSet.getString("name");
		var email = resultSet.getString("email");
		var customerId = toUUID(resultSet.getBytes("customer_id"));
		var lastLoginAt = resultSet.getTimestamp("last_login_at") != null ?
			resultSet.getTimestamp("last_login_at").toLocalDateTime() : null;
		var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();

		return new Customer(customerId, customerName, email, lastLoginAt, createdAt);
	};

	public CustomerJdbcRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Customer insert(Customer customer) {
		try {
			jdbcTemplate.update(
				"INSERT INTO customers(customer_id, name, email, created_at) VALUES (UUID_TO_BIN(?), ?, ?, ?)",
				customer.getCustomerId().toString().getBytes(),
				customer.getName(),
				customer.getEmail(),
				Timestamp.valueOf(customer.getCreatedAt()));
		} catch (DuplicateKeyException e) {
			logger.info("Customer {} insert fail", customer);

			throw new UpdateFailException(e);
		} catch (DataAccessException e) {
			logger.info("Customer {} insert fail", customer);

			throw new UpdateFailException(e);
		}

		return customer;
	}

	@Override
	public Customer update(Customer customer) {
		var update = jdbcTemplate.update(
			"UPDATE customers SET name = ?, email = ?, last_login_at = ? WHERE customer_id = UUID_TO_BIN(?)",
			customer.getName(),
			customer.getEmail(),
			customer.getLastLoginAt() != null ? Timestamp.valueOf(customer.getLastLoginAt()) : null,
			customer.getCustomerId().toString().getBytes()
		);
		if (update != 1) {
			throw new UpdateFailException();
		}
		return customer;
	}

	@Override
	public List<Customer> findAllCustomers() {
		return jdbcTemplate.query("select * from customers", customerRowMapper);
	}

	@Override
	public Optional<Customer> findById(UUID customerId) {
		try {
			return Optional.ofNullable(
				jdbcTemplate.queryForObject("select * from customers WHERE customer_id = UUID_TO_BIN(?)",
					customerRowMapper,
					customerId.toString().getBytes()));
		} catch (DataAccessException e) {
			logger.error("find by ID {} 실패!", customerId, e);

			return Optional.empty();
		}
	}

	@Override
	public Optional<Customer> findByName(String name) {
		try {
			return Optional.ofNullable(jdbcTemplate.queryForObject("select * from customers WHERE name = ?",
				customerRowMapper,
				name));
		} catch (EmptyResultDataAccessException e) {
			logger.error("find by name {} 실패!", name, e);

			return Optional.empty();
		}
	}

	@Override
	public Optional<Customer> findByEmail(String email) {
		try {
			return Optional.ofNullable(jdbcTemplate.queryForObject("select * from customers WHERE email = ?",
				customerRowMapper,
				email));
		} catch (EmptyResultDataAccessException e) {
			logger.error("find by email {} 실패!", email, e);

			return Optional.empty();
		}
	}

	@Override
	public boolean deleteAll() {
		jdbcTemplate.update("DELETE FROM customers");

		return true;
	}

	@Override
	public boolean deleteById(UUID customerId) {
		try {
			jdbcTemplate.update("DELETE FROM customers WHERE customer_id = UUID_TO_BIN(?)",
				customerId.toString().getBytes());
		} catch (DataAccessException e) {
			logger.info("delete by id {} 실패", customerId, e);

			return false;
		}
		return true;

	}

	static UUID toUUID(byte[] bytes) {
		var byteBuffer = ByteBuffer.wrap(bytes);
		return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
	}
}

