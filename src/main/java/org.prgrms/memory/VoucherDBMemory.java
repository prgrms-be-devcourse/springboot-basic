package org.prgrms.memory;

import static org.prgrms.memory.query.VoucherSQL.DELETE_ALL;
import static org.prgrms.memory.query.VoucherSQL.DELETE_BY_ID;
import static org.prgrms.memory.query.VoucherSQL.FIND_ALL;
import static org.prgrms.memory.query.VoucherSQL.FIND_BY_ID;
import static org.prgrms.memory.query.VoucherSQL.INSERT;
import static org.prgrms.memory.query.VoucherSQL.UPDATE;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import org.prgrms.voucher.discountType.Amount;
import org.prgrms.voucher.voucherType.Voucher;
import org.prgrms.voucher.voucherType.VoucherType;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@Profile("jdbc")
public class VoucherDBMemory implements Memory {


  private final NamedParameterJdbcTemplate jdbcTemplate;
  private final int NO_RESULT = 0;


  public VoucherDBMemory(NamedParameterJdbcTemplate jdbcTemplate) {

    this.jdbcTemplate = jdbcTemplate;
  }

  private static final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
    UUID id = UUID.fromString(resultSet.getString("id"));
    VoucherType type = VoucherType.of(resultSet.getString("type"));
    Amount amount = type.generateAmount(resultSet.getLong("amount"));

    return type.generateVoucherWithId(id, amount);
  };


  public Voucher save(Voucher voucher) {
    Map<String, Object> paramMap = new HashMap<>() {{
      put("id", String.valueOf(voucher.getVoucherId()));
      put("voucherType", voucher.getVoucherType().name());
      put("amountValue", voucher.getVoucherAmount().getValue());
    }};
    try {
      jdbcTemplate.update(INSERT.getSql(), paramMap);
    } catch (BadSqlGrammarException e) {
      throw new DuplicateKeyException(
          "ID for this voucher already exists *current id: " + voucher.getVoucherId());
    }
    int type = voucher.getVoucherType().getType();
    return VoucherType.of(type).generateVoucherWithId(voucher.getVoucherId(), voucher.getVoucherAmount());
  }

  public List<Voucher> findAll() {

    return jdbcTemplate.query(FIND_ALL.getSql(), voucherRowMapper);
  }

  public Optional<Voucher> findById(UUID id) {

    try {
      Voucher voucher = jdbcTemplate.queryForObject(FIND_BY_ID.getSql(),
          Collections.singletonMap("id", String.valueOf(id)), voucherRowMapper);
      return Optional.ofNullable(voucher);
    } catch (DataAccessException e) {
      return Optional.empty();
    }
  }

  public void deleteById(UUID id) {
    jdbcTemplate.update(DELETE_BY_ID.getSql(), Collections.singletonMap("id", String.valueOf(id)));
  }

  public void deleteAll() {
    jdbcTemplate.update(DELETE_ALL.getSql(), Collections.emptyMap());
  }

  public Voucher update(Voucher voucher) {
    Map<String, Object> paramMap = new HashMap<>() {{
      put("voucherType", voucher.getVoucherType().name());
      put("amountValue", voucher.getVoucherAmount().getValue());
      put("id", String.valueOf(voucher.getVoucherId()));
    }};

    int updateNum = jdbcTemplate.update(UPDATE.getSql(), paramMap);
    if (updateNum == NO_RESULT) {
      throw new NoSuchElementException(
          "That ID could not be found *current ID : " + voucher.getVoucherId());
    }
    return voucher;
  }
}
