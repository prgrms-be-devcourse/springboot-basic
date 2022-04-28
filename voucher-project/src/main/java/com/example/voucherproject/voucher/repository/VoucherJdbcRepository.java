package com.example.voucherproject.voucher.repository;

import com.example.voucherproject.user.model.User;
import com.example.voucherproject.user.model.UserType;
import com.example.voucherproject.voucher.model.VoucherType;
import com.example.voucherproject.voucher.model.Voucher;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class VoucherJdbcRepository implements VoucherRepository{
    private final JdbcTemplate jdbcTemplate;
    private final String INSERT_SQL = "INSERT INTO voucher(id, type, amount, created_at) VALUES(UNHEX(REPLACE(?,'-','')), ?, ?, ?)";
    private final String FIND_ALL_SQL = "select * from voucher";
    private final String FIND_BY_ID_SQL = "select * from voucher where id = UNHEX(REPLACE(?,'-',''))";
    private final String COUNT_SQL = "select count(*) from voucher";
    private final String DELETE_SQL = "DELETE FROM voucher";
    private final String DELETE_BY_ID_SQL = "DELETE FROM voucher WHERE id = UNHEX(REPLACE(?,'-',''))";

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
    public List<Voucher> findAll() {
        return jdbcTemplate.query(FIND_ALL_SQL, rowMapper());
    }

    @Override
    public List<Voucher> findAllByVoucherType(VoucherType type) {
        var vouchers = jdbcTemplate.query(FIND_ALL_SQL, rowMapper());
        return vouchers.stream()
                .filter(voucher -> voucher.getType()== type)
                .collect(Collectors.toList());
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
    public int deleteById(UUID id) {
        return jdbcTemplate.update(DELETE_BY_ID_SQL, id.toString().getBytes());
    }

    @Override
    public List<Voucher> findByTypeAndDate(VoucherType type, String from, String to) {
        var vouchers = jdbcTemplate.query(FIND_ALL_SQL, rowMapper());

        var start = string2LocalDateTimeConverter(from);
        var end = string2LocalDateTimeConverter(to);

        if(type==VoucherType.ALL){
            var listVouchers =  vouchers.stream()
                    .filter(voucher -> voucher.getCreatedAt().isAfter(start))
                    .filter(voucher -> voucher.getCreatedAt().isBefore(end))
                    .collect(Collectors.toList());
            System.out.println(listVouchers);
            System.out.println("nums : "+listVouchers.size());
            return listVouchers;
        }

        var listUsers2 = vouchers.stream()
                .filter(voucher -> voucher.getType() == type)
                .filter(voucher -> voucher.getCreatedAt().isAfter(start))
                .filter(voucher -> voucher.getCreatedAt().isBefore(end))
                .collect(Collectors.toList());
        System.out.println(listUsers2);
        System.out.println("nums : "+listUsers2.size());
        return listUsers2;

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
    private static LocalDateTime string2LocalDateTimeConverter(String date){
        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
        return LocalDateTime.parse(date + "T00:00:00.000", formatter);
    }
}