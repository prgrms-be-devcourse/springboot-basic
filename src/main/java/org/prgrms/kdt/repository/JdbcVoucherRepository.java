package org.prgrms.kdt.repository;

import static org.prgrms.kdt.utils.UUIDUtils.toUUID;

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
    var voucher = jdbcTemplate.queryForObject(
        "SELECT * FROM voucher WHERE voucher_id = UUID_TO_BIN(:voucher_id)",
        new HashMap<>() {{
          put("voucher_id", voucherId.toString().getBytes());
        }}, voucherRowMapper);

    return Optional.ofNullable(voucher);
  }

  @Override
  public Optional<Voucher> save(Voucher voucher) {
    HashMap<String, Object> paramMap = toParamMap(voucher);
    var updateCount = jdbcTemplate.update(
        "INSERT INTO voucher (voucher_id, amount, customer_id, voucher_type) VALUES (UUID_TO_BIN(:voucher_id), :amount, UUID_TO_BIN(:customer_id), :voucher_type)",
        paramMap);

    return updateCount == 1 ? Optional.of(voucher) : Optional.empty();
  }

  @Override
  public Optional<Voucher> update(Voucher voucher) {
    var updateCount = jdbcTemplate.update(
        "UPDATE voucher SET customer_id = UUID_TO_BIN(:customer_id), amount = :amount, voucher_type = :voucher_type WHERE voucher_id = UUID_TO_BIN(:voucher_id)",
        toParamMap(voucher));
    return updateCount == 1 ? Optional.of(voucher) : Optional.empty();
  }

  @Override
  public List<Voucher> findAll() {
    return jdbcTemplate.query("SELECT * FROM voucher", voucherRowMapper);
  }

  @Override
  public void delete(UUID voucherId, UUID customerId) {
    var update = jdbcTemplate.update(
        "DELETE FROM voucher WHERE voucher_id = UUID_TO_BIN(:voucher_id) AND customer_id = UUID_TO_BIN(:customer_id)",
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
    jdbcTemplate.update("DELETE FROM voucher", Collections.emptyMap());
  }

  @Override
  public List<Voucher> findByCustomerId(UUID customerId) {
    return jdbcTemplate.query("SELECT * FROM voucher WHERE customer_id = UUID_TO_BIN(:customer_id)",
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