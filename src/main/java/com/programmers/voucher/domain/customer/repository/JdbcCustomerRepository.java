package com.programmers.voucher.domain.customer.repository;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.programmers.voucher.domain.customer.model.Customer;
import com.programmers.voucher.domain.customer.model.CustomerType;
import com.programmers.voucher.exception.DataUpdateException;
import com.programmers.voucher.exception.ExceptionMessage;
import com.programmers.voucher.exception.NotFoundException;

@Repository
@Profile({"jdbc", "test"})
public class JdbcCustomerRepository implements CustomerRepository {

	private static final Logger log = LoggerFactory.getLogger(JdbcCustomerRepository.class);
	private final NamedParameterJdbcTemplate jdbcTemplate;

	public JdbcCustomerRepository(DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public Customer save(Customer customer) {
		int save = jdbcTemplate.update(
			"INSERT INTO customers(customer_id, customer_type, created_at, last_modified_at) VALUES (:customerId, :customerType, :createdAt, :lastModifiedAt)",
			toParamMap(customer));

		if (save != 1) {
			log.error(ExceptionMessage.DATA_UPDATE_FAIL.getMessage());
			throw new DataUpdateException();
		}
		return customer;
	}

	@Override
	public Customer findById(UUID customerId) {
		return Optional.ofNullable(
				jdbcTemplate.queryForObject("SELECT * FROM customers WHERE customer_id = :customerId", toIdMap(customerId),
					customerRowMapper))
			.orElseThrow(() -> {
				log.error(ExceptionMessage.CUSTOMER_NOT_FOUND.getMessage());
				throw new NotFoundException(ExceptionMessage.CUSTOMER_NOT_FOUND.getMessage());
			});
	}

	@Override
	public Customer update(Customer updateCustomer) {
		int update = jdbcTemplate.update(
			"UPDATE customers SET customer_type = :customerType, last_modified_at = :lastModifiedAt WHERE customer_id = :customerId",
			toParamMap(updateCustomer));

		if (update != 1) {
			log.error(ExceptionMessage.DATA_UPDATE_FAIL.getMessage());
			throw new NotFoundException(ExceptionMessage.CUSTOMER_NOT_FOUND.getMessage());
		}
		return updateCustomer;
	}

	@Override
	public void delete(UUID customerId) {
		int delete = jdbcTemplate.update("DELETE FROM customers WHERE customer_id = :customerId",
			toIdMap(customerId));

		if (delete != 1) {
			log.error(ExceptionMessage.CUSTOMER_NOT_FOUND.getMessage());
			throw new NotFoundException(ExceptionMessage.CUSTOMER_NOT_FOUND.getMessage());
		}
	}

	@Override
	public List<Customer> findAll() {
		return jdbcTemplate.query("SELECT * FROM customers", customerRowMapper);
	}

	@Override
	public List<Customer> findAllBlacklist() {
		return jdbcTemplate.query("SELECT * FROM customers WHERE customer_type = :customerType", customerRowMapper)
			.stream()
			.filter(customer -> customer.getCustomerType().equals(CustomerType.BLACKLIST))
			.collect(Collectors.toList());
	}

	@Override
	public void clear() {
		jdbcTemplate.update("DELETE FROM customers", Collections.emptyMap());
	}

	private Map<String, Object> toParamMap(Customer customer) {
		return Map.of(
			"customerId", customer.getCustomerId().toString().getBytes(StandardCharsets.UTF_8),
			"customerType", customer.getCustomerType().name(),
			"createdAt", customer.getCreatedAt(),
			"lastModifiedAt", customer.getLastModifiedAt()
		);
	}

	private Map<String, Object> toIdMap(UUID customerId) {
		return Collections.singletonMap("customerId", customerId.toString().getBytes(StandardCharsets.UTF_8));
	}

	private RowMapper<Customer> customerRowMapper = (ResultSet rs, int rowNum) -> {
		UUID customerId = toUUID(rs.getBytes("customer_id"));
		LocalDateTime createdAt = rs.getObject("created_at", LocalDateTime.class);
		CustomerType customerType = CustomerType.getCustomerType(rs.getString("customer_type"));
		LocalDateTime lastModifiedAt = rs.getObject("last_modified_at", LocalDateTime.class);
		return new Customer(customerId, createdAt, customerType, lastModifiedAt);
	};

	private UUID toUUID(byte[] bytes) {
		ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
		return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
	}
}
