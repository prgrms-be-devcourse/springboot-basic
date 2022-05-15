package com.prgrms.vouchermanagement.customer.repository;

import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
		String customerName = resultSet.getString("name");
		String email = resultSet.getString("email");
		UUID customerId = toUUID(resultSet.getBytes("customer_id"));
		LocalDateTime lastLoginAt = resultSet.getTimestamp("last_login_at") != null ?
			resultSet.getTimestamp("last_login_at").toLocalDateTime() : null;
		LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();

		return new Customer(customerId, customerName, email, lastLoginAt, createdAt);
	};

	public CustomerJdbcRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Customer insert(Customer customer) {
		jdbcTemplate.update(
			"INSERT INTO customers(customer_id, name, email, created_at) VALUES (UUID_TO_BIN(?), ?, ?, ?)",
			customer.getCustomerId().toString().getBytes(),
			customer.getName(),
			customer.getEmail(),
			Timestamp.valueOf(customer.getCreatedAt()));

		return customer;
	}

	@Override
	public void update(Customer customer) {
		int update = jdbcTemplate.update(
			"UPDATE customers SET name = ?, email = ?, last_login_at = ? WHERE customer_id = UUID_TO_BIN(?)",
			customer.getName(),
			customer.getEmail(),
			customer.getLastLoginAt() != null ? Timestamp.valueOf(customer.getLastLoginAt()) : null,
			customer.getCustomerId().toString().getBytes()
		);
		if (update != 1) {
			throw new UpdateFailException();
		}
	}

	@Override
	public List<Customer> findAll() {
		return jdbcTemplate.query("select * from customers", customerRowMapper);
	}

	@Override
	public Optional<Customer> findById(UUID customerId) {
		try {
			return Optional.ofNullable(
				jdbcTemplate.queryForObject("select * from customers WHERE customer_id = UUID_TO_BIN(?)",
					customerRowMapper,
					customerId.toString().getBytes()));
		} catch (EmptyResultDataAccessException e) {
			logger.info("id {} 을 가진 Customer 가 존재하지 않습니다", customerId);

			return Optional.empty();
		}
	}

	@Override
	public List<Customer> findAllByName(String name) {
		return jdbcTemplate.query("select * from customers WHERE name = ?",
			customerRowMapper,
			name);
	}

	@Override
	public Optional<Customer> findByEmail(String email) {
		try {
			return Optional.ofNullable(jdbcTemplate.queryForObject("select * from customers WHERE email = ?",
				customerRowMapper,
				email));
		} catch (EmptyResultDataAccessException e) {
			logger.info("email {} 을 가진 Customer 가 존재하지 않습니다", email);

			return Optional.empty();
		}
	}

	@Override
	public long deleteAll() {
		return jdbcTemplate.update("DELETE FROM customers");
	}

	@Override
	public long deleteById(UUID customerId) {
		return jdbcTemplate.update("DELETE FROM customers WHERE customer_id = UUID_TO_BIN(?)",
			customerId.toString().getBytes());
	}

	static UUID toUUID(byte[] bytes) {
		var byteBuffer = ByteBuffer.wrap(bytes);
		return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
	}
}

