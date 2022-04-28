package com.example.voucherproject.voucher.repository;

import com.example.voucherproject.user.model.User;
import com.example.voucherproject.user.model.UserType;
import com.example.voucherproject.voucher.dto.VoucherDTO;
import com.example.voucherproject.voucher.model.VoucherType;
import com.example.voucherproject.voucher.model.Voucher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@RequiredArgsConstructor
public class VoucherJdbcRepository implements VoucherRepository{
    private final JdbcTemplate jdbcTemplate;
    private final String INSERT_SQL = "INSERT INTO voucher(id, type, amount, created_at) VALUES(UNHEX(REPLACE(?,'-','')), ?, ?, ?)";
    private final String FIND_ALL_SQL = "select * from voucher";
    private final String FIND_BY_ID_SQL = "select * from voucher where id = UNHEX(REPLACE(?,'-',''))";
    private final String COUNT_SQL = "select count(*) from voucher";
    private final String DELETE_SQL = "DELETE FROM voucher";
    private final String DELETE_BY_ID_SQL = "DELETE FROM voucher WHERE id = UNHEX(REPLACE(?,'-',''))";
    private final String UPDATE_TYPE_AND_AMOUNT = "UPDATE voucher SET type = ? , amount = ? WHERE id = UNHEX(REPLACE(?,'-',''))";
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
    public void updateTypeAndAmountByDto(VoucherDTO.Update dto) {
        var update = jdbcTemplate.update(UPDATE_TYPE_AND_AMOUNT,
                dto.getType().toString(),
                dto.getAmount(),
                dto.getId().toString().getBytes());

        if (update != 1){
            throw new RuntimeException("Nothing Updated");
        }
    }

    @Override
    public List<Voucher> findByTypeAndDate(VoucherDTO.Query query) {

        var vouchers = jdbcTemplate.query(FIND_ALL_SQL, rowMapper());

        if(query.getType()==VoucherType.ALL){
            var listVouchers =  vouchers.stream()
                    .filter(voucher -> voucher.getCreatedAt().isAfter(query.convertTimeFrom()))
                    .filter(voucher -> voucher.getCreatedAt().isBefore(query.convertTimeTo()))
                    .collect(Collectors.toList());
            return listVouchers;
        }

        var listUsers2 = vouchers.stream()
                .filter(voucher -> voucher.getType() == query.getType())
                .filter(voucher -> voucher.getCreatedAt().isAfter(query.convertTimeFrom()))
                .filter(voucher -> voucher.getCreatedAt().isBefore(query.convertTimeTo()))
                .collect(Collectors.toList());

        return listUsers2;

    }

    @Override
    public long count() {
        var count = jdbcTemplate.queryForObject(COUNT_SQL, Integer.class);
        return (count != null) ? count.longValue() : 0;
    }
    /* helper method */
    private RowMapper<Voucher> rowMapper() {
        return ((resultSet, rowNum) -> {
            var id = toUUID(resultSet.getBytes("id"));
            var type = VoucherType.valueOf(resultSet.getString("type"));
            var amount = resultSet.getLong("amount");
            var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime().truncatedTo(ChronoUnit.MILLIS);

            var updatedAt = resultSet.getTimestamp("updated_at") != null ?
                    resultSet.getTimestamp("updated_at").toLocalDateTime() : null;
            return new Voucher(id, type, amount, createdAt, updatedAt);
        });
    }

    private static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

}