package com.programmers.vouchermanagement.repository.wallet;

import com.programmers.vouchermanagement.domain.wallet.Wallet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Repository
public class JdbcWalletRepository implements WalletRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final Logger logger = LoggerFactory.getLogger(JdbcWalletRepository.class);

    public JdbcWalletRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Wallet> findAll() {
        String sql = "SELECT * FROM wallet";
        return jdbcTemplate.query(sql, (resultSet, i) -> mapToWallet(resultSet));
    }

    @Override
    public List<Wallet> findByCustomerId(UUID customerId) {
        String sql = "SELECT * FROM wallet WHERE customer_id = UUID_TO_BIN(:customerId)";
        SqlParameterSource namedParameters = new MapSqlParameterSource("customerId", customerId.toString());
        return jdbcTemplate.query(sql, namedParameters, (resultSet, i) -> mapToWallet(resultSet));
    }

    @Override
    public List<Wallet> findByVoucherId(UUID voucherId) {
        String sql = "SELECT * FROM wallet WHERE customer_id = UUID_TO_BIN(:voucherId)";
        SqlParameterSource namedParameters = new MapSqlParameterSource("voucherId", voucherId.toString());
        return jdbcTemplate.query(sql, namedParameters, (resultSet, i) -> mapToWallet(resultSet));
    }

    @Override
    public Wallet save(Wallet wallet) {
        String sql = "INSERT INTO wallet (id, customer_id, voucher_id) VALUES (UUID_TO_BIN(:id),UUID_TO_BIN(:customerId),UUID_TO_BIN(:voucherId))";
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("id", wallet.getId().toString())
                .addValue("customer_id", wallet.getCustomerId().toString())
                .addValue("voucher_id", wallet.getVoucherId().toString());

        int affectedRow = jdbcTemplate.update(sql, namedParameters);
        logger.debug("Affected Row on save: {}", affectedRow);
        return wallet;
    }

    @Override
    public int delete(UUID id) {
        String sql = "DELETE FROM wallet WHERE id = UUID_TO_BIN(:id)";
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("id", id.toString());
        int affectedRow = jdbcTemplate.update(sql, namedParameters);
        logger.debug("Affected Row on delete: {}", affectedRow);
        return affectedRow;
    }

    private Wallet mapToWallet(ResultSet resultSet) throws SQLException {
        return new Wallet(
                toUUID(resultSet.getBytes("id")),
                toUUID(resultSet.getBytes("customerId")),
                toUUID(resultSet.getBytes("voucherId"))
        );
    }

    private UUID toUUID(byte[] bytes) {
        final ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}
