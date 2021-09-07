package org.prgrms.kdt.wallet;

import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import org.prgrms.kdt.customer.Customer;
import org.prgrms.kdt.customer.CustomerDto;
import org.prgrms.kdt.voucher.Voucher;
import org.prgrms.kdt.voucher.VoucherType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 * Created by yhh1056
 * Date: 2021/09/04 Time: 1:51 오전
 */
@Repository
public class WalletJdbcRepository implements WalletRepository {

    private final JdbcTemplate jdbcTemplate;

    public WalletJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        var voucherId = toUUID(resultSet.getBytes("voucher_id"));
        var name = resultSet.getString("name");
        var discount = resultSet.getLong("discount");
        var voucherType = resultSet.getString("voucher_type");
        var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        return new Voucher(voucherId, name, discount, VoucherType.valueOf(voucherType), createdAt);
    };

    private static final RowMapper<Customer> customerRowMapper =  (resultSet, i) -> {
        var customerId = toUUID(resultSet.getBytes("customer_id"));
        var customerName = resultSet.getString("name");
        var email = resultSet.getString("email");
        var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        var lastLoginAt = resultSet.getTimestamp("last_login_at") != null ?
                resultSet.getTimestamp("last_login_at").toLocalDateTime() : null;
        return new Customer(customerId, customerName, email, createdAt, lastLoginAt);
    };

    static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    @Override
    public int insert(Wallet wallet) {
        return jdbcTemplate.update("INSERT INTO wallets (wallet_id, customer_id, voucher_id) VALUES (UUID_TO_BIN(?), UUID_TO_BIN(?), UUID_TO_BIN(?))",
                wallet.voucherWalletId().toString().getBytes(),
                wallet.customerId().toString().getBytes(),
                wallet.voucherId().toString().getBytes());
    }

    @Override
    public int deleteAll() {
        return jdbcTemplate.update("DELETE FROM wallets");
    }

    public int deleteBy(UUID customerId, UUID voucherId) {
        return jdbcTemplate.update("DELETE FROM wallets "
                                   + "WHERE customer_id = UUID_TO_BIN(?) AND voucher_id = UUID_TO_BIN(?) "
                                   + "ORDER BY created_at ASC "
                                   + "LIMIT 1",
                customerId.toString().getBytes(),
                voucherId.toString().getBytes());
    }
}
