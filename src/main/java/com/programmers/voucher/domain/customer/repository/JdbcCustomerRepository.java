package com.programmers.voucher.domain.customer.repository;

import static com.programmers.voucher.core.exception.ExceptionMessage.*;
import static com.programmers.voucher.core.util.JdbcTemplateUtil.*;
import static com.programmers.voucher.domain.customer.repository.CustomerSQL.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.programmers.voucher.core.exception.DataUpdateException;
import com.programmers.voucher.core.exception.NotFoundException;
import com.programmers.voucher.domain.customer.model.Customer;
import com.programmers.voucher.domain.customer.model.CustomerType;

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
		int save = jdbcTemplate.update(INSERT.getSql(), toCustomerParamMap(customer));

		if (save != 1) {
			log.error(DATA_UPDATE_FAIL.getMessage());
			throw new DataUpdateException();
		}
		return customer;
	}

	@Override
	public Customer findById(UUID customerId) {
		return Optional.ofNullable(
				jdbcTemplate.queryForObject(SELECT_BY_ID.getSql(), toCustomerIdMap(customerId), customerRowMapper))
			.orElseThrow(() -> {
				log.error(CUSTOMER_NOT_FOUND.getMessage());
				throw new NotFoundException(CUSTOMER_NOT_FOUND.getMessage());
			});
	}

	@Override
	public Customer update(Customer updateCustomer) {
		int update = jdbcTemplate.update(UPDATE.getSql(), toCustomerParamMap(updateCustomer));

		if (update != 1) {
			log.error(DATA_UPDATE_FAIL.getMessage());
			throw new NotFoundException(CUSTOMER_NOT_FOUND.getMessage());
		}
		return updateCustomer;
	}

	@Override
	public void deleteById(UUID customerId) {
		int delete = jdbcTemplate.update(DELETE_BY_ID.getSql(), toCustomerIdMap(customerId));

		if (delete != 1) {
			log.error(CUSTOMER_NOT_FOUND.getMessage());
			throw new NotFoundException(CUSTOMER_NOT_FOUND.getMessage());
		}
	}

	@Override
	public List<Customer> findAll() {
		return jdbcTemplate.query(SELECT_ALL.getSql(), customerRowMapper);
	}

	@Override
	public List<Customer> findAllBlacklist() {
		return jdbcTemplate.query(SELECT_ALL_BLACKLIST.getSql(), toBlacklistTypeMap(), customerRowMapper)
			.stream()
			.filter(customer -> customer.getCustomerType().equals(CustomerType.BLACKLIST))
			.collect(Collectors.toList());
	}

	@Override
	public void clear() {
		jdbcTemplate.update(DELETE_ALL.getSql(), Collections.emptyMap());
	}
}
