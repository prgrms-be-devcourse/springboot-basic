package org.prgrms.prgrmsspring.repository.wallet;

import org.prgrms.prgrmsspring.entity.wallet.Wallet;
import org.prgrms.prgrmsspring.exception.DataAccessException;
import org.prgrms.prgrmsspring.exception.ExceptionMessage;
import org.prgrms.prgrmsspring.utils.BinaryToUUIDConverter;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Profile("prod")
@Repository
public class DBWalletRepository implements WalletRepository {

    private final JdbcTemplate jdbcTemplate;


    public DBWalletRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Wallet allocateVoucherToCustomer(UUID customerId, UUID voucherId) {
        String sql = "INSERT INTO WALLET (CUSTOMER_ID, VOUCHER_ID) VALUES (UUID_TO_BIN(?), UUID_TO_BIN(?))";
        int update = jdbcTemplate.update(sql, customerId.toString(), voucherId.toString());
        if (update != 1) {
            throw new DataAccessException(ExceptionMessage.INSERT_QUERY_FAILED.getMessage());
        }
        return new Wallet(customerId, voucherId);
    }

    @Override
    public Optional<List<UUID>> findVoucherIdListByCustomerId(UUID customerId) {
        String sql = "SELECT VOUCHER_ID FROM WALLET WHERE CUSTOMER_ID = UUID_TO_BIN(?)";
        List<UUID> voucherIdList = jdbcTemplate.query(sql, (rs, rowNum) -> new BinaryToUUIDConverter().run(rs.getBytes("VOUCHER_ID")), customerId.toString());
        return Optional.ofNullable(voucherIdList);
    }

    @Override
    public void deleteVouchersByCustomerId(UUID customerId) {
        String sql = "DELETE FROM WALLET WHERE CUSTOMER_ID = UUID_TO_BIN(?)";
        jdbcTemplate.update(sql, customerId.toString());
    }

    @Override
    public Optional<UUID> findCustomerIdByVoucherId(UUID voucherId) {
        String sql = "SELECT CUSTOMER_ID FROM WALLET WHERE VOUCHER_ID = UUID_TO_BIN(?)";
        UUID customerId = jdbcTemplate.queryForObject(sql, (rs, rowNum) -> new BinaryToUUIDConverter().run(rs.getBytes("CUSTOMER_ID")), voucherId.toString());
        return Optional.ofNullable(customerId);
    }
}
