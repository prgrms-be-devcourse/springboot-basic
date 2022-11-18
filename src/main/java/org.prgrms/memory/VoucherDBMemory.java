package org.prgrms.memory;

import static org.prgrms.query.VoucherSQL.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.prgrms.voucher.discountType.Amount;
import org.prgrms.voucher.voucherType.Voucher;
import org.prgrms.voucher.voucherType.VoucherType;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
@Profile("jdbc")
public class VoucherDBMemory implements Memory {


  private final JdbcTemplate jdbcTemplate;


  public VoucherDBMemory(JdbcTemplate jdbcTemplate) {

    this.jdbcTemplate = jdbcTemplate;
  }

  private static final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
    UUID id = UUID.fromString(resultSet.getString("id"));
    VoucherType type = VoucherType.of(resultSet.getString("type"));
    Amount amount = type.generateAmount(resultSet.getLong("amount"));

    return type.generateVoucherWithId(id, amount);
  };


  public Voucher save(Voucher voucher) {
    try {
      jdbcTemplate.update(INSERT.getSql(), String.valueOf(voucher.getVoucherId()),
          String.valueOf(voucher.getVoucherType()),
          voucher.getVoucherAmount().getValue());
    } catch (DataAccessException e) {
      throw new DuplicateKeyException(
          "ID for this voucher already exists *current id: " + voucher.getVoucherId());
    }
    return voucher;
  }

  public List<Voucher> findAll() {

    return jdbcTemplate.query(FIND_ALL.getSql(), voucherRowMapper);
  }

  public Optional<Voucher> findById(UUID id) {
    Voucher voucher;
    try {
      voucher = jdbcTemplate.queryForObject(FIND_BY_ID.getSql(), voucherRowMapper,
          String.valueOf(id));
    } catch (DataAccessException e) {
      return Optional.empty();
    }
    return Optional.ofNullable(voucher);
  }

  public Optional<Voucher> deleteById(UUID id) {
    Optional<Voucher> beforeDeletion = findById(id);
    if (beforeDeletion.isPresent()) {
      jdbcTemplate.update(DELETE_BY_ID.getSql(), String.valueOf(id));
    }
    return beforeDeletion;
  }

  public void deleteAll() {
    jdbcTemplate.update(DELETE_ALL.getSql());

  }
}
