package org.programs.kdt.Voucher.repository;

import lombok.RequiredArgsConstructor;
import org.programs.kdt.Exception.EntityNotFoundException;
import org.programs.kdt.Exception.ErrorCode;
import org.programs.kdt.Voucher.domain.Voucher;
import org.programs.kdt.Voucher.domain.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
@Profile("db")
public class JdbcVoucherRepository implements VoucherRepository {

  private static final Logger logger = LoggerFactory.getLogger(JdbcVoucherRepository.class);

  private final JdbcTemplate jdbcTemplate;

  private static final RowMapper<Voucher> voucherRowMapper =
      (rs, rowNum) -> {
        String voucherId = rs.getString("voucher_id");
        VoucherType voucherType = VoucherType.findVoucherType(rs.getString("type"));
        Long value = rs.getLong("voucher_value");
        LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
        return voucherType.createVoucher(UUID.fromString(voucherId), value, createdAt);
      };

  @Override
  public List<Voucher> findAll() {
    return jdbcTemplate.query("select * from voucher", voucherRowMapper);
  }

  @Override
  public Voucher insert(Voucher voucher) {

    jdbcTemplate.update(
        "INSERT INTO voucher(voucher_id, type, voucher_value, created_at) VALUES (?, ?, ?, ?)",
        voucher.getVoucherId().toString(),
        voucher.getVoucherType().getType(),
        voucher.getValue(),
        Timestamp.valueOf(voucher.getCreatedAt()));
    return voucher;
  }

  @Override
  public Optional<Voucher> findById(UUID voucherId) {
    try {
      return Optional.ofNullable(
          jdbcTemplate.queryForObject(
              "select * from voucher WHERE voucher_id = ?",
              voucherRowMapper,
              voucherId.toString()));
    } catch (EmptyResultDataAccessException e) {
      logger.error("Got empty result");
      return Optional.empty();
    }
  }

  @Override
  public List<Voucher> findByType(VoucherType voucherType) {
    return jdbcTemplate.query(
        "select * from voucher where type = ?", voucherRowMapper, voucherType.getType());
  }

  @Override
  public boolean existId(UUID voucherId) {
    int count =
            jdbcTemplate.queryForObject(
                    "select count(*) from voucher where voucher_id = ?", Integer.class, voucherId.toString());
    return count > 0 ? true : false;
  }

  @Override
  public Voucher update(Voucher voucher) {
    int update =
        jdbcTemplate.update(
            "UPDATE voucher SET type = ?, voucher_value = ?, created_at = ? WHERE voucher_id = ?",
            voucher.getVoucherType().getType(),
            voucher.getValue(),
            Timestamp.valueOf(voucher.getCreatedAt()),
            voucher.getVoucherId().toString());
    if (update != 1) {
      throw new EntityNotFoundException(ErrorCode.NOT_FOUND_VOUCHER_ID);
    }
    return voucher;
  }

  @Override
  public void deleteAll() {
    jdbcTemplate.update("DELETE FROM VOUCHER");
  }

  @Override
  public void delete(UUID uuid) {
    String uuidString = uuid.toString();
    int update = jdbcTemplate.update("DELETE FROM VOUCHER WHERE voucher_id=?", uuidString);
    if (update != 1) {
      throw new EntityNotFoundException(ErrorCode.NOT_FOUND_VOUCHER_ID);
    }
  }
}
