package org.programmers.devcourse.voucher.engine.voucher.repository;

import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import javax.sql.DataSource;
import org.programmers.devcourse.voucher.configuration.Transactional;
import org.programmers.devcourse.voucher.engine.exception.VoucherException;
import org.programmers.devcourse.voucher.engine.voucher.VoucherType;
import org.programmers.devcourse.voucher.engine.voucher.entity.Voucher;
import org.programmers.devcourse.voucher.util.UUIDMapper;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Repository;

@Repository
@Profile({"dev", "web"})
@Primary
public class JdbcVoucherRepository implements VoucherRepository, Transactional {

  static final String PARAM_KEY_VOUCHER_ID = "voucherId";
  static final String PARAM_KEY_TYPE = "type";
  static final String PARAM_KEY_DISCOUNT_DEGREE = "discountDegree";

  private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
  private final DataSourceTransactionManager transactionManager;
  private final RowMapper<Voucher> voucherRowMapper = (resultSet, ignored) -> {
    var voucherId = UUIDMapper.fromBytes(resultSet.getBytes("voucher_id"));
    var type = resultSet.getString("type");
    var discountDegree = resultSet.getInt("discount_degree");
    var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();

    return VoucherType.from(type).orElseThrow(SQLException::new)
        .createVoucher(voucherId, discountDegree, createdAt);
  };

  public JdbcVoucherRepository(DataSource dataSource) {
    this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(new JdbcTemplate(dataSource));
    this.transactionManager = new DataSourceTransactionManager(dataSource);
  }

  private Map<String, Object> mapToParam(Voucher voucher) {
    var map = new HashMap<String, Object>();
    map.put(PARAM_KEY_VOUCHER_ID,
        voucher.getVoucherId().toString().getBytes(StandardCharsets.UTF_8));
    map.put(PARAM_KEY_TYPE, VoucherType.mapToTypeId(voucher));
    map.put(PARAM_KEY_DISCOUNT_DEGREE, voucher.getDiscountDegree());
    return map;
  }

  @Override
  public UUID save(Voucher voucher) throws VoucherException {
    namedParameterJdbcTemplate.update(
        "INSERT INTO vouchers(voucher_id, type, discount_degree) values (UUID_TO_BIN(:voucherId), :type, :discountDegree)",
        mapToParam(voucher));
    return voucher.getVoucherId();
  }

  @Override
  public Optional<Voucher> getVoucherById(UUID voucherId) {
    try {
      var voucher = namedParameterJdbcTemplate.queryForObject(
          "SELECT voucher_id , type, discount_degree,created_at FROM vouchers WHERE voucher_id = UUID_TO_BIN(:voucherId)",
          Map.of(PARAM_KEY_VOUCHER_ID, UUIDMapper.toBytes(voucherId)),
          voucherRowMapper);
      return Optional.ofNullable(voucher);
    } catch (EmptyResultDataAccessException exception) {
      return Optional.empty();
    }
  }

  @Override
  public List<Voucher> getAllVouchers() {
    return namedParameterJdbcTemplate.query(
        "SELECT voucher_id, type, discount_degree,created_at FROM vouchers", voucherRowMapper);
  }

  @Override
  public List<Voucher> getVouchersByType(String type) {
    return namedParameterJdbcTemplate.query(
        "SELECT voucher_id, type, discount_degree,created_at FROM vouchers WHERE type=:type",
        Map.of("type", type), voucherRowMapper);
  }

  @Override
  public int deleteAll() {
    return namedParameterJdbcTemplate.update("DELETE FROM vouchers", Collections.emptyMap());
  }

  @Override
  public void delete(UUID voucherId) {
    int result = namedParameterJdbcTemplate.update(
        "DELETE FROM vouchers WHERE voucher_id = UUID_TO_BIN(:voucherId)",
        Map.of(PARAM_KEY_VOUCHER_ID, UUIDMapper.toBytes(voucherId)));
    if (result != 1) {
      throw new EmptyResultDataAccessException(1);
    }
  }

  @Override
  public void runTransaction(Runnable runnable) {
    var status = transactionManager.getTransaction(null);
    try {
      runnable.run();
      transactionManager.commit(status);
    } catch (DataAccessException exception) {
      transactionManager.rollback(status);
      throw exception;
    }
  }
}
