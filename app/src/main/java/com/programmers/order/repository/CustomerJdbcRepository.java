package com.programmers.order.repository;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.programmers.order.domain.Customer;
import com.programmers.order.exception.JdbcException;

@Repository
public class CustomerJdbcRepository implements CustomerRepository {

	private final NamedParameterJdbcTemplate jdbcTemplate;
	private final int SUCCESS_EXECUTE_QUERY = 1;

	public CustomerJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Customer insert(Customer customer) {
		int insert = jdbcTemplate.update(
				"insert into customers(customer_id, email, name, created_at, updated_at) "
						+ "values(UUID_TO_BIN(:customerId),:email, :name,:createdAt, :updatedAt)",
				toParams(customer)
		);

		if (insert != SUCCESS_EXECUTE_QUERY) {
			throw new JdbcException.NotExecuteQueryException("customer insert query 가 정상적으로 실행되지 않았습니다..");
		}

		return customer;
	}

	@Override
	public void deleteAll() {
		jdbcTemplate.update(
				"delete from customers "
				, Collections.emptyMap()
		);
	}

	private Map<String, Object> toParams(Customer customer) {
		Map<String, Object> params = new HashMap<>();

		params.put("customerId", customer.getCustomerId().toString().getBytes());
		params.put("email", customer.getEmail());
		params.put("name", customer.getName());
		params.put("createdAt", customer.getCreatedAt());
		params.put("updatedAt", customer.getUpdatedAt());

		return params;
	}
}
