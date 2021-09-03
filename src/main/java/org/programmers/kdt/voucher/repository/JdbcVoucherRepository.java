package org.programmers.kdt.voucher.repository;

import org.programmers.kdt.customer.Customer;
import org.programmers.kdt.voucher.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

import static org.programmers.kdt.utils.UuidUtils.toUUID;

@Repository
@Profile("local")
public class JdbcVoucherRepository implements VoucherRepository {
	// TODO : AOP 적용
	private static final Logger logger = LoggerFactory.getLogger(JdbcVoucherRepository.class);
	private final NamedParameterJdbcTemplate jdbcTemplate;

	public JdbcVoucherRepository(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Voucher insert(Voucher voucher) {
		int update = jdbcTemplate.update(
				"INSERT INTO vouchers(voucher_id, type, amount) VALUES (UUID_TO_BIN(:voucher_id), :type, :amount)",
				toParamMap(voucher)
		);

		if (update != 1) {
			logger.error("[FAILED] Insert a new voucher : Already Exists");
		}

		return voucher;
	}

	@Override
	public Optional<Voucher> findById(UUID voucherId) {
		try {
			return Optional.ofNullable(jdbcTemplate.queryForObject(
					"SELECT * FROM vouchers WHERE voucher_id = UUID_TO_BIN(:voucher_id)",
					Collections.singletonMap("voucher_id", voucherId.toString().getBytes()),
					voucherMapper)
			);
		} catch (EmptyResultDataAccessException e) {
			logger.error("No Such Voucher(ID: {}) Exists. -> {}", voucherId, e);
			return Optional.empty();
		}
	}

	@Override
	public void deleteVoucher(UUID voucherId) {
		jdbcTemplate.update("DELETE FROM vouchers WHERE voucher_id = UUID_TO_BIN(:voucher_id)",
				Collections.singletonMap("voucher_id", voucherId.toString().getBytes()));
	}

	@Override
	public List<Voucher> findAll() {
		return jdbcTemplate.query("SELECT * FROM vouchers", voucherMapper);
	}

	@Override
	public List<Voucher> findAllUnregisteredVouchers() {
		return jdbcTemplate.query("SELECT * FROM vouchers WHERE customer_id IS null", voucherMapper);
	}

	@Override
	public Voucher addOwner(Customer customer, Voucher voucher) {
		if (voucher.getStatus().equals(VoucherStatus.VALID)) {
			jdbcTemplate.update("UPDATE vouchers SET customer_id = UUID_TO_BIN(:customer_id) WHERE voucher_id = UUID_TO_BIN(:voucher_id)",
					voucherOwnerMap(customer, voucher.getVoucherId()));
			return voucher;
		}

		logger.error("Registering invalid voucher : {}", voucher.getVoucherId());
		throw new RuntimeException("Registering invalid voucher.");
	}

	@Override
	public void removeOwner(Customer customer, UUID voucherId) {
		jdbcTemplate.update("DELETE FROM vouchers WHERE customer_Id = UUID_TO_BIN(:customer_id) and voucher_id = UUID_TO_BIN(:voucher_id)",
				voucherOwnerMap(customer, voucherId));
	}

	@Override
	public Optional<UUID> findCustomerIdByVoucherId(UUID voucherId) {
		return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM vouchers WHERE voucher_id = UUID_TO_BIN(:voucher_id)",
				Collections.singletonMap("voucher_id", voucherId.toString().getBytes()),
				customerIdMapper));
	}

	@Override
	public List<Voucher> findVouchersByCustomerId(UUID customerId) {
		return jdbcTemplate.query("SELECT * FROM vouchers WHERE customer_id = UUID_TO_BIN(:customer_id)",
				Collections.singletonMap("customer_id", customerId.toString().getBytes()),
				voucherMapper);
	}

	private Map<String, Object> voucherOwnerMap(Customer customer, UUID voucherId) {
		return new HashMap<>() {
			{
				put("customer_id", customer.getCustomerId().toString().getBytes());
				put("voucher_id", voucherId.toString().getBytes());
			}
		};
	}

	private final RowMapper<UUID> customerIdMapper = (resultSet, rowNum) -> {
		byte[] customerId = resultSet.getBytes("customer_id");
		if (customerId == null) {
			return null;
		}
		return toUUID(customerId);
	};

	private final RowMapper<Voucher> voucherMapper = (resultSet, rowNum) -> {
		UUID voucherId = toUUID(resultSet.getBytes("voucher_id"));
		VoucherType type = VoucherType.of(resultSet.getString("type"));
		long amount = resultSet.getLong("amount");
		return type.equals(VoucherType.FIXED) ?
				new FixedAmountVoucher(voucherId, amount) : new PercentDiscountVoucher(voucherId, amount);
	};

	private Map<String, Object> toParamMap(Voucher voucher) {
		return new HashMap<>() {
			{
				put("voucher_id", voucher.getVoucherId().toString().getBytes());
				put("type", voucher.getVoucherType().toString());
				put("amount", voucher.getDiscount());
			}
		};
	}
}
