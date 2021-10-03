package org.programmers.kdt.voucher.repository;

import org.programmers.kdt.customer.Customer;
import org.programmers.kdt.voucher.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.*;

import static org.programmers.kdt.utils.UuidUtils.toUUID;

@Repository
public class JdbcVoucherRepository implements VoucherRepository {
	// TODO : AOP 적용
	private static final Logger logger = LoggerFactory.getLogger(JdbcVoucherRepository.class);
	private final NamedParameterJdbcTemplate jdbcTemplate;
	private final String voucherTable;

	private static String SQL_INSERT_VOUCHER = "INSERT INTO %s(voucher_id, type, amount) VALUES (UUID_TO_BIN(:voucher_id), :type, :amount)";
	private static String SQL_SELECT_VOUCHER_BY_ID = "SELECT * FROM %s WHERE voucher_id = UUID_TO_BIN(:voucher_id)";
	private static String SQL_SELECT_VOUCHER_BY_OWNER = "SELECT * FROM %s WHERE customer_id = UUID_TO_BIN(:customer_id)";
	private static String SQL_SELECT_VOUCHER_NO_OWNER = "SELECT * FROM %s WHERE customer_id IS null";
	private static String SQL_SELECT_ALL = "SELECT * FROM %s";
	private static String SQL_DELETE_VOCUHER_BY_ID = "DELETE FROM %s WHERE voucher_id = UUID_TO_BIN(:voucher_id)";
	private static String SQL_DEALLOCATE_VOUCHER_BY_ID_AND_OWNER_ID = "UPDATE %s SET customer_id = null WHERE voucher_id = UUID_TO_BIN(:voucher_id)";
	private static String SQL_DEALLOCATE_ALL_VOUCHER_BY_OWNER_ID = "UPDATE %s SET customer_id = null WHERE customer_id = UUID_TO_BIN(:customer_id)";
	private static String SQL_UPDATE_OWNER = "UPDATE %s SET customer_id = UUID_TO_BIN(:customer_id) WHERE voucher_id = UUID_TO_BIN(:voucher_id)";
	private static String SQL_SELECT_VOUCHER_BY_DATE = "SELECT * FROM %s WHERE created_at BETWEEN :from and :to";

	public JdbcVoucherRepository(NamedParameterJdbcTemplate jdbcTemplate, String voucherTable) {
		this.jdbcTemplate = jdbcTemplate;
		this.voucherTable = voucherTable;
	}

	@Override
	public Voucher insert(Voucher voucher) {
		int update = jdbcTemplate.update(SQL_INSERT_VOUCHER.formatted(voucherTable), toParamMap(voucher));

		if (update != 1) {
			logger.error("[FAILED] Insert a new voucher : Already Exists");
		}

		return voucher;
	}

	@Override
	public Optional<Voucher> findById(UUID voucherId) {
		try {
			return Optional.ofNullable(jdbcTemplate.queryForObject(
					SQL_SELECT_VOUCHER_BY_ID.formatted(voucherTable),
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
		jdbcTemplate.update(SQL_DELETE_VOCUHER_BY_ID.formatted(voucherTable),
				Collections.singletonMap("voucher_id", voucherId.toString().getBytes()));
	}

	@Override
	public List<Voucher> findAll() {
		return jdbcTemplate.query(SQL_SELECT_ALL.formatted(voucherTable), voucherMapper);
	}

	@Override
	public List<Voucher> findAllUnregisteredVouchers() {
		return jdbcTemplate.query(SQL_SELECT_VOUCHER_NO_OWNER.formatted(voucherTable), voucherMapper);
	}

	@Override
	public Voucher addOwner(Customer customer, Voucher voucher) {
		if (voucher.getStatus().equals(VoucherStatus.VALID)) {
			jdbcTemplate.update(SQL_UPDATE_OWNER.formatted(voucherTable), voucherOwnerMap(customer, voucher.getVoucherId()));
			return voucher;
		}

		logger.error("Registering invalid voucher : {}", voucher.getVoucherId());
		throw new RuntimeException("Registering invalid voucher.");
	}

	@Override
	public void removeOwner(Customer customer, UUID voucherId) {
		jdbcTemplate.update(SQL_DEALLOCATE_VOUCHER_BY_ID_AND_OWNER_ID.formatted(voucherTable), voucherOwnerMap(customer, voucherId));
	}

	@Override
	public void releaseAllVoucherBelongsTo(Customer customer) {
		jdbcTemplate.update(SQL_DEALLOCATE_ALL_VOUCHER_BY_OWNER_ID.formatted(voucherTable),
				Collections.singletonMap(
						"customer_id", customer.getCustomerId().toString().getBytes()
				));
	}

	@Override
	public Optional<UUID> findCustomerIdByVoucherId(UUID voucherId) {
		return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_SELECT_VOUCHER_BY_ID.formatted(voucherTable),
				Collections.singletonMap("voucher_id", voucherId.toString().getBytes()),
				customerIdMapper));
	}

	@Override
	public List<Voucher> findVouchersByCustomerId(UUID customerId) {
		return jdbcTemplate.query(SQL_SELECT_VOUCHER_BY_OWNER.formatted(voucherTable),
				Collections.singletonMap("customer_id", customerId.toString().getBytes()),
				voucherMapper);
	}

	@Override
	public List<Voucher> findVouchersBetween(Timestamp from, Timestamp to) {
		Map<String, Timestamp> timeMap = new HashMap<>();
		timeMap.put("from", from);
		timeMap.put("to", to);

		return jdbcTemplate.query(SQL_SELECT_VOUCHER_BY_DATE.formatted(voucherTable, from, to),
				timeMap,
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
		return Map.of(
				"voucher_id", voucher.getVoucherId().toString().getBytes(),
				"type", voucher.getVoucherType().toString(),
				"amount", voucher.getDiscount()
		);
	}
}
