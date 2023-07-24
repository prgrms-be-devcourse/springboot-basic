package org.prgrms.kdt.model.repository.jdbc;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.prgrms.kdt.model.entity.CustomerEntity;
import org.prgrms.kdt.model.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier("JdbcCustomerRepository")
public class CustomerJdbcRepository implements CustomerRepository {

	private final DataSource dataSource;

	private final JdbcTemplate jdbcTemplate;

	public CustomerJdbcRepository(DataSource dataSource, JdbcTemplate jdbcTemplate) {
		this.dataSource = dataSource;
		this.jdbcTemplate = jdbcTemplate;
	}

	private static RowMapper<CustomerEntity> customerRowMapper = (resultSet, i) -> {
		String customerName = resultSet.getString("name");
		String email = resultSet.getString("email");
		Long customerId = resultSet.getLong("customer_id");
		LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
		return new CustomerEntity(customerId, customerName, email, createdAt);
	};

	@Override
	public CustomerEntity create(CustomerEntity customer) {
		int update = jdbcTemplate.update(
			"INSERT INTO customers(customer_id, name, email, created_at) VALUES (?, ?, ?, ?)",
			customer.getCustomerId().toString(),
			customer.getName(),
			customer.getEmail(),
			Timestamp.valueOf(customer.getCreatedAt())
		);

		if (update != 1) {
			throw new RuntimeException("Nothing was created");
		}
		return customer;
	}

	@Override
	public CustomerEntity update(CustomerEntity updatedCustomer) {
		int update = jdbcTemplate.update("UPDATE customers SET name = ?, email = ? WHERE customer_id = ?",
			updatedCustomer.getName(),
			updatedCustomer.getEmail(),
			updatedCustomer.getCustomerId()
		);
		if (update != 1) {
			throw new RuntimeException("Nothing was updated");
		}
		return updatedCustomer;
	}

	@Override
	public List<CustomerEntity> findAll() {
		return jdbcTemplate.query("select * from customers", customerRowMapper);
	}

	@Override
	public Optional<CustomerEntity> findById(Long customerId) {
		try {
			return Optional.ofNullable(jdbcTemplate.queryForObject(
				"select * from customers WHERE customer_id = ?",
				customerRowMapper,
				customerId.toString())
			);
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}
}
