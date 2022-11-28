package org.prgrms.springorder.repository.customer;

import static org.prgrms.springorder.utils.JdbcUtil.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.prgrms.springorder.domain.customer.Customer;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerJdbcRepository implements CustomerRepository {

	private final NamedParameterJdbcTemplate jdbcTemplate;

	public CustomerJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void save(Customer customer) {
		Map<String, Object> paramMap = toParamMap(customer);
		jdbcTemplate.update(
			"INSERT INTO customer(customer_id, customer_name, email, customer_created_at ,customer_type) Values(:customerId,:name,:email,:customerCreatedAt,:customerType)",
			paramMap);
	}

	@Override
	public void updateByObject(Customer customer) {
		Map<String, Object> paramMap = toParamMap(customer);
		jdbcTemplate.update(
			"UPDATE customer SET customer_name = :name, email = :email, customer_type = :customerType WHERE customer_id = :customerId",
			paramMap);
	}


	@Override
	public List<Customer> findAll() {
		return jdbcTemplate.query("SELECT * FROM customer", customerRowMapper);
	}

	@Override
	public Optional<Customer> findById(UUID customerId) {
		return Optional.ofNullable(
			jdbcTemplate.queryForObject("SELECT * FROM customer WHERE customer_id = :customerId",
				Collections.singletonMap("customerId", customerId.toString()), customerRowMapper));
	}

	public void clear() {
		jdbcTemplate.update("DELETE FROM customer", Collections.emptyMap());
	}

}
