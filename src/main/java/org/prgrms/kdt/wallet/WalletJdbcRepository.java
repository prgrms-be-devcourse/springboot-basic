package org.prgrms.kdt.wallet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class WalletJdbcRepository implements WalletRepository{
    private static final Logger logger = LoggerFactory.getLogger(WalletJdbcRepository.class);
    private final DataSource dataSource;
    private final JdbcTemplate jdbcTemplate;
    private static final RowMapper<Wallet> walletRowMapper = (resultSet, i) -> {
        UUID customerId = toUUID(resultSet.getBytes("customer_id"));
        UUID voucherId = toUUID(resultSet.getBytes("voucher_id"));
        return new Wallet(customerId.toString(), voucherId.toString());
    };

    public WalletJdbcRepository(DataSource dataSource, JdbcTemplate jdbcTemplate) {
        this.dataSource = dataSource;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Wallet save(Wallet wallet) {
        return null;
    }

    @Override
    public Optional<Wallet> findById(String walletId) {
        return Optional.empty();
    }

    @Override
    public List<Wallet> findByCustomerId(String customerId) {
        return null;
    }

    @Override
    public List<Wallet> findByVoucherId(String voucherId) {
        return null;
    }

    @Override
    public void deleteByCustomerId(String customerId) {

    }

    static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}
