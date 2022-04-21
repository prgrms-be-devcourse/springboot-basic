package org.voucherProject.voucherProject.voucher.repository;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.voucherProject.voucherProject.voucher.entity.Voucher;
import org.voucherProject.voucherProject.voucher.entity.VoucherStatus;
import org.voucherProject.voucherProject.voucher.entity.VoucherType;
import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Repository
@Slf4j
@Primary
public class JdbcVoucherDao implements VoucherDao {

    private final String SELECT_ALL_SQL = "SELECT * FROM voucher";
    private final String SELECT_BY_ID_SQL = "SELECT * FROM voucher WHERE voucher_id = UUID_TO_BIN(:voucherId)";
    private final String INSERT_SQL = "INSERT INTO voucher(voucher_id, amount, voucher_type, voucher_status, created_at, customer_id) VALUES (UUID_TO_BIN(:voucherId), :amount, :voucherType, :voucherStatus, :createdAt, UUID_TO_BIN(:customerId))";
    private final String DELETE_ALL_SQL = "DELETE FROM voucher";
    private final String UPDATE_SQL = "UPDATE voucher SET voucher_type = :voucherType, voucher_status = :voucherStatus, amount = :amount WHERE voucher_id = UUID_TO_BIN(:voucherId)";
    private final String SELECT_BY_CUSTOMER_ID_SQL = "SELECT * FROM voucher v LEFT JOIN customers c ON v.customer_id = c.customer_id WHERE v.customer_id = UUID_TO_BIN(:customerId)";
    private final String SELECT_BY_VOUCHER_TYPE_SQL = "SELECT * FROM voucher WHERE voucher_type = :voucherType";
    private final String DELETE_ONE_BY_CUSTOMER_ID_SQL = "DELETE FROM voucher WHERE customer_id = UUID_TO_BIN(:customerId) AND voucher_id = UUID_TO_BIN(:voucherId)";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcVoucherDao(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                    SELECT_BY_ID_SQL,
                    Collections.singletonMap("voucherId", voucherId.toString().getBytes()),
                    customerRowMapper()));
        } catch (EmptyResultDataAccessException e) {
            log.error("result empty -> {}", e.toString());
            return Optional.empty();
        }
    }

    @Override
    public Voucher save(Voucher voucher) {
        int save = jdbcTemplate.update(INSERT_SQL, toParamMap(voucher));
        if (save != 1) {
            throw new RuntimeException("No Save");
        }
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query(SELECT_ALL_SQL, customerRowMapper());
    }

    @Override
    public List<Voucher> findByCustomerId(UUID customerId) {
        return jdbcTemplate.query(SELECT_BY_CUSTOMER_ID_SQL,
                Collections.singletonMap("customerId",customerId.toString().getBytes()),
                customerRowMapper());
    }

    @Override
    public List<Voucher> findByVoucherType(VoucherType voucherType) {
        return jdbcTemplate.query(SELECT_BY_VOUCHER_TYPE_SQL,
                Collections.singletonMap("voucherType",voucherType.toString()),
                customerRowMapper());
    }

    @Override
    public Voucher update(Voucher voucher) {
        int update = jdbcTemplate.update(UPDATE_SQL,
                toParamMap(voucher));
        if (update != 1) {
            throw new RuntimeException("No Update");
        }
        return voucher;
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(DELETE_ALL_SQL, Collections.emptyMap());
    }

    @Override
    public void deleteOneByCustomerId(UUID customerId, UUID voucherId) {
        Map<String, Object> hashMap = new HashMap<>() {{
            put("customerId", customerId.toString().getBytes());
            put("voucherId", voucherId.toString().getBytes());
        }};
        int delete = jdbcTemplate.update(DELETE_ONE_BY_CUSTOMER_ID_SQL, hashMap);
        if (delete != 1) {
            throw new RuntimeException("No Delete");
        }
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
            UUID customerId = toUUID(resultSet.getBytes("customer_id"));
            return voucherType.createVoucher(voucherId, amount, voucherStatus, createdAt, customerId);
        };
    }

    private Map<String, Object> toParamMap(Voucher voucher) {
        return new HashMap<>() {{
            put("voucherId", voucher.getVoucherId().toString().getBytes());
            put("amount", voucher.getHowMuch());
            put("voucherType", String.valueOf(voucher.getVoucherType()));
            put("voucherStatus", String.valueOf(voucher.getVoucherStatus()));
            put("createdAt", Timestamp.valueOf(voucher.getCreatedAt()));
            put("customerId", voucher.getCustomerId().toString().getBytes());
        }};
    }
}
