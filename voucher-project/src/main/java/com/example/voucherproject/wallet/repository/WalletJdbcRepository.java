package com.example.voucherproject.wallet.repository;

import com.example.voucherproject.wallet.model.Wallet;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class WalletJdbcRepository implements WalletRepository{
    private final JdbcTemplate jdbcTemplate;

    private final String INSERT_SQL = "INSERT INTO wallet(id, user_id, voucher_id, created_at, updated_at) " +
            "VALUES(UNHEX(REPLACE(?,'-','')), UNHEX(REPLACE(?,'-','')), UNHEX(REPLACE(?,'-','')), ?, ?)";
    private final String FIND_ALL_SQL = "select * from wallet";
    private final String FIND_BY_IDS_SQL = "select * from wallet " +
            "where user_id = UNHEX(REPLACE(?,'-','')) and voucher_id = UNHEX(REPLACE(?,'-',''))";
    private final String COUNT_SQL = "select count(*) from wallet";
    private final String DELETE_SQL = "DELETE FROM wallet";
    private final String DELETE_BY_ID_SQL = "DELETE FROM wallet WHERE id = UNHEX(REPLACE(?,'-',''))";
    private final String FIND_BY_USER_ID_SQL = "select * FROM wallet WHERE user_id = UNHEX(REPLACE(?,'-',''))";
    private final String FIND_BY_VOUCHER_ID_SQL = "select * FROM wallet WHERE voucher_id = UNHEX(REPLACE(?,'-',''))";
    private final String FIND_BY_ID_SQL = "select * from wallet where id = UNHEX(REPLACE(?,'-',''))";

    @Override
    public Wallet insert(Wallet wallet) {
        var update = jdbcTemplate.update(INSERT_SQL,
                wallet.getId().toString().getBytes(),
                wallet.getUserId().toString().getBytes(),
                wallet.getVoucherId().toString().getBytes(),
                Timestamp.valueOf(wallet.getCreatedAt()),
                Timestamp.valueOf(wallet.getUpdatedAt()));

        if (update != 1) {
            throw new RuntimeException("Nothing was inserted");
        }
        return wallet;
    }
    @Override
    public Optional<Wallet> findById(UUID id) {
        try{
            return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_BY_ID_SQL,
                    rowMapper(),
                    id.toString().getBytes()));
        }catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }
    @Override
    public Optional<Wallet> findByIds(UUID userId, UUID voucherId) {
        try{
            return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_BY_IDS_SQL,
                    rowMapper(), userId.toString().getBytes(), voucherId.toString().getBytes()));
        }catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }
    @Override
    public List<Wallet> findAllByUserId(UUID userId) {
        return jdbcTemplate.query(FIND_BY_USER_ID_SQL, rowMapper(), userId.toString().getBytes());
    }
    @Override
    public List<Wallet> findAllByVoucherId(UUID voucherId) {
        return jdbcTemplate.query(FIND_BY_VOUCHER_ID_SQL, rowMapper(), voucherId.toString().getBytes());
    }
    @Override
    public List<Wallet> findAll() {
        return jdbcTemplate.query(FIND_ALL_SQL, rowMapper());
    }

    @Override
    public int deleteById(UUID id) {
        return jdbcTemplate.update(DELETE_BY_ID_SQL, id.toString().getBytes());
    }
    @Override
    public int deleteAll() {
        return jdbcTemplate.update(DELETE_SQL);
    }
    @Override
    public int count() {
        var count = jdbcTemplate.queryForObject(COUNT_SQL, Integer.class);
        return (count != null) ? count : 0;
    }

    private RowMapper<Wallet> rowMapper() {
        return ((resultSet, rowNum) -> {
            var id = toUUID(resultSet.getBytes("id"));
            var userId = toUUID(resultSet.getBytes("user_id"));
            var voucherId = toUUID(resultSet.getBytes("voucher_id"));
            var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime().truncatedTo(ChronoUnit.MILLIS);
            var updatedAt = resultSet.getTimestamp("updated_at") != null ?
                    resultSet.getTimestamp("updated_at").toLocalDateTime() : null;
            return new Wallet(id, userId, voucherId, createdAt,updatedAt);
        });
    }
    private static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}
