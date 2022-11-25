package org.prgrms.springorder.repository.wallet;

import static org.prgrms.springorder.utils.JdbcUtil.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.prgrms.springorder.domain.ErrorMessage;
import org.prgrms.springorder.domain.customer.Customer;
import org.prgrms.springorder.domain.voucher.Voucher;
import org.prgrms.springorder.domain.wallet.Wallet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Profile("jdbc")
@Repository
public class WalletJdbcRepository {

	private final Logger log = LoggerFactory.getLogger(WalletJdbcRepository.class);
	private final NamedParameterJdbcTemplate jdbcTemplate;

	public WalletJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void allocate(Wallet wallet) {
		Map<String, Object> paramMap = toParamMap(wallet);
		jdbcTemplate.update(
			"INSERT INTO wallet(wallet_id,customer_id,voucher_id, wallet_created_at) Values(:walletId, :customerId, :voucherId, :walletCreatedAt)",
			paramMap);
	}

	public List<Voucher> findVoucherByCustomerId(UUID customerId) {
		try {
			return jdbcTemplate.query(
				"SELECT voucher.* from wallet INNER JOIN customer ON wallet.customer_id = customer.customer_id AND wallet.customer_id = :customerId INNER JOIN voucher ON wallet.voucher_id = voucher.voucher_id",
				Collections.singletonMap("customerId", customerId.toString()), voucherRowMapper);

		} catch (DataAccessException e) {
			log.error(ErrorMessage.DATA_ACCESS_MESSAGE.toString());
			return List.of();
		}

	}

	public void delete(Wallet wallet) {

		try {
			jdbcTemplate.update(
				"DELETE FROM wallet WHERE wallet.wallet_id = :walletId AND wallet.wallet_id = :walletId",
				Collections.singletonMap("walletId", wallet.getWalletId().toString()));
		} catch (DataAccessException e) {
			log.error(ErrorMessage.DATA_ACCESS_MESSAGE.toString());
		}

	}

	public List<Customer> findCustomerByVoucherId(UUID voucherId) {
		try {
			return jdbcTemplate.query(
				"SELECT * from wallet "
					+ "INNER JOIN voucher ON wallet.voucher_id = voucher.voucher_id "
					+ "INNER JOIN customer ON wallet.customer_id = customer.customer_id "
					+ "WHERE wallet.voucher_id = :voucherId",
				Collections.singletonMap("voucherId", voucherId.toString()), customerRowMapper);

		} catch (DataAccessException e) {
			log.error(ErrorMessage.DATA_ACCESS_MESSAGE.toString());
			return List.of();
		}

	}

	public void clear() {
		jdbcTemplate.update("DELETE FROM wallet", Collections.emptyMap());
	}

	private Map<String, Object> toIdParamMap(Wallet wallet) {
		return new HashMap<>() {{
			put("customerId", wallet.getCustomer().getCustomerId().toString());
			put("voucherId", wallet.getVoucher().getVoucherId().toString());
		}};
	}

}
