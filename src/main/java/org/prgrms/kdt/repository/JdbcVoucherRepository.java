package org.prgrms.kdt.repository;

import static org.prgrms.kdt.utils.ByteUtils.toUUID;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.prgrms.kdt.domain.Voucher;
import org.prgrms.kdt.dto.VoucherDto;
import org.prgrms.kdt.type.VoucherType;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcVoucherRepository implements VoucherRepository {

  public static final String SELECT_ALL_VOUCHERS = "SELECT * FROM voucher";
  public static final String SELECT_VOUCHERS_BY_CUSTOMER_ID = "SELECT * FROM voucher WHERE customer_id = UUID_TO_BIN(:customer_id)";
  public static final String SELECT_VOUCHER_BY_VOUCHER_ID = "SELECT * FROM voucher WHERE voucher_id = UUID_TO_BIN(:voucher_id)";
  public static final String INSERT_VOUCHER = "INSERT INTO voucher (voucher_id, amount, customer_id, voucher_type) VALUES (UUID_TO_BIN(:voucher_id), :amount, UUID_TO_BIN(:customer_id), :voucher_type)";
  public static final String UPDATE_VOUCHER = "UPDATE voucher SET customer_id = UUID_TO_BIN(:customer_id), amount = :amount, voucher_type = :voucher_type WHERE voucher_id = UUID_TO_BIN(:voucher_id)";
  public static final String DELETE_VOUCHER_BY_VOUCHER_ID_AND_CUSTOMER_ID = "DELETE FROM voucher WHERE voucher_id = UUID_TO_BIN(:voucher_id) AND customer_id = UUID_TO_BIN(:customer_id)";
  public static final String DELETE_ALL_VOUCHERS = "DELETE FROM voucher";
  private static final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
    var voucherId = toUUID(resultSet.getBytes("voucher_id"));
    var voucherType = VoucherType.of(resultSet.getInt("voucher_type"));
    var amount = resultSet.getLong("amount");
    var customerId = resultSet.getBytes("customer_id") == null ? null
        : toUUID(resultSet.getBytes("customer_id"));
    var voucherDto = new VoucherDto(voucherId, customerId, amount);
    return voucherType.create(voucherDto);
  };
  private final NamedParameterJdbcTemplate jdbcTemplate;

  public JdbcVoucherRepository(NamedParameterJdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public Optional<Voucher> findById(UUID voucherId) {
    var voucher = jdbcTemplate.queryForObject(SELECT_VOUCHER_BY_VOUCHER_ID,
        new HashMap<>() {{
          put("voucher_id", voucherId.toString().getBytes());
        }}, voucherRowMapper);

    return Optional.ofNullable(voucher);
  }

  @Override
  public Voucher save(Voucher voucher) {
    HashMap<String, Object> paramMap = toParamMap(voucher);
    var updateCount = jdbcTemplate.update(INSERT_VOUCHER, paramMap);
    if (updateCount != 1) {
      throw new RuntimeException("Failed to save the voucher");
    }

    return voucher;
  }

  @Override
  public Voucher update(Voucher voucher) {
    var updateCount = jdbcTemplate.update(UPDATE_VOUCHER, toParamMap(voucher));
    if (updateCount != 1) {
      throw new RuntimeException("Failed to update the voucher");
    }

    return voucher;
  }

  @Override
  public List<Voucher> findAll() {
    return jdbcTemplate.query(SELECT_ALL_VOUCHERS, voucherRowMapper);
  }

  @Override
  public void delete(UUID voucherId, UUID customerId) {
    var update = jdbcTemplate.update(DELETE_VOUCHER_BY_VOUCHER_ID_AND_CUSTOMER_ID,
        new HashMap<>() {{
          put("voucher_id", voucherId.toString().getBytes());
          put("customer_id", customerId.toString().getBytes());
        }});
    if (update != 1) {
      throw new RuntimeException("Failed to delete the voucher");
    }
  }

  @Override
  public void deleteAll() {
    jdbcTemplate.update(DELETE_ALL_VOUCHERS, Collections.emptyMap());
  }

  @Override
  public List<Voucher> findByCustomerId(UUID customerId) {
    return jdbcTemplate.query(SELECT_VOUCHERS_BY_CUSTOMER_ID,
        new HashMap<>() {{
          put("customer_id", customerId.toString().getBytes());
        }}, voucherRowMapper);
  }

  private HashMap<String, Object> toParamMap(Voucher voucher) {
    var customerId =
        voucher.getCustomerId() == null ? null : voucher.getCustomerId().toString().getBytes();

    return new HashMap<>() {{
      put("voucher_id", voucher.getVoucherId().toString().getBytes());
      put("amount", voucher.getAmount());
      put("voucher_type", voucher.getType());
      put("customer_id", customerId);
    }};
  }
}