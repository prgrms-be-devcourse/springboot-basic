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
import java.util.Optional;
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
    public Optional<Wallet> findById(UUID id) {
        String sql = "SELECT * FROM wallet WHERE id = UUID_TO_BIN(:id)";
        SqlParameterSource namedParameters = new MapSqlParameterSource("id", id.toString());
        return jdbcTemplate.query(sql, namedParameters, (resultSet, i) -> mapToWallet(resultSet)).stream().findFirst();
    }

    @Override
    public List<Wallet> findByCustomerId(UUID customerId) {
        String sql = "SELECT * FROM wallet WHERE customer_id = UUID_TO_BIN(:customerId)";
        SqlParameterSource namedParameters = new MapSqlParameterSource("customerId", customerId.toString());
        return jdbcTemplate.query(sql, namedParameters, (resultSet, i) -> mapToWallet(resultSet));
    }

    @Override
    public List<Wallet> findByVoucherId(UUID voucherId) {
        String sql = "SELECT * FROM wallet WHERE voucher_id = UUID_TO_BIN(:voucherId)";
        SqlParameterSource namedParameters = new MapSqlParameterSource("voucherId", voucherId.toString());
        return jdbcTemplate.query(sql, namedParameters, (resultSet, i) -> mapToWallet(resultSet));
    }

    @Override
    public Wallet save(Wallet wallet) {
        String sql = "INSERT INTO wallet (id, customer_id, voucher_id) VALUES (UUID_TO_BIN(:id),UUID_TO_BIN(:customerId),UUID_TO_BIN(:voucherId))";
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("id", wallet.getId().toString())
                .addValue("customerId", wallet.getCustomerId().toString())
                .addValue("voucherId", wallet.getVoucherId().toString());

        int affectedRow = jdbcTemplate.update(sql, namedParameters);
        logger.debug("Affected Row on save: {}", affectedRow);
        return wallet;
    }

    @Override
    public int delete(UUID customerId, UUID voucherId) {
        String sql = "DELETE FROM wallet WHERE customer_id = UUID_TO_BIN(:customerId) AND voucher_id = UUID_TO_BIN(:voucherId)";
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("customerId", customerId.toString())
                .addValue("voucherId", voucherId.toString());
        int affectedRow = jdbcTemplate.update(sql, namedParameters);
        logger.debug("Affected Row on delete: {}", affectedRow);
        return affectedRow;
    }

    @Override
    public int deleteByVoucherId(UUID voucherId) {
        String sql = "DELETE FROM wallet WHERE voucher_id = UUID_TO_BIN(:voucherId)";
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("voucherId", voucherId.toString());
        int affectedRow = jdbcTemplate.update(sql, namedParameters);
        logger.debug("Affected Row on delete by Voucher Id: {}", affectedRow);
        return affectedRow;
    }

    @Override
    public int deleteByCustomerId(UUID customerId) {
        String sql = "DELETE FROM wallet WHERE customer_id = UUID_TO_BIN(:customerId)";
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("id", customerId.toString());
        int affectedRow = jdbcTemplate.update(sql, namedParameters);
        logger.debug("Affected Row on delete by Customer Id: {}", affectedRow);
        return affectedRow;
    }

    private Wallet mapToWallet(ResultSet resultSet) throws SQLException {
        return new Wallet(
                toUUID(resultSet.getBytes("id")),
                toUUID(resultSet.getBytes("customer_id")),
                toUUID(resultSet.getBytes("voucher_id"))
        );
    }

    private UUID toUUID(byte[] bytes) {
        final ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}
