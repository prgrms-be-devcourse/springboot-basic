package com.example.voucherproject.wallet.repository;

import com.example.voucherproject.wallet.domain.Wallet;
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

    private final String INSERT_SQL = "INSERT INTO wallets(id, user_id, voucher_id, created_at) " +
            "VALUES(UNHEX(REPLACE(?,'-','')), UNHEX(REPLACE(?,'-','')), UNHEX(REPLACE(?,'-','')), ?)";
    private final String FIND_ALL_SQL = "select * from wallets";

    private final String FIND_BY_IDS_SQL = "select * from wallets " +
            "where user_id = UNHEX(REPLACE(?,'-','')) and voucher_id = UNHEX(REPLACE(?,'-',''))";

    private final String COUNT_SQL = "select count(*) from wallets";
    private final String DELETE_SQL = "DELETE FROM wallets";
    private final String DELETE_BY_ID_SQL = "DELETE FROM wallets WHERE id = UNHEX(REPLACE(?,'-',''))";
    private final String FIND_BY_USER_ID_SQL = "select * FROM wallets WHERE user_id = UNHEX(REPLACE(?,'-',''))";
    private final String FIND_BY_VOUCHER_ID_SQL = "select * FROM wallets WHERE voucher_id = UNHEX(REPLACE(?,'-',''))";

    @Override
    public Wallet insert(Wallet wallet) {
        var update = jdbcTemplate.update(INSERT_SQL,
                wallet.getId().toString().getBytes(),
                wallet.getUserId().toString().getBytes(),
                wallet.getVoucherId().toString().getBytes(),
                Timestamp.valueOf(wallet.getCreatedAt()));

        if (update != 1) {
            throw new RuntimeException("Nothing was inserted");
        }
        return wallet;
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
    public List<Wallet> findByUserId(UUID userId) {
        return jdbcTemplate.query(FIND_BY_USER_ID_SQL, rowMapper(), userId.toString().getBytes());
    }

    @Override
    public List<Wallet> findByVoucherId(UUID voucherId) {
        return jdbcTemplate.query(FIND_BY_VOUCHER_ID_SQL, rowMapper(), voucherId.toString().getBytes());
    }

    @Override
    public int deleteById(UUID id) {
        return jdbcTemplate.update(DELETE_BY_ID_SQL, id.toString().getBytes());
    }

    @Override
    public List<Wallet> findAll() {
        return jdbcTemplate.query(FIND_ALL_SQL, rowMapper());
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
        return ((rs, rowNum) -> {
            var id = toUUID(rs.getBytes("id"));
            var userId = toUUID(rs.getBytes("user_id"));
            var voucherId = toUUID(rs.getBytes("voucher_id"));
            var createdAt = rs.getTimestamp("created_at").toLocalDateTime().truncatedTo(ChronoUnit.MILLIS);

            return new Wallet(id, userId, voucherId,createdAt);
        });
    }
    private static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}
