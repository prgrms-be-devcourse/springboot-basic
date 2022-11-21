package com.programmers.voucher.domain.wallet.repository;

import static com.programmers.voucher.core.exception.ExceptionMessage.*;
import static com.programmers.voucher.core.util.JdbcTemplateUtil.*;

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
		int save = jdbcTemplate.update(
			"INSERT INTO wallets(voucher_id, customer_id, created_at) VALUES (:voucherId, :customerId, :createdAt)",
			toWalletParamMap(wallet));

		if (save != 1) {
			log.error(DATA_UPDATE_FAIL.getMessage());
			throw new DataUpdateException();
		}
		return wallet;
	}

	@Override
	public List<Voucher> findVouchersByCustomerId(UUID customerId) {
		return jdbcTemplate.query(
			"SELECT v.* from wallets w INNER JOIN customers c ON w.customer_id = c.customer_id AND w.customer_id = :customerId INNER JOIN vouchers v ON w.voucher_id = v.voucher_id",
			toCustomerIdMap(customerId), voucherRowMapper);
	}

	@Override
	public List<Customer> findCustomersByVoucherId(UUID voucherId) {
		return jdbcTemplate.query(
			"SELECT c.* from wallets w INNER JOIN vouchers v ON w.voucher_id = v.voucher_id AND w.voucher_id = :voucherId INNER JOIN customers c ON w.customer_id = c.customer_id",
			toVoucherIdMap(voucherId), customerRowMapper);
	}

	@Override
	public void deleteByCustomerId(UUID customerId) {
		jdbcTemplate.update("DELETE FROM wallets WHERE customer_id = :customerId",
			toCustomerIdMap(customerId));
	}
}
