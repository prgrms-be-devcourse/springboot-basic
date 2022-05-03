package org.prgrms.kdt.repository;

import static org.prgrms.kdt.utils.UUIDUtils.toUUID;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.prgrms.kdt.domain.Voucher;
import org.prgrms.kdt.dto.VoucherDto;
import org.prgrms.kdt.type.VoucherType;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcVoucherRepository implements VoucherRepository {

  private static final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
    var voucherId = toUUID(resultSet.getBytes("voucher_id"));
    var voucherTypeCode = resultSet.getInt("voucher_type");
    var voucherType = VoucherType.of(voucherTypeCode);
    var amount = resultSet.getLong("amount");
    var customerId = resultSet.getBytes("customer_id") == null ? null
        : toUUID(resultSet.getBytes("customer_id"));
    var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
    var voucherDto = new VoucherDto(voucherId, customerId, amount, voucherType, createdAt);
    return voucherType.create(voucherDto);
  };

  private final NamedParameterJdbcTemplate jdbcTemplate;

  public JdbcVoucherRepository(NamedParameterJdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public Optional<Voucher> findById(UUID voucherId) {
    var voucher = jdbcTemplate.queryForObject(
        """
            SELECT *\040
            FROM voucher\040
            WHERE voucher_id = UUID_TO_BIN(:voucher_id)
            """,
        new HashMap<>() {{
          put("voucher_id", voucherId.toString().getBytes());
        }}, voucherRowMapper);

    return Optional.ofNullable(voucher);
  }

  @Override
  public Optional<Voucher> save(Voucher voucher) {
    HashMap<String, Object> paramMap = toParamMap(voucher);
    var updateCount = jdbcTemplate.update(
        """
            INSERT INTO voucher (voucher_id, amount, voucher_type)\040
            VALUES (UUID_TO_BIN(:voucher_id), :amount, :voucher_type)
            """,
        paramMap);

    return updateCount == 1 ? Optional.of(voucher) : Optional.empty();
  }

  @Override
  public Optional<Voucher> update(Voucher voucher) {
    var updateCount = jdbcTemplate.update(
        """
            UPDATE voucher\040
            SET customer_id = UUID_TO_BIN(:customer_id),\040
            amount = :amount,\040
            voucher_type = :voucher_type\040
            WHERE voucher_id = UUID_TO_BIN(:voucher_id)
            """,
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
        """
            DELETE FROM voucher\040
            WHERE voucher_id = UUID_TO_BIN(:voucher_id)\040
            AND customer_id = UUID_TO_BIN(:customer_id)
            """,
        new HashMap<>() {{
          put("voucher_id", voucherId.toString().getBytes());
          put("customer_id", customerId.toString().getBytes());
        }});
    if (update != 1) {
      throw new EmptyResultDataAccessException(1);
    }
  }

  @Override
  public void deleteById(UUID voucherId) {
    var update = jdbcTemplate.update(
        """
            DELETE FROM voucher\040
            WHERE voucher_Id = UUID_TO_BIN(:voucherId)
            """,
        new HashMap<>() {{
          put("voucherId", voucherId.toString().getBytes());
        }});
    if (update != 1) {
      throw new EmptyResultDataAccessException(1);
    }
  }

  @Override
  public void deleteAll() {
    jdbcTemplate.update("DELETE FROM voucher", Collections.emptyMap());
  }

  @Override
  public List<Voucher> findByCustomerId(UUID customerId) {
    return jdbcTemplate.query(
        """
            SELECT *\040
            FROM voucher\040
            WHERE customer_id = UUID_TO_BIN(:customer_id)
            """,
        new HashMap<>() {{
          put("customer_id", customerId.toString().getBytes());
        }}, voucherRowMapper);
  }

  @Override
  public List<Voucher> findByTypeAndCreatedAt(Integer voucherType, LocalDateTime startAt,
      LocalDateTime endAt) {
    StringBuilder query = new StringBuilder(
        """
            SELECT *
            FROM voucher
            WHERE 1 = 1
            """
    );
    Map<String, Object> queryParams = new HashMap<>();
    if (voucherType != null) {
      query.append(" AND voucher_type = :voucherType");
      queryParams.put("voucherType", voucherType);
    }
    if (startAt != null) {
      query.append(" AND created_at >= :startAt");
      queryParams.put("startAt", startAt);
    }
    if (endAt != null) {
      query.append(" AND created_at <= :endAt");
      queryParams.put("endAt", endAt);
    }
    return jdbcTemplate.query(query.toString(), queryParams, voucherRowMapper);
  }


  private HashMap<String, Object> toParamMap(Voucher voucher) {
    var customerId =
        voucher.getCustomerId() == null ? null : voucher.getCustomerId().toString().getBytes();

    return new HashMap<>() {{
      put("voucher_id", voucher.getVoucherId().toString().getBytes());
      put("amount", voucher.getAmount());
      put("voucher_type", voucher.getType().getCode());
      put("customer_id", customerId);
    }};
  }
}