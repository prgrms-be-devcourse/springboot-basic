package com.programmers.springbasic.repository.customer;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.programmers.springbasic.entity.customer.Customer;

@Repository
@Profile("test")
public class JdbcCustomerRepository implements CustomerRepository {

	private final JdbcTemplate jdbcTemplate;

	private final RowMapper<Customer> customerRowMapper = (resultSet, rowNum) -> new Customer(
		UUID.fromString(resultSet.getString("customer_id")),
		resultSet.getString("name"),
		resultSet.getString("email"),
		resultSet.getTimestamp("created_at").toLocalDateTime()
	); // todo : nullable value 체크

	public JdbcCustomerRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Customer insert(Customer customer) {
		String sql = "INSERT INTO customer (customer_id, name, email, created_at) VALUES (?, ?, ?, ?)";
		jdbcTemplate.update(sql, customer.getId(), customer.getName(), customer.getEmail(), customer.getCreatedAt());
		return customer;
	}

	@Override
	public Customer update(Customer customer) {
		String sql = "UPDATE customer SET name = ? WHERE customer_id = ?";

		jdbcTemplate.update(sql,
			customer.getName(),
			customer.getId().toString()
		);
		return customer;
	}

	@Override
	public List<Customer> findAll() {
		String sql = "SELECT * FROM customer";
		return jdbcTemplate.query(sql, customerRowMapper);
	}

	@Override
	public Optional<Customer> findById(UUID id) {
		String sql = "SELECT * FROM customer WHERE customer_id = ?";
		List<Customer> customers = jdbcTemplate.query(sql, customerRowMapper, id);
		return customers.isEmpty() ? Optional.empty() : Optional.of(customers.get(0));
	}

	@Override
	public void deleteById(UUID id) {
		String sql = "DELETE FROM customer WHERE customer_id = ?";
		jdbcTemplate.update(sql, id);
	}
}
