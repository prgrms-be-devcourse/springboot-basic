package org.prgrms.kdt.model.repository.jdbc;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.prgrms.kdt.common.codes.ErrorCode;
import org.prgrms.kdt.common.exception.CommonRuntimeException;
import org.prgrms.kdt.model.entity.CustomerEntity;
import org.prgrms.kdt.model.entity.VoucherEntity;
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
			customer.customerId().toString(),
			customer.name(),
			customer.email(),
			Timestamp.valueOf(customer.createdAt())
		);

		if (update != 1) {
			throw new RuntimeException("Nothing was created");
		}
		return customer;
	}

	@Override
	public CustomerEntity update(CustomerEntity customerEntity) {
		try {
			CustomerEntity targetCustomer = findById(customerEntity.customerId());
			jdbcTemplate.update("UPDATE customers SET name = ?, email = ? WHERE customer_id = ?",
				targetCustomer.name(),
				targetCustomer.email(),
				targetCustomer.customerId()
			);
			return targetCustomer;
		} catch (RuntimeException e) {
			throw new CommonRuntimeException(ErrorCode.CUSTOMER_UPDATE_FAIL);
		}
	}

	@Override
	public List<CustomerEntity> findAll() {
		return jdbcTemplate.query("select * from customers", customerRowMapper);
	}

	@Override
	public CustomerEntity findById(Long customerId) {
		try {
			return jdbcTemplate.queryForObject(
				"select * from customers WHERE customer_id = ?",
				customerRowMapper,
				customerId.toString()
			);
		} catch (EmptyResultDataAccessException e) {
			throw new CommonRuntimeException(ErrorCode.VOUCHER_ID_NOT_FOUND);
		}
	}
}
