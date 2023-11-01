package com.prgrms.voucher_manage.domain.wallet.repository;

import com.prgrms.voucher_manage.domain.wallet.entity.Wallet;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.prgrms.voucher_manage.exception.ErrorMessage.CUSTOMER_NOT_EXIST;

@Repository
@RequiredArgsConstructor
public class JdbcWalletRepository implements WalletRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Wallet save(Wallet wallet) {
        String sql = "insert into wallet(voucher_id, customer_id) values (?,?)";
        jdbcTemplate.update(sql, wallet.getVoucher_id().toString(), wallet.getCustomer_id().toString());
        return wallet;
    }

    @Override
    public List<Wallet> findByCustomerId(UUID customerId) {
        String sql = "select * from wallet where customer_id = ?";
        try {
            return jdbcTemplate.query(sql, rowMapper, customerId.toString());
        } catch (Exception e) {
            throw new RuntimeException(CUSTOMER_NOT_EXIST.getMessage());
        }
    }

    @Override
    public List<Wallet> findByVoucherId(UUID voucherId) {
        String sql = "select * from wallet where voucher_id = ?";
        return jdbcTemplate.query(sql, rowMapper, voucherId.toString());
    }

    @Override
    public Optional<Wallet> findByIds(UUID customerId, UUID voucherId) {
        String sql = "select * from wallet where customer_id = ? and voucher_id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, rowMapper, customerId.toString(), voucherId.toString()));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public int delete(Wallet wallet) {
        String sql = "delete from wallet where customer_id = ? and voucher_id = ?";
        return jdbcTemplate.update(sql, wallet.getCustomer_id().toString(), wallet.getVoucher_id().toString());
    }

    private static final RowMapper<Wallet> rowMapper = (resultSet, i) -> {
        UUID customerId = UUID.fromString(resultSet.getString("customer_id"));
        UUID voucherId = UUID.fromString(resultSet.getString("voucher_id"));

        return new Wallet(customerId, voucherId);
    };
}
