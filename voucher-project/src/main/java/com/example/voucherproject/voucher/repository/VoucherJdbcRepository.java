package com.example.voucherproject.voucher.repository;

import com.example.voucherproject.voucher.enums.VoucherType;
import com.example.voucherproject.voucher.domain.Voucher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
public class VoucherJdbcRepository implements VoucherRepository{
    private final JdbcTemplate jdbcTemplate;
    private final String INSERT_SQL = "INSERT INTO vouchers(id, type, amount, created_at) VALUES(UNHEX(REPLACE(?,'-','')), ?, ?, ?)";
    private final String FIND_ALL_SQL = "select * from vouchers";

    private final String FIND_BY_TYPE_SQL = "select * from vouchers where type = ?";
    private final String FIND_BY_ID_SQL = "select * from vouchers where id = UNHEX(REPLACE(?,'-',''))";
    private final String COUNT_SQL = "select count(*) from vouchers";
    private final String DELETE_SQL = "DELETE FROM vouchers";

    @Override
    public Voucher insert(Voucher voucher) {
        var update = jdbcTemplate.update(INSERT_SQL,
                voucher.getId().toString().getBytes(),
                voucher.getType().toString(),
                voucher.getAmount(),
                Timestamp.valueOf(voucher.getCreatedAt()));

        if (update != 1) {
            throw new RuntimeException("Nothing was inserted");
        }
        return voucher;
    }

    @Override
    public List<Voucher> findHavingTypeAll(VoucherType type) { //findAll인데 타입이 왜 필요행?
        var vouchers = jdbcTemplate.query(FIND_ALL_SQL, rowMapper());
        return vouchers.stream()
                .filter(voucher -> voucher.getType()== type)
                .collect(Collectors.toList());
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query(FIND_ALL_SQL, rowMapper());
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try{
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                    FIND_BY_ID_SQL, rowMapper(), voucherId.toString().getBytes()));
        }catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }


    @Override
    public int deleteAll() {
        return jdbcTemplate.update(DELETE_SQL);
    }

    @Override
    public long count() {
        var count = jdbcTemplate.queryForObject(COUNT_SQL, Integer.class);
        return (count != null) ? count.longValue() : 0;
    }



    /* helper method */
    private RowMapper<Voucher> rowMapper() {
        return ((rs, rowNum) -> {
            var id = toUUID(rs.getBytes("id"));
            var type = VoucherType.valueOf(rs.getString("type"));
            var amount = rs.getLong("amount");
            var createdAt = rs.getTimestamp("created_at").toLocalDateTime().truncatedTo(ChronoUnit.MILLIS);
            var updatedAt = rs.getString("updated_at");
            return new Voucher(id,type,amount,createdAt);
        });
    }

    private static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}