package org.programs.kdt.Wallet;

import lombok.RequiredArgsConstructor;
import org.programs.kdt.Customer.Customer;
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

@RequiredArgsConstructor
@Repository
@Profile("db")
public class JdbcWalletRepository implements WalletRepository {

  private static final Logger logger = LoggerFactory.getLogger(JdbcWalletRepository.class);

  private final JdbcTemplate jdbcTemplate;

  private static final RowMapper<Wallet> walletRowMapper =
      (rs, rowNum) -> {
        UUID voucherId = UUID.fromString(rs.getString("voucher_id"));
        Long voucherValue = Long.parseLong(rs.getString("voucher_value"));
        VoucherType voucherType = VoucherType.findVoucherType(rs.getString("type"));
        Voucher voucher = voucherType.createVoucher(voucherId, voucherValue, LocalDateTime.now());

        UUID customerId = UUID.fromString(rs.getString("customer_id"));
        String name = rs.getString("name");
        String email = rs.getString("email");
        Customer customer = new Customer(customerId, name, email);

        UUID walletId = UUID.fromString(rs.getString("wallet_id"));
        LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();

        return new Wallet(voucher, customer, walletId, createdAt);
      };

  @Override
  public List<Wallet> findAll() {
    return jdbcTemplate.query(
        "select w.wallet_id, w.voucher_id, w.customer_id, c.name, c.email, v.voucher_value, w.created_at, v.type from wallet as w\n"
            + "left join voucher as v on w.voucher_id = v.voucher_id\n"
            + "left join customers as c on c.customer_id = w.customer_id\n",
        walletRowMapper);
  }

  @Override
  public Wallet insert(Wallet wallet) {
    jdbcTemplate.update(
        "INSERT INTO wallet(wallet_id, voucher_id, customer_id, created_at) VALUES (?, ?, ?, ?)",
        wallet.getWalletId().toString(),
        wallet.getVoucherId().toString(),
        wallet.getCustomerId().toString(),
        Timestamp.valueOf(wallet.getCreatedAt()));
    return wallet;
  }

  @Override
  public List<Wallet> findByVoucherId(UUID voucherId) {
    return jdbcTemplate.query(
        "select w.wallet_id, w.voucher_id, w.customer_id, c.name, c.email, v.voucher_value, v.type, w.created_at from wallet w\n"
            + "left join voucher v on w.voucher_id = v.voucher_id\n"
            + "left join customers c on c.customer_id = w.customer_id\n"
            + "where w.voucher_id = ?",
        walletRowMapper,
        voucherId.toString());
  }

  @Override
  public List<Wallet> findByCustomerId(UUID customerId) {
    return jdbcTemplate.query(
        "select w.wallet_id, w.voucher_id, w.customer_id, c.name, c.email, v.voucher_value, w.created_at, v.type from wallet as w "
            + "left join voucher as v on w.voucher_id = v.voucher_id "
            + "left join customers as c on c.customer_id = w.customer_id "
            + "where w.customer_id = ?",
        walletRowMapper,
        customerId.toString());
  }

  @Override
  public Optional<Wallet> findById(UUID walletId) {
    try {
      return Optional.ofNullable(
          jdbcTemplate.queryForObject(
              "select w.wallet_id, w.voucher_id, w.customer_id, c.name, c.email, v.voucher_value, v.type, w.created_at from wallet as w "
                  + "left join voucher as v on w.voucher_id = v.voucher_id "
                  + "left join customers as c on c.customer_id = w.customer_id "
                  + "where w.wallet_id = ?",
              walletRowMapper,
              walletId.toString()));
    } catch (EmptyResultDataAccessException e) {
      logger.error("Got empty result", e);
      return Optional.empty();
    }
  }

  @Override
  public List<Wallet> findByCustomerEmail(String email) {
    return jdbcTemplate.query(
        "select w.wallet_id, w.voucher_id, w.customer_id, c.name, c.email, v.voucher_value, v.type, w.created_at from wallet as w "
            + "left join voucher as v on w.voucher_id = v.voucher_id "
            + "left join customers as c on c.customer_id = w.customer_id "
            + "where c.email = ?",
        walletRowMapper,
        email);
  }

  @Override
  public void deleteByCustomerId(UUID customerId) {
    jdbcTemplate.update("DELETE FROM WALLET WHERE customer_id = ?", customerId);
  }

  @Override
  public void deleteByVoucherId(UUID voucherId) {
    jdbcTemplate.update("DELETE FROM WALLET WHERE voucher_id = ?", voucherId);
  }

  @Override
  public void deleteById(UUID walletId) {
    jdbcTemplate.update("DELETE FROM wallet where wallet_id = ?", walletId.toString());
  }

  @Override
  public void deleteAll() {
    jdbcTemplate.update("DELETE FROM wallet");
  }

  @Override
  public boolean existWalletId(UUID walletId) {
    int count =
        jdbcTemplate.queryForObject(
            "select count(*) from wallet where wallet_id = ?", Integer.class, walletId.toString());
    return count > 0 ? true : false;
  }
}
