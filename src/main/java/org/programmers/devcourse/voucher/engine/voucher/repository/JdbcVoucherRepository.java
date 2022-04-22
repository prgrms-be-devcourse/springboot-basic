package org.programmers.devcourse.voucher.engine.voucher.repository;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import javax.sql.DataSource;
import org.programmers.devcourse.voucher.configuration.Transactional;
import org.programmers.devcourse.voucher.engine.exception.VoucherException;
import org.programmers.devcourse.voucher.engine.voucher.VoucherType;
import org.programmers.devcourse.voucher.engine.voucher.entity.Voucher;
import org.programmers.devcourse.voucher.util.UUIDMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Repository;

@Repository
@Profile({"dev", "web"})
public class JdbcVoucherRepository implements VoucherRepository, Transactional {

  private static final Logger logger = LoggerFactory.getLogger(JdbcVoucherRepository.class);
  private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  private final DataSourceTransactionManager transactionManager;

  public JdbcVoucherRepository(DataSource dataSource) {
    this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(new JdbcTemplate(dataSource));
    this.transactionManager = new DataSourceTransactionManager(dataSource);
  }

  private RowMapper<Voucher> voucherRowMapper = (resultSet, index) -> {
    UUID voucherId;
    try {
      voucherId = UUIDMapper.fromBytes(resultSet.getBinaryStream("voucher_id").readAllBytes());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    var type = resultSet.getString("type");
    var discountDegree = resultSet.getInt("discount_degree");

    return VoucherType.from(type).orElseThrow(() -> new SQLException()).getFactory()
        .create(voucherId, discountDegree);

  };

  private Map<String, Object> mapToParam(Voucher voucher) {
    var map = new ConcurrentHashMap<String, Object>();
    map.put("voucherId", voucher.getVoucherId().toString().getBytes(StandardCharsets.UTF_8));
    map.put("type", VoucherType.mapToTypeId(voucher));
    map.put("discountDegree", voucher.getDiscountDegree());
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
    var voucher = namedParameterJdbcTemplate.queryForObject(
        "SELECT voucher_id , type, discount_degree FROM vouchers WHERE voucher_id = UUID_TO_BIN(:voucherId)",
        Map.of("voucherId", UUIDMapper.toBytes(voucherId)),
        voucherRowMapper);
    return Optional.ofNullable(voucher);
  }

  @Override
  public List<Voucher> getAllVouchers() {
    return namedParameterJdbcTemplate.query(
        "SELECT voucher_id, type, discount_degree FROM vouchers",
        voucherRowMapper);
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
