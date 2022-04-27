package org.prgrms.vouchermanagement.customer.wallet;

import org.prgrms.vouchermanagement.customer.Customer;
import org.prgrms.vouchermanagement.customer.repository.JdbcCustomerRepository;
import org.prgrms.vouchermanagement.voucher.voucher.Voucher;
import org.prgrms.vouchermanagement.voucher.voucher.VoucherFactory;
import org.prgrms.vouchermanagement.voucher.voucher.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

@Repository
public class JdbcWalletRepository implements WalletRepository {

  private static final Logger log = LoggerFactory.getLogger(JdbcCustomerRepository.class);

  private final NamedParameterJdbcTemplate jdbcTemplate;

  public JdbcWalletRepository(NamedParameterJdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  private static RowMapper<UUID> voucherIdRowMapper =  (resultSet, i) -> {
    return toUUID(resultSet.getBytes("voucher_id"));
  };

  private static RowMapper<UUID> customerIdRowMapper =  (resultSet, i) -> {
    return toUUID(resultSet.getBytes("customer_id"));
  };

  @Override
  public List<UUID> findVouchersByCustomerId(UUID customerId) {
    return jdbcTemplate.query("SELECT voucher_id FROM wallet WHERE customer_id = UUID_TO_BIN(:customerId)",
      Collections.singletonMap("customerId", customerId.toString().getBytes()),
      voucherIdRowMapper
    );
  }

  @Override
  public List<UUID> findCustomersByVoucherId(UUID voucherId) {
    return jdbcTemplate.query("SELECT customer_id FROM wallet WHERE voucher_id = UUID_TO_BIN(:voucherId)",
      Collections.singletonMap("voucherId", voucherId.toString().getBytes()),
      customerIdRowMapper
    );
  }

  @Override
  public void insert(UUID customerId, UUID voucherId) {
    int update = jdbcTemplate.update(
      "INSERT INTO wallet(voucher_id, customer_id) VALUES (UUID_TO_BIN(:voucherId), UUID_TO_BIN(:customerId))",
      toParamMap(customerId, voucherId));
    if(update != 1) {
      throw new RuntimeException("Nothing was inserted");
    }
  }

  @Override
  public void delete(UUID customerId, UUID voucherId) {
    try {
      jdbcTemplate.update("DELETE FROM wallet WHERE voucher_id = UUID_TO_BIN(:voucherId) AND customer_id = UUID_TO_BIN(:customerId)",
        toParamMap(customerId, voucherId));
    } catch (Exception e) {
      log.error("No such voucher", e);
    }
  }

  @Override
  public int count() {
    return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM wallet", Collections.emptyMap(), Integer.class);
  }

  private Map<String, Object> toParamMap(UUID customerId, UUID voucherId) {
    return new HashMap<String, Object>(){{
      put("customerId", customerId.toString().getBytes());
      put("voucherId", voucherId.toString().getBytes());
    }};
  }

  static UUID toUUID(byte[] bytes) throws SQLException {
    var byteBuffer = ByteBuffer.wrap(bytes);
    return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
  }
}
