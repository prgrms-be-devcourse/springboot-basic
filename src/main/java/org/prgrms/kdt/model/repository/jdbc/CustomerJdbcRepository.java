package org.prgrms.kdt.model.repository.jdbc;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import javax.sql.DataSource;

import org.prgrms.kdt.common.codes.ErrorCode;
import org.prgrms.kdt.common.exception.CommonRuntimeException;
import org.prgrms.kdt.model.entity.CustomerEntity;
import org.prgrms.kdt.model.repository.CustomerRepository;
import org.prgrms.kdt.model.repository.file.FileVoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
@Primary
@Qualifier("JdbcCustomerRepository")
public class CustomerJdbcRepository implements CustomerRepository {

	private final DataSource dataSource;

	private final JdbcTemplate jdbcTemplate;

	private static final Logger logger = LoggerFactory.getLogger(CustomerJdbcRepository.class);

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
	public CustomerEntity saveCustomer(CustomerEntity customer) {
		try{
			jdbcTemplate.update(
				"INSERT INTO customers(customer_id, name, email, created_at) VALUES (?, ?, ?, ?)",
				customer.customerId().toString(),
				customer.name(),
				customer.email(),
				Timestamp.valueOf(customer.createdAt())
			);
			return customer;
		}catch (Exception e) {
			logger.error("CustomerEntity id is {}",customer.customerId());
			logger.error(ErrorCode.CUSTOMER_CREATE_FAIL.getErrorMessage(), e);
			throw new CommonRuntimeException(ErrorCode.CUSTOMER_CREATE_FAIL);
		}
	}

	@Override
	public CustomerEntity updateCustomer(CustomerEntity customerEntity) {
		try {
			jdbcTemplate.update("UPDATE customers SET name = ?, email = ? WHERE customer_id = ?",
				customerEntity.name(),
				customerEntity.email(),
				customerEntity.customerId()
			);
			return customerEntity;
		} catch (RuntimeException e) {
			logger.error("CustomerEntity id is {}",customerEntity.customerId());
			logger.error(ErrorCode.CUSTOMER_UPDATE_FAIL.getErrorMessage(), e);
			throw new CommonRuntimeException(ErrorCode.CUSTOMER_UPDATE_FAIL);
		}
	}

	@Override
	public List<CustomerEntity> findAllCustomers() {
		return jdbcTemplate.query("select * from customers", customerRowMapper);
	}

	@Override
	public CustomerEntity findCustomerById(Long customerId) {
		try {
			return jdbcTemplate.queryForObject(
				"select * from customers WHERE customer_id = ?",
				customerRowMapper,
				customerId.toString()
			);
		} catch (EmptyResultDataAccessException e) {
			logger.error("CustomerEntity id is {}", customerId);
			logger.error(ErrorCode.CUSTOMER_ID_NOT_FOUND.getErrorMessage(), e);
			throw new CommonRuntimeException(ErrorCode.VOUCHER_ID_NOT_FOUND);
		}
	}
}
