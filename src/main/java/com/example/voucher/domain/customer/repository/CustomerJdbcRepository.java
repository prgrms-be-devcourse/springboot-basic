package com.example.voucher.domain.customer.repository;

import com.example.voucher.domain.customer.Customer;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import static com.example.voucher.exception.ErrorMessage.INVALID_INPUT;

@Repository
public class CustomerJdbcRepository implements CustomerRepository {

	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private final SimpleJdbcInsert insertAction;

	public CustomerJdbcRepository(DataSource dataSource, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		insertAction = new SimpleJdbcInsert(dataSource)
				.withTableName("customers")
				.usingGeneratedKeyColumns("customer_id");
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	@Override
	public Customer save(Customer customer) {
		if (customer == null) {
			throw new IllegalArgumentException(INVALID_INPUT.getMessage());
		}
		Long customerId = insertAction.executeAndReturnKey(toCustomerParamMap(customer)).longValue();
		return new Customer(customerId, customer.getName(), customer.getCreatedAt(), customer.getUpdatedAt());
	}

	@Override
	public List<Customer> findAll() {
		return namedParameterJdbcTemplate.query(
				"SELECT * FROM customers", customerRowMapper);
	}

	@Override
	public int deleteById(Long customerId) {
		if (customerId == null) {
			throw new IllegalArgumentException(INVALID_INPUT.getMessage());
		}
		return namedParameterJdbcTemplate.update("DELETE FROM customers WHERE customer_id = :customerId", Collections.singletonMap("customerId", customerId));
	}

	private Map<String, Object> toCustomerParamMap(Customer customer) {
		var paramMap = new HashMap<String, Object>();
		paramMap.put("name", customer.getName());
		paramMap.put("created_at", customer.getCreatedAt());
		paramMap.put("updated_at", customer.getUpdatedAt());
		return paramMap;
	}

	private static final RowMapper<Customer> customerRowMapper = (resultSet, i) -> {
		Long customerId = resultSet.getLong("customer_id");
		String name = resultSet.getString("name");
		LocalDateTime createdAt = toLocalDateTime(resultSet.getTimestamp("created_at"));
		LocalDateTime updatedAt = toLocalDateTime(resultSet.getTimestamp("created_at"));
		return new Customer(customerId, name, createdAt, updatedAt);
	};

	private static LocalDateTime toLocalDateTime(Timestamp timestamp) {
		return timestamp != null ? timestamp.toLocalDateTime() : null;
	}

}
