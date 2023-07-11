package com.prgrms.commandLineApplication.repository.voucher;

import com.prgrms.commandLineApplication.voucher.Voucher;
import com.prgrms.commandLineApplication.voucher.VoucherFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;

@Repository
public class JdbcVoucherRepository implements VoucherRepository {

  private static final String NO_EXIST_VOUCHER = "It doesn't exist";
  private final NamedParameterJdbcTemplate jdbcTemplate;

  public JdbcVoucherRepository(NamedParameterJdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public Voucher save(Voucher voucher) {
    SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
            .addValue("voucherId", voucher.getVoucherId().toString())
            .addValue("voucherType", voucher.supplyDiscountType().toString())
            .addValue("discountAmount", voucher.supplyDiscountAmount());
    jdbcTemplate.update("INSERT INTO vouchers(voucher_id, voucher_type, discount_amount) VALUES(:voucherId, :voucherType, :discountAmount)",
            sqlParameterSource);
    return voucher;
  }

  @Override
  public List<Voucher> findAll() {
    List<Voucher> voucherList = jdbcTemplate.query("SELECT * FROM vouchers", voucherRowMapper);
    return voucherList;
  }

  @Override
  public Voucher findById(UUID voucherId) {
    try {
      Voucher voucher = jdbcTemplate.queryForObject("SELECT * FROM vouchers WHERE voucher_id = :voucherId",
              Map.of("voucherId", voucherId), voucherRowMapper);
      return voucher;
    } catch (EmptyResultDataAccessException e) {
      throw new NoSuchElementException(NO_EXIST_VOUCHER + " -> " + voucherId);
    }
  }

  private final RowMapper<Voucher> voucherRowMapper = (resultSet, rowNum) ->
          VoucherFactory.of(
                  UUID.fromString(resultSet.getString("voucher_id")),
                  resultSet.getString("voucher_type"),
                  resultSet.getInt("discount_amount")
          );

}
