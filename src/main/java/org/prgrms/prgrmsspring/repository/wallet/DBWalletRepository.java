package org.prgrms.prgrmsspring.repository.wallet;

import org.prgrms.prgrmsspring.entity.wallet.Wallet;
import org.prgrms.prgrmsspring.exception.DataAccessException;
import org.prgrms.prgrmsspring.exception.ExceptionMessage;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
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
}
