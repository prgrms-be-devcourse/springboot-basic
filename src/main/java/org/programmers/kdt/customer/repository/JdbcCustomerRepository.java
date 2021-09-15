package org.programmers.kdt.customer.repository;

import org.programmers.kdt.customer.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

import static org.programmers.kdt.utils.UuidUtils.toUUID;

@Repository
public class JdbcCustomerRepository implements CustomerRepository {
	// TODO : AOP 적용
	private static final Logger logger = LoggerFactory.getLogger(JdbcCustomerRepository.class);
	private final NamedParameterJdbcTemplate jdbcTemplate;
	private final String customerTable;
	private final String customerBlacklistTable;

	private static String SQL_INSERT_CUSTOMER = "INSERT INTO %s(customer_id, name, email, last_login_at, created_at) VALUES (UUID_TO_BIN(:customer_id), :name, :email, :last_login_at, :created_at)";
	private static String SQL_DELETE_CUSTOMER_BY_ID = "DELETE FROM %s WHERE customer_id = UUID_TO_BIN(:customer_id)";
	private static String SQL_DELETE_ALL = "DELETE FROM %s";
	private static String SQL_SELECT_CUSTOMER_BY_ID = "SELECT * FROM %s WHERE customer_id = UUID_TO_BIN(:customer_id)";
	private static String SQL_SELECT_CUSTOMER_BY_NAME = "SELECT * FROM %s WHERE name = :name";
	private static String SQL_SELECT_CUSTOMER_BY_EMAIL = "SELECT * FROM %s WHERE email = :email";
	private static String SQL_SELECT_ALL = "SELECT * FROM %s";

	public JdbcCustomerRepository(DataSource dataSource, String customerTable, String customerBlacklistTable) {
		jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		this.customerTable = customerTable;
		this.customerBlacklistTable = customerBlacklistTable;
	}

	@Override
	public Customer insert(Customer customer) {
		int numberOfRowSAffected = jdbcTemplate.update(SQL_INSERT_CUSTOMER.formatted(customerTable), toParamMap(customer)
		);

		// 0: The customer already exists so that no row has been affected.
		// 1: Successfully created so that one row has been affected(created).
		if (numberOfRowSAffected != 1) {
			logger.info("Attempt to create a duplicate customer has been detected. Customer Information -> {}", customer);
			throw new RuntimeException("Try to create a duplicate customer!");
		}

		return customer;
	}

	@Override
	public void deleteCustomer(UUID customerId) {
		jdbcTemplate.update(SQL_DELETE_CUSTOMER_BY_ID.formatted(customerTable), Collections.singletonMap("customer_id", customerId.toString().getBytes()));
		jdbcTemplate.update(SQL_DELETE_CUSTOMER_BY_ID.formatted(customerBlacklistTable), Collections.singletonMap("customer_id", customerId.toString().getBytes()));
	}

	@Override
	public void deleteAll() {
		jdbcTemplate.update(SQL_DELETE_ALL.formatted(customerTable), Collections.emptyMap());
		jdbcTemplate.update(SQL_DELETE_ALL.formatted(customerBlacklistTable), Collections.emptyMap());
	}

	@Override
	public Optional<Customer> findById(UUID customerId) {
		try {
			return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_SELECT_CUSTOMER_BY_ID.formatted(customerTable),
					Collections.singletonMap("customer_id", customerId.toString().getBytes()),
					rowMapper));
		} catch (EmptyResultDataAccessException e) {
			logger.error("No Such Customer(ID: {}) Exists. -> {}", customerId, e.getMessage());
			return Optional.empty();
		}
	}

	@Override
	public List<Customer> findByName(String name) {
		return jdbcTemplate.query(SQL_SELECT_CUSTOMER_BY_NAME.formatted(customerTable),
					Collections.singletonMap("name", name),
					rowMapper);
	}

	@Override
	public Optional<Customer> findByEmail(String email) {
		try {
			return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_SELECT_CUSTOMER_BY_EMAIL.formatted(customerTable),
					Collections.singletonMap("email", email),
					rowMapper));
		} catch (EmptyResultDataAccessException e) {
			logger.error("No Such Customer(email: {0}) Exists. -> {}", email, e);
			return Optional.empty();
		}
	}

	@Override
	public List<Customer> findAll() {
		return jdbcTemplate.query(SQL_SELECT_ALL.formatted(customerTable), rowMapper);
	}

	@Override
	public Customer registerToBlacklist(Customer customer) {
		int update = jdbcTemplate.update(SQL_INSERT_CUSTOMER.formatted(customerBlacklistTable), toParamMap(customer));

		if (update != 1) {
			logger.error("[FAILED] Register a customer to blacklist : Already Exists");
		}

		return customer;
	}

	@Override
	public List<Customer> findAllBlacklistCustomer() {
		return jdbcTemplate.query(SQL_SELECT_ALL.formatted(customerBlacklistTable), rowMapper);
	}

	@Override
	public Optional<Customer> findCustomerOnBlacklistById(UUID customerId) {
		try {
			return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_SELECT_CUSTOMER_BY_ID.formatted(customerBlacklistTable),
					Collections.singletonMap("customer_id", customerId.toString().getBytes()),
					rowMapper));
		} catch (EmptyResultDataAccessException e) {
			logger.error("No Such Customer(ID: {0}) Exists on Blacklist. -> {}", customerId, e);
			return Optional.empty();
		}
	}

	private final RowMapper<Customer> rowMapper = (resultSet, rowNum) -> {
			UUID customerId = toUUID(resultSet.getBytes("customer_id"));
			String name = resultSet.getString("name");
			String email = resultSet.getString("email");
			LocalDateTime lastLoginAt = resultSet.getTimestamp("last_login_at") != null ?
					resultSet.getTimestamp("last_login_at").toLocalDateTime() : null;
			LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
			return new Customer.Builder(customerId, email, createdAt).name(name).lastLoginAt(lastLoginAt).build();
		};

	private Map<String, Object> toParamMap(Customer customer) {
		return Map.of(
				"customer_id", customer.getCustomerId().toString().getBytes(),
				"name", customer.getName(),
				"email", customer.getEmail(),
				"last_login_at", customer.getLastLoginAt() != null ? Timestamp.valueOf(customer.getLastLoginAt()) : null,
				"created_at", customer.getCreatedAt()
		);
	}
}
