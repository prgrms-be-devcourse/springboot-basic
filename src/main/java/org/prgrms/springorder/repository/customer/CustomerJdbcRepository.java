package org.prgrms.springorder.repository.customer;

import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.prgrms.springorder.domain.customer.Customer;
import org.prgrms.springorder.domain.customer.CustomerType;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class CustomerJdbcRepository implements CustomerRepository {


	private final NamedParameterJdbcTemplate jdbcTemplate;

	public CustomerJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	private static UUID toUUID(byte[] bytes) {
		ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
		return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
	}

	private final RowMapper<Customer> customerRowMapper = (resultSet, i) -> {
		var customerId = toUUID(resultSet.getBytes("customer_id"));
		var customerName = resultSet.getString("customer_name");
		var email = resultSet.getString("email");
		var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
		var customerType = CustomerType.getCustomerTypeByRating(resultSet.getString("customer_type"));
		return new Customer(customerId, customerName, email, createdAt, customerType);
	};

	private Map<String, Object> toParamMap(Customer customer) {
		return new HashMap<>() {{
			put("customerId", customer.getCustomerId().toString().getBytes());
			put("name", customer.getName());
			put("email", customer.getEmail());
			put("createdAt", Timestamp.valueOf(customer.getCreatedAt()));
			put("customerType", customer.getCustomerType().getRating());
		}};
	}

	@Override
	public void save(Customer customer) {
		Map<String, Object> paramMap = toParamMap(customer);
		jdbcTemplate.update("INSERT INTO customer(customer_id, customer_name, email, created_at,customer_type) Values(UUID_TO_BIN(:customerId),:name,:email,:createdAt,:customerType)" ,paramMap);
	}

	@Override
	public void update(Customer customer) {
		Map<String, Object> paramMap = toParamMap(customer);
		jdbcTemplate.update("UPDATE customer SET customer_name = :name, email = :email, customer_type = :customerType WHERE customer_id = UUID_TO_BIN(:customerId)",paramMap);
	}

	@Override
	public List<Customer> findAll() {
		return jdbcTemplate.query("SELECT * FROM customer",customerRowMapper);
	}


	@Override
	public Optional<Customer> findById(UUID customerId) {
		return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM customer WHERE customer_id = UUID_TO_BIN(:customerId)",
			Collections.singletonMap("customerId", customerId.toString()), customerRowMapper));
	}




}
