package com.prgms.voucher.voucherproject.repository.customer;

import builder.Delete;
import builder.Insert;
import builder.Select;
import builder.Where;
import com.prgms.voucher.voucherproject.domain.customer.Customer;
import com.prgms.voucher.voucherproject.domain.customer.CustomerResponse;
import com.prgms.voucher.voucherproject.exception.NotFoundCustomerException;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static builder.Operator.Type.*;
import static com.prgms.voucher.voucherproject.util.JdbcUtils.toUUID;

@Component
public class JdbcCustomerRepository implements CustomerRepository {
	public static final int SUCCESS_QUERY = 1;
	private final JdbcTemplate jdbcTemplate;

	public JdbcCustomerRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void save(Customer customer) {
		Map<String, Object> values = new HashMap<>();
		values.put("customer_id", customer.getCustomerId());
		values.put("email", customer.getEmail());
		values.put("name", customer.getName());
		values.put("created_at", Timestamp.valueOf(customer.getCreatedAt()));

		Insert insert = Insert.into(Customer.class)
				.values(values)
				.build();

		int save = jdbcTemplate.update(insert.getQuery());

		if (save != SUCCESS_QUERY) {
			throw new IllegalArgumentException("고객 저장에 실패하였습니다.");
		}
	}

	@Override
	public List<Customer> findAll() {
		Select selectQueryAll = Select.builder()
				.select(CustomerResponse.class)
				.from(Customer.class)
				.build();

		List<Customer> customers = jdbcTemplate.query(selectQueryAll.getQuery(), customerRowMapper);
		return customers;
	}

	@Override
	public Optional<Customer> findByEmail(String email) {
		Select selectQuery = Select.builder()
				.select(CustomerResponse.class)
				.from(Customer.class)
				.where(
						Where.builder("email", EQ, email)
								.build()
				)
				.build();

		return jdbcTemplate.query(selectQuery.getQuery(), customerRowMapper)
			.stream()
			.findFirst();
	}

	@Override
	public void deleteByEmail(String email) {
		Delete delete = Delete.from(Customer.class)
				.where(
						Where.builder("email", EQ, email)
								.build()
				)
				.build();

		int deleteResult = jdbcTemplate.update(delete.getQuery());

		if (deleteResult != SUCCESS_QUERY) {
			throw new NotFoundCustomerException("삭제할 고객이 존재하지 않습니다.");
		}
	}

	private static final RowMapper<Customer> customerRowMapper = (resultSet, i) -> {
		UUID customerId = toUUID(resultSet.getBytes("customer_id"));
		String email = resultSet.getString("email");
		String name = resultSet.getString("name");
		LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();

		return new Customer(customerId, email, name, createdAt);
	};

}
