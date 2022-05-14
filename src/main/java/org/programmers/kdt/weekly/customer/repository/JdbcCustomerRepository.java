package org.programmers.kdt.weekly.customer.repository;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.programmers.kdt.weekly.customer.model.Customer;
import org.programmers.kdt.weekly.customer.model.CustomerType;
import org.programmers.kdt.weekly.utils.UtilFunction;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class JdbcCustomerRepository implements CustomerRepository {

	private static final int SUCCESS = 1;
	private final NamedParameterJdbcTemplate jdbcTemplate;

	public JdbcCustomerRepository(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	private final RowMapper<Customer> customerRowMapper = (rs, rowNum) -> {
		var customerId = UtilFunction.toUUID(rs.getBytes("customer_id"));
		var customerEmail = rs.getString("email");
		var customerName = rs.getString("name");
		var customerType = CustomerType.valueOf(rs.getString("type"));

		return new Customer(customerId, customerEmail, customerName, customerType);
	};

	private Map<String, Object> toParamMap(Customer customer) {
		HashMap<String, Object> customerMap = new HashMap<>();

		customerMap.put("customerId", customer.getCustomerId().toString().getBytes());
		customerMap.put("email", customer.getEmail());
		customerMap.put("name", customer.getCustomerName());
		customerMap.put("type", customer.getCustomerType().name());

		return customerMap;
	}

	@Override
	public Customer insert(Customer customer) {
		String insertSql = "INSERT INTO customer(customer_id, name, email,type) " +
			"VALUES (UNHEX(REPLACE(:customerId, '-', '')), :name, :email, :type)";
		var update = this.jdbcTemplate.update(insertSql, toParamMap(customer));

		if (update != SUCCESS) {
			throw new RuntimeException("Failed to save customer");
		}

		return customer;
	}

	@Override
	public List<Customer> findAll() {
		String selectSql = "SELECT * FROM customer";

		return this.jdbcTemplate.query(selectSql, customerRowMapper);
	}

	@Override
	public Optional<Customer> findByEmail(String customerEmail) {
		String selectByEmailSql = "SELECT * FROM customer WHERE email = :customerEmail";

		var customer = this.jdbcTemplate.queryForObject(selectByEmailSql,
			Collections.singletonMap("customerEmail", customerEmail), customerRowMapper);

		return Optional.ofNullable(customer);

	}

	@Override
	public List<Customer> findByType(String customerType) {
		String selectByTypeSql = "SELECT * FROM customer WHERE type = :customerType";

		return this.jdbcTemplate.query(selectByTypeSql,
			Collections.singletonMap("customerType", customerType), customerRowMapper);
	}

	@Override
	public Optional<Customer> findById(UUID customerId) {
		String selectByIdSql = "SELECT * FROM customer WHERE customer_id = :customerId";
		var customer = this.jdbcTemplate.queryForObject(selectByIdSql,
			Collections.singletonMap("customerId", customerId), customerRowMapper);

		return Optional.ofNullable(customer);
	}

	@Override
	public Customer update(Customer customer) {
		String updateSql = "UPDATE customer SET type = :type WHERE customer_id = UNHEX(REPLACE(:customerId, '-', ''))";
		var update = this.jdbcTemplate.update(updateSql, toParamMap(customer));

		if (update != SUCCESS) {
			throw new RuntimeException("Failed to update customer");
		}

		return customer;
	}

	@Override
	public void deleteAll() {
		String deleteSql = "DELETE FROM customer";
		this.jdbcTemplate.update(deleteSql, Collections.emptyMap());
	}
}