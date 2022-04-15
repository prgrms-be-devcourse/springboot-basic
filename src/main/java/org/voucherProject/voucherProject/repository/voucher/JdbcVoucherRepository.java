package org.voucherProject.voucherProject.repository.voucher;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.voucherProject.voucherProject.entity.voucher.Voucher;
import org.voucherProject.voucherProject.entity.voucher.VoucherStatus;
import org.voucherProject.voucherProject.entity.voucher.VoucherType;

import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Repository
@Slf4j
//@Primary
@RequiredArgsConstructor
public class JdbcVoucherRepository implements VoucherRepository {

    private final String SELECT_ALL_SQL = "select * from voucher";
    private final String SELECT_BY_ID_SQL = "select * from voucher where voucher_id = UUID_TO_BIN(:voucherId)";
    private final String SAVE_SQL = "insert into voucher(voucher_id, amount, voucher_type, voucher_status, created_at) values (UUID_TO_BIN(:voucherId), :amount, :voucherType, :voucherStatus)";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                    SELECT_BY_ID_SQL,
                    Collections.singletonMap("customerId", voucherId.toString().getBytes()),
                    customerRowMapper()));
        } catch (EmptyResultDataAccessException e) {
//            log.error("result empty -> {}", e);
            return Optional.empty();
        }
    }

    @Override
    public Voucher save(Voucher voucher) {
        int save = jdbcTemplate.update(SAVE_SQL, toParamMap(voucher));
        if (save != 1) {
            throw new RuntimeException("No Save");
        }
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query(SELECT_ALL_SQL, customerRowMapper());
    }

    static UUID toUUID(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        UUID customerId = new UUID(byteBuffer.getLong(), byteBuffer.getLong());
        return customerId;
    }

    private static RowMapper<Voucher> customerRowMapper() {
        return (resultSet, i) -> {
            UUID voucherId = toUUID(resultSet.getBytes("voucher_id"));
            long amount = resultSet.getLong("amount");
            VoucherType voucherType = VoucherType.valueOf(resultSet.getString("voucher_type").toUpperCase());
            VoucherStatus voucherStatus = VoucherStatus.valueOf(resultSet.getString("voucher_status").toUpperCase());
            LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
            return voucherType.create(voucherId, amount, voucherStatus, createdAt);
        };
    }

    private Map<String, Object> toParamMap(Voucher voucher) {
        return new HashMap<>() {{
            put("voucherId", voucher.getVoucherId().toString().getBytes());
            put("amount", voucher.getHowMuch());
            put("voucherType", voucher.getVoucherType());
            put("voucherStatus", voucher.getVoucherStatus());
            put("createdAt", Timestamp.valueOf(voucher.getCreatedAt()));
        }};
    }

}
