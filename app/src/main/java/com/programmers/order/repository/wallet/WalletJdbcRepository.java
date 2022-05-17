package com.programmers.order.repository.wallet;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.programmers.order.domain.Customer;
import com.programmers.order.domain.Wallet;
import com.programmers.order.domain.Voucher;
import com.programmers.order.dto.VoucherDto;
import com.programmers.order.exception.JdbcException;
import com.programmers.order.factory.VoucherManagerFactory;
import com.programmers.order.manager.VoucherManager;
import com.programmers.order.message.ErrorMessage;
import com.programmers.order.message.LogMessage;
import com.programmers.order.type.VoucherType;
import com.programmers.order.utils.TranslatorUtils;

@Profile("jdbc")
@Repository
public class WalletJdbcRepository implements WalletRepository {
	private static final Logger log = LoggerFactory.getLogger(WalletJdbcRepository.class);

	private final DataSource dataSource;
	private final JdbcTemplate jdbcTemplate;
	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	private final VoucherManagerFactory voucherManagerFactory;

	public WalletJdbcRepository(DataSource dataSource, JdbcTemplate jdbcTemplate,
			NamedParameterJdbcTemplate namedParameterJdbcTemplate, VoucherManagerFactory voucherManagerFactory) {
		this.dataSource = dataSource;
		this.jdbcTemplate = jdbcTemplate;
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
		this.voucherManagerFactory = voucherManagerFactory;
	}

	@Override
	public Wallet insert(Wallet wallet) {
		int update = namedParameterJdbcTemplate.update(
				"INSERT INTO wallets(wallet_id,customer_id,voucher_id,created_at) values (UUID_TO_BIN(:walletId) ,UUID_TO_BIN(:customerId), UUID_TO_BIN(:voucherId), :createdAt)",
				toParameters(wallet));

		if (update != 1) {
			log.error(LogMessage.ErrorLogMessage.getPrefix(), LogMessage.ErrorLogMessage.NOT_EXECUTE_QUERY);
			throw new JdbcException.NotExecuteQuery(ErrorMessage.INTERNAL_PROGRAM_ERROR);
		}

		return wallet;
	}

	@Override
	public Optional<Wallet> findByVoucherId(UUID voucherId) {
		try {
			return Optional.ofNullable(
					namedParameterJdbcTemplate.queryForObject(
							"select * from wallets where voucher_id = UUID_TO_BIN(:voucherId)",
							Collections.singletonMap("voucherId", voucherId.toString().getBytes()),
							WALLETS_ROW_MAPPER)
			);
		} catch (EmptyResultDataAccessException e) {
			log.error(LogMessage.ErrorLogMessage.getPrefix(), LogMessage.ErrorLogMessage.NOT_FOUND_RESOURCE);
		}

		return Optional.empty();
	}

	@Override
	public Optional<Voucher> findVoucherByVoucherId(UUID voucherId) {
		try {
			return Optional.ofNullable(
					namedParameterJdbcTemplate
							.queryForObject("select * from vouchers where voucher_id = UUID_TO_BIN(:voucher_id)",
									Collections.singletonMap("voucher_id", voucherId.toString().getBytes()),
									(rs, row) -> this.getVoucherMapper(rs)));
		} catch (EmptyResultDataAccessException e) {
			log.error(LogMessage.ErrorLogMessage.getPrefix(), LogMessage.ErrorLogMessage.NOT_FOUND_RESOURCE);
			return Optional.empty();
		}
	}

	@Override
	public Optional<Wallet> findByCustomerId(UUID customerId) {
		try {
			return Optional.ofNullable(
					namedParameterJdbcTemplate.queryForObject(
							"select * from wallets where customer_id = UUID_TO_BIN(:customerId)",
							Collections.singletonMap("customerId", customerId.toString().getBytes()),
							WALLETS_ROW_MAPPER)
			);
		} catch (EmptyResultDataAccessException e) {
			log.error(LogMessage.ErrorLogMessage.getPrefix(), LogMessage.ErrorLogMessage.NOT_FOUND_RESOURCE);
		}

		return Optional.empty();
	}

	@Override
	public boolean isDuplicatePublish(UUID customerId, UUID voucherId) {
		try {
			return Optional.ofNullable(
					namedParameterJdbcTemplate.queryForObject(
							"SELECT id FROM wallets WHERE customer_id = UUID_TO_BIN(:customerId) and voucher_id = UUID_TO_BIN(:voucherId);",
							Map.ofEntries(
									Map.entry("voucherId", voucherId.toString().getBytes()),
									Map.entry("customerId", customerId.toString().getBytes())
							), (rs, rowNom) -> TranslatorUtils.toUUID(rs.getBytes("id")))
			).isPresent();
		} catch (EmptyResultDataAccessException e) {
			log.info(LogMessage.InfoLogMessage.getPrefix(), LogMessage.InfoLogMessage.POSSIBLE_REGISTER);
			return false;
		}

	}

	@Override
	public List<Voucher> findVouchersByCustomerId(UUID customerId) {
		return namedParameterJdbcTemplate.query(
				"select v.* from wallets cv join vouchers v on v.voucher_id = cv.voucher_id where cv.customer_id = uuid_to_bin(:customerId)",
				Collections.singletonMap("customerId", customerId.toString().getBytes()),
				(rs, rowNum) -> getVoucherMapper(rs));
	}

	@Override
	public List<Customer> joinCustomers(UUID voucherId) {
		return namedParameterJdbcTemplate.query(
				"select c.* from wallets cv join customers c on c.customer_id = cv.customer_id where cv.voucher_id = uuid_to_bin(:voucherId)",
				Collections.singletonMap("voucherId", voucherId.toString().getBytes()),
				CUSTOMER_ROW_MAPPER);
	}

	@Override
	public void deleteByCustomerIdAndVoucherId(UUID customerId, UUID voucherId) {
		namedParameterJdbcTemplate.update(
				"DELETE FROM customers where customer_id = UUID_TO_BIN(:customerId) and voucher_id= UUID_TO_BIN(:voucherId)",
				Map.ofEntries(
						Map.entry("voucherId", voucherId.toString().getBytes()),
						Map.entry("customerId", customerId.toString().getBytes())
				));

	}

	private Map<String, Object> toParameters(Wallet wallet) {
		HashMap<String, Object> params = new HashMap<>();
		params.put("walletId", wallet.getWalletId().toString().getBytes());
		params.put("customerId", wallet.getCustomerId().toString().getBytes());
		params.put("voucherId", wallet.getVoucherId().toString().getBytes());
		params.put("createdAt", wallet.getCreatedAt().toString().getBytes());

		return params;
	}

	private static final RowMapper<Wallet> WALLETS_ROW_MAPPER = (rs, rowNum) -> {
		UUID walletId = TranslatorUtils.toUUID(rs.getBytes("id"));
		UUID voucherId = TranslatorUtils.toUUID(rs.getBytes("voucher_id"));
		UUID customerId = TranslatorUtils.toUUID(rs.getBytes("customer_id"));
		LocalDateTime createdAt = rs.getTimestamp(("created_at")).toLocalDateTime();

		return new Wallet(walletId, customerId, voucherId, createdAt);
	};
	private static final RowMapper<Customer> CUSTOMER_ROW_MAPPER = (rs, rowNum) -> {
		UUID id = TranslatorUtils.toUUID(rs.getBytes("customer_id"));
		String name = rs.getString("name");
		String email = rs.getString("email");
		LocalDateTime lastLoginAt = TranslatorUtils.toLocalDateTIme(rs.getTimestamp("last_login_at"));
		LocalDateTime createdAt = rs.getTimestamp(("created_at")).toLocalDateTime();

		return new Customer(id, name, email, lastLoginAt, createdAt);
	};

	private Voucher getVoucherMapper(ResultSet rs) throws SQLException {
		UUID id = TranslatorUtils.toUUID(rs.getBytes("voucher_id"));
		String voucherType = rs.getString("voucher_type");
		long discountValue = rs.getLong("discount_value");
		LocalDateTime createdAt = rs.getTimestamp(("created_at")).toLocalDateTime();

		VoucherType type = VoucherType.matchTheType(voucherType);
		VoucherManager voucherManager = voucherManagerFactory.getVoucherManager(type);
		VoucherDto.Resolver resolver = new VoucherDto.Resolver(id, discountValue, createdAt);
		return voucherManager.resolve(resolver);
	}

}
