package com.programmers.voucher.domain.wallet.repository;

import static com.programmers.voucher.core.exception.ExceptionMessage.*;
import static com.programmers.voucher.core.util.JdbcTemplateUtil.*;
import static com.programmers.voucher.domain.wallet.repository.WalletSQL.*;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.programmers.voucher.core.exception.DataUpdateException;
import com.programmers.voucher.domain.customer.model.Customer;
import com.programmers.voucher.domain.voucher.model.Voucher;
import com.programmers.voucher.domain.wallet.model.Wallet;

@Repository
@Profile({"jdbc", "test"})
public class JdbcWalletRepository implements WalletRepository {

	private static final Logger log = LoggerFactory.getLogger(JdbcWalletRepository.class);
	private final NamedParameterJdbcTemplate jdbcTemplate;

	public JdbcWalletRepository(DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public Wallet save(Wallet wallet) {
		int save = jdbcTemplate.update(INSERT.getSql(), toWalletParamMap(wallet));

		if (save != 1) {
			log.error(DATA_UPDATE_FAIL.getMessage());
			throw new DataUpdateException();
		}
		return wallet;
	}

	@Override
	public List<Voucher> findVouchersByCustomerId(UUID customerId) {
		return jdbcTemplate.query(SELECT_VOUCHER_BY_CUSTOMER_ID.getSql(), toCustomerIdMap(customerId),
			voucherRowMapper);
	}

	@Override
	public List<Customer> findCustomersByVoucherId(UUID voucherId) {
		return jdbcTemplate.query(SELECT_CUSTOMER_BY_VOUCHER_ID.getSql(), toVoucherIdMap(voucherId), customerRowMapper);
	}

	@Override
	public void deleteByCustomerId(UUID customerId) {
		jdbcTemplate.update(DELETE_BY_CUSTOMER_ID.getSql(), toCustomerIdMap(customerId));
	}

	@Override
	public void deleteAll() {
		jdbcTemplate.update(DELETE_ALL.getSql(), Collections.emptyMap());
	}
}
